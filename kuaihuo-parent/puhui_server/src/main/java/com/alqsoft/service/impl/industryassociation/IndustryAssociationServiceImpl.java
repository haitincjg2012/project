package com.alqsoft.service.impl.industryassociation;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.easyui.EasyUtils;
import org.alqframework.easyui.EasyuiResult;
import org.alqframework.net.html.HttpClientUtils;
import org.alqframework.orm.filter.DynamicSpecifications;
import org.alqframework.orm.filter.SearchFilter;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.alqframework.webmvc.springmvc.SpringMVCUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alqsoft.dao.industryassociation.IndustryAssociationDao;
import com.alqsoft.entity.bankcard.BankCard;
import com.alqsoft.entity.industryassociation.IndustryAssociation;
import com.alqsoft.entity.order.Order;
import com.alqsoft.entity.role.Role;
import com.alqsoft.entity.user.User;
import com.alqsoft.init.InitParamPc;
import com.alqsoft.init.InitParams;
import com.alqsoft.mybatis.service.bankcard.MyBankCardService;
import com.alqsoft.mybatis.service.industryassociation.MyIndustryAssociationService;
import com.alqsoft.service.bankcard.BankCardService;
import com.alqsoft.service.industryassociation.IndustryAssociationService;
import com.alqsoft.service.industrymoney.IndustryMoneyService;
import com.alqsoft.service.order.OrderService;
import com.alqsoft.service.user.UserService;
import com.alqsoft.utils.HttpClientObject;
import com.mysql.jdbc.StringUtils;

@Service
@Transactional(readOnly=true)
public class IndustryAssociationServiceImpl implements IndustryAssociationService {

	@Autowired
	private IndustryAssociationDao industryAssociationDao;
	@Autowired
	private OrderService orderService;
	@Autowired
	private IndustryMoneyService industryMoneyService;
	@Autowired
	private BankCardService bankCardService;
	@Autowired
	private InitParamPc initParamPc;
	@Autowired
	private UserService userService;
	@Autowired
	private StandardPasswordEncoder passwordEncoder;
	@Autowired
	private MyIndustryAssociationService myIndustryAssociationService;
	@Autowired
	private MyBankCardService myBankCardService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(IndustryAssociationServiceImpl.class);
	
	@Override
	@CacheEvict(key="'alq_industry_association'+#arg0",value="alq_industry_association")
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Cacheable(key="'alq_industry_association'+#arg0",value="alq_industry_association")
	public IndustryAssociation get(Long arg0) {
		// TODO Auto-generated method stub
		return industryAssociationDao.findOne(arg0);
	}

	@Override
	@Transactional
	@CacheEvict(key="'alq_industry_association'+#arg0.id",value="alq_industry_association")
	public IndustryAssociation saveAndModify(IndustryAssociation arg0) {
		
		
		return industryAssociationDao.save(arg0);
	}
	
	@Override
	@Transactional
	public void industryAssociationFenRun(Order order) {
		
		try {
			IndustryAssociation industryAssociation = this.industryAssociationDao.getRowLock(order.getHunter().getIndustryAssociation().getId());
			Long industryFen = order.getIndustryFen();
			Long incomeAllMoney = industryAssociation.getIncomeAllMoney()==null?0L:industryAssociation.getIncomeAllMoney();
			order.setIndustryAfterMoney(incomeAllMoney.longValue()+industryFen.longValue());//维护订单行业公会分润后的金额
			//order.setFenRunStatus(1);
			
			/*IndustryMoney industryMoney = new IndustryMoney();//维护行业公会分润信息
			industryMoney.setMoney(industryFen);
			industryMoney.setBeforeMoney(incomeAllMoney);
			industryMoney.setAfterMoney(incomeAllMoney.longValue()+industryFen.longValue());
			industryMoney.setIndustryAssociation(industryAssociation);
			industryMoney.setType(0);
			industryMoney.setOrderNo(order.getOrderNo());
			industryMoney.setOrderSubNo(order.getOrderSubNo());*/
			
			industryAssociation.setIncomeAllMoney(incomeAllMoney.longValue()+industryFen.longValue());//维护行业金额
			this.saveAndModify(industryAssociation);
			//this.industryMoneyService.saveAndModify(industryMoney);
			//this.orderService.saveAndModify(order);
			logger.info("订单:"+order.getId()+"行业分润完成");
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.info("订单:"+order.getId()+"行业分润失败");
		}
		
	}

	
	/**
	 * 行业公会后台--安全设置-修改手机号  原手机号码发送验证码   PHPF2017070305
	 * @param oldphone
	 * @return
	 */
	@Override
	public Result sendMsgForOldPhone(String oldphone, String codeType, HttpServletRequest request) {
		Result result = new Result();
		
		//验证手机号
		if(StringUtils.isEmptyOrWhitespaceOnly(oldphone)){
			return ResultUtils.returnError("原手机号不能为空");
		}

		String regex="^1[34578]\\d{9}$";
		if(!oldphone.matches(regex)){
			return ResultUtils.returnError("请输入正确的手机号码,1(3,4,5,7,8)开头");
		}
		
		//登录信息取值判断手机号是否是该行业公会
		User user=(User)request.getSession().getAttribute("user");
		String phone=user.getUserName();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("phone",phone);
		IndustryAssociation industryAssociationLog=myIndustryAssociationService.getAssociationByPhone(param);
		if(null==industryAssociationLog){
			return ResultUtils.returnError("登录信息有误");
		}
		if(!oldphone.equals(industryAssociationLog.getPhone())){
			return ResultUtils.returnError("修改手机号与用户信息不一致");
		}
		
		try {
			String msg_url = this.initParamPc.getProperties().getProperty("send_msg_url");//发送短信路径
			String url = msg_url+"?phone="+oldphone+"&codeType="+codeType;
			String sendGet = HttpClientUtils.getHttpsToGet(url);
			JSONObject json = JSON.parseObject(sendGet);
			result.setCode(Integer.parseInt(json.get("code").toString()));
			result.setMsg(json.get("msg").toString());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("调用发送验证码接口异常"+e.getMessage());
			
			result.setCode(0);
			result.setMsg("原手机号验证码发送失败");
		}
		
		return result;
	}

	/**
	 * 行业公会后台--安全设置-修改手机号  新手机号码发送验证码   PHPF2017070306
	 * @param newPhone
	 * @return
	 */
	@Override
	public Result sendMsgForNewPhone(String newPhone, String codeType) {
		Result result = new Result();
		
		//验证手机号
		if(StringUtils.isEmptyOrWhitespaceOnly(newPhone)){
			return ResultUtils.returnError("新手机号不能为空");
		}

		String regex="^1[34578]\\d{9}$";
		if(!newPhone.matches(regex)){
			return ResultUtils.returnError("请输入正确的手机号码,1(3,4,5,7,8)开头");
		}
		
		try {
			String msg_url = this.initParamPc.getProperties().getProperty("send_msg_url");//发送短信路径
			String url = msg_url+"?phone="+newPhone+"&codeType="+codeType;
			String sendGet = HttpClientUtils.getHttpsToGet(url);
			JSONObject json = JSON.parseObject(sendGet);
			result.setCode(Integer.parseInt(json.get("code").toString()));
			result.setMsg(json.get("msg").toString());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("调用发送验证码接口异常"+e.getMessage());
			
			result.setCode(0);
			result.setMsg("新手机号验证码发送失败");
		}
		
		return result;
	}

	/**
	 * 验证验证码
	 * @param phone
	 * @param code
	 * @param codeType
	 * @return
	 */
	public Result checkMsg(String phone, String code, String codeType) {
		Result result = new Result();
		try {
			String msg_url = this.initParamPc.getProperties().getProperty("check_msg_url");//发送短信路径
			String url = msg_url+"?phone="+phone+"&code="+code+"&codeType="+codeType;
			String sendGet = HttpClientUtils.getHttpsToGet(url);
			JSONObject json = JSON.parseObject(sendGet);
			result.setCode(Integer.parseInt(json.get("code").toString()));
			result.setMsg(json.get("msg").toString());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("调用验证验证码接口异常"+e.getMessage());
			result.setCode(0);
			result.setMsg("调用验证验证码接口异常");
		}
		return result;
	}
	
	/**
	 * 行业公会后台--安全设置-修改手机号 
	 * @return
	 */
	@Override
	@Transactional
	public Result updatephone(String oldPhone, String oldCode, String newPhone, String newCode,
			IndustryAssociation industryAssociation) {
		
			Result result = new Result();
		
			//验证手机号
				String regex="^1[34578]\\d{9}$";
				
				if(StringUtils.isEmptyOrWhitespaceOnly(oldPhone)){
					return ResultUtils.returnError("旧手机号不能为空");
				}
				if(!oldPhone.matches(regex)){
					return ResultUtils.returnError("请输入正确的手机号码,1(3,4,5,7,8)开头");
				}
				if(StringUtils.isEmptyOrWhitespaceOnly(oldCode)){
					return ResultUtils.returnError("旧手机号验证码不能为空");
				}
				if(StringUtils.isEmptyOrWhitespaceOnly(newPhone)){
					return ResultUtils.returnError("新手机号不能为空");
				}
				if(!newPhone.matches(regex)){
					return ResultUtils.returnError("请输入正确的手机号码,1(3,4,5,7,8)开头");
				}
				if(StringUtils.isEmptyOrWhitespaceOnly(newCode)){
					return ResultUtils.returnError("新手机号验证码不能为空");
				}
				
				if(!oldPhone.matches(industryAssociation.getPhone())){
					return ResultUtils.returnError("原手机号与您信息不一致");
				}
				
				//验证 验证码是否正确
				result=this.checkMsg(oldPhone, oldCode, "PHPF2017070305");//旧手机号
				
				 if(result.getCode()==1){
					 
					 result=this.checkMsg(newPhone, newCode, "PHPF2017070306");//新手机号
					 
					 if(result.getCode()==1){
						 
						 //修改手机号
						 industryAssociation.setPhone(newPhone);
						 try {
							
							 this.saveAndModify(industryAssociation);//保存行业公会信息
							 
							 //根据原手机号查询user信息
							 User olduser=userService.getUserByName(oldPhone);
							//根据新手机号查询user信息
							 User newuser=userService.getUserByName(newPhone);
							 if(null==olduser){
								 return ResultUtils.returnError("信息有误");
							 }
							 if(null!=newuser){
								 return ResultUtils.returnError("新手机号已存在，请重新输入");
							 }
							 
							 olduser.setUserName(newPhone);
							 userService.saveAndModify(olduser);//保存user信息
							 
							 
							 result.setCode(1);
							 result.setMsg("手机号修改成功,请重新登录");
							 
						} catch (Exception e) {
							
							e.printStackTrace();
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
							logger.error("手机号修改有误：" + e.getMessage() + "时间：" + new Date());
							result.setCode(0);
							result.setMsg("手机号修改失败");
						}
						 
					 }else{
						 	result.setCode(0);
							result.setMsg("新手机号验证码有误");
					 }
					 
				 }else{
					 	result.setCode(0);
						result.setMsg("旧手机号验证码有误");
				 }
		
		return result;
	}

	/**
	 * 行业公会后台--安全设置-绑定银行卡   手机号发送验证码   PHPF2017070307
	 * @param bangPhone
	 * @return
	 */
	@Override
	public Result sendMsgForBangPhone(String bangPhone, String codeType, HttpServletRequest request) {
		Result result = new Result();
		
		//验证手机号
		if(StringUtils.isEmptyOrWhitespaceOnly(bangPhone)){
			return ResultUtils.returnError("绑卡手机号不能为空");
		}

		String regex="^1[34578]\\d{9}$";
		if(!bangPhone.matches(regex)){
			return ResultUtils.returnError("请输入正确的手机号码,1(3,4,5,7,8)开头");
		}
		
		//登录信息取值判断手机号是否是该行业公会
				User user=(User)request.getSession().getAttribute("user");
				String phone=user.getUserName();
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("phone",phone);
				IndustryAssociation industryAssociationLog=myIndustryAssociationService.getAssociationByPhone(param);
				if(null==industryAssociationLog){
					return ResultUtils.returnError("登录信息有误");
				}
				if(!bangPhone.equals(industryAssociationLog.getPhone())){
					return ResultUtils.returnError("绑定银行卡手机号与用户信息不一致");
				}
		
		try {
			String msg_url = this.initParamPc.getProperties().getProperty("send_msg_url");//发送短信路径
			String url = msg_url+"?phone="+bangPhone+"&codeType="+codeType;
			String sendGet = HttpClientUtils.getHttpsToGet(url);
			JSONObject json = JSON.parseObject(sendGet);
			result.setCode(Integer.parseInt(json.get("code").toString()));
			result.setMsg(json.get("msg").toString());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("调用发送验证码接口异常"+e.getMessage());
			
			result.setCode(0);
			result.setMsg("绑定银行卡手机号验证码发送失败");
		}
		
		return result;
	}

	/**
	 * 行业公会后台--安全设置-绑定银行卡
	 */
	@Override
	@Transactional
	public Result addIndustryAssociationBankCard(BankCard bankCard, String bangPhone, String phoneCode, IndustryAssociation industryAssociation) {
		
		 Result result=new Result();
		 
		//去除空格
			String bankNo=bankCard.getBankNo().replaceAll(" ", "");//银行卡号
			 String card=bankCard.getCard().replaceAll(" ", "");//身份证号
			 String name=bankCard.getName().replaceAll(" ", "");//开户人姓名
			 String bank=bankCard.getBank().replaceAll(" ", "");//开户银行
		 
			 if("".equals(bangPhone)){
				return ResultUtils.returnError("手机号码不能为空");
		 	}
		 	if("".equals(phoneCode)){
				return ResultUtils.returnError("验证码不能为空");
		 	}
			
		 	if(null == bankNo || bankNo.equals("")){
				return ResultUtils.returnError("银行卡号不能为空");
			}else{
				String regex="^\\d{16}|\\d{18}|\\d{19}$";
				if(!bankNo.matches(regex)){
					return ResultUtils.returnError("请输入正确的银行卡号16,18,19位");
				}
			}
		 	if(null == card || card.equals("")){
			 	return ResultUtils.returnError("身份证号不能为空");
			}
		 	if(null == name || name.equals("")){
			 	return ResultUtils.returnError("开户人姓名不能为空");
			}else{
				if(name.length()<1 || name.length()>20){
					return ResultUtils.returnError("开户人姓名请输入1-20位字符");
				}
			}
		 	if(null == bank || bank.equals("")){
			 	return ResultUtils.returnError("开户银行不能为空");
			}
		 
		 //验证身份
			String industryAssociationName=industryAssociation.getPayname();//姓名
			String industryAssociationCard=industryAssociation.getCard();//身份证号
			
			if(null==industryAssociationName||null==industryAssociationCard){
				return ResultUtils.returnError("您的信息不完善，请先进行身份认证");
			}else if(industryAssociationName.equals("")||industryAssociationCard.equals("")){
				return ResultUtils.returnError("您的信息不完善，请先进行身份认证");
			}
			
			 if (!name.equals(industryAssociationName)) {
					return ResultUtils.returnError("用户姓名与开户人姓名不一致！");
				}
			 if (!card.toUpperCase().equals(industryAssociationCard.toUpperCase())) {
					return ResultUtils.returnError("用户身份证号与输入身份证号不一致！");
				}
		
		 
			 List<Map<String,Object>> bankCardList=myBankCardService.getBankCardByIndustryAssociationId(industryAssociation.getId());//根据行业公会ID获取银行卡信息
				
				if(bankCardList.size()>0){//已经绑定银行卡信息
					
					return ResultUtils.returnError("您已绑定银行卡，请勿重复操作");
				}
		 
				//验证 验证码是否正确
					result=this.checkMsg(bangPhone, phoneCode, "PHPF2017070307");//绑卡手机号
					
					 if(result.getCode()!=1){	
						 
							return ResultUtils.returnError("绑卡手机号验证码有误");
					 }
				
				
				//三要素验证
					String three_element_url = (String) initParamPc.getProperties().get("three_element_url");// 获取三要素地址
					// 初始接口参数
					List<NameValuePair> callpayParame = new ArrayList<NameValuePair>();
					callpayParame.add(new BasicNameValuePair("name", name));//姓名
					callpayParame.add(new BasicNameValuePair("cardNum", card));//身份证号
					callpayParame.add(new BasicNameValuePair("bankCardNo", bankNo));//银行卡号
					Long industryAssociation_id=industryAssociation.getId();//批发商id
					try {
						
						logger.info("行业公会ID：" + industryAssociation_id + "，参数：" +callpayParame.toString() + ",三要素地址：" + three_element_url
								+ "/mobile/view/jdCheck/checkJDCard");
						String checkResult = HttpClientObject.sendGet(three_element_url + "/mobile/view/jdCheck/checkJDCard",
								callpayParame);
						logger.info("行业公会ID：" + industryAssociation_id + "，参数：" + callpayParame.toString() + ",返回值："
								+ checkResult.toString());
						JSONObject jsonObj = JSON.parseObject(checkResult);
						Integer result_code = jsonObj.getInteger("code");
						String msg = jsonObj.getString("msg");
						if (result_code != 1&&result_code != 3) {
							logger.info("验证三要素：" + msg.toString() == null ? "获取错误" : msg.toString());
							return ResultUtils.returnError("银行卡信息验证失败，请核对信息！");
						}
						
						
					} catch (Exception e) {
						for (StackTraceElement st : e.getStackTrace()) {
							logger.error(st.toString());
						}
						logger.error("三要素接口异常,错误信息：" + e.getMessage() + "时间：" + new Date());
						return ResultUtils.returnError("三要素接口异常,错误信息：" + e.getMessage());
					}
				 
				 //绑定银行卡
				try {
						
					 bankCard.setIndustryAssociationId(industryAssociation);//行业公会id
					 bankCard.setStatus(1);//0 绑卡记录 1正在绑定中的银行卡
					 this.bankCardService.saveAndModify(bankCard);
					 result.setCode(1);
					 result.setMsg("绑定银行卡成功");
					
				} catch (Exception e) {
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
					logger.error("绑定银行卡有误：" + e.getMessage() + "时间：" + new Date());
					return ResultUtils.returnError("绑定银行卡有误：" + e.getMessage());
				}
				
				return result;
	}

	

	@Override
	public EasyuiResult<List<IndustryAssociation>> findIndustryAssociationList(Map<String, Object> map, Integer page,
			Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<IndustryAssociation> specification = DynamicSpecifications.bySearchFilter(filter.values(),
				IndustryAssociation.class);
		Page<IndustryAssociation> userPage = industryAssociationDao.findAll(specification, new PageRequest(page - 1, rows,
				new Sort(Direction.DESC, new String[] { "createdTime" })));
		return EasyUtils.returnPage(IndustryAssociation.class, userPage);
	}

	/**
	 * 运行专员添加行业公会
	 */
	@Override
	@Transactional
	public  org.alqframework.webmvc.springmvc.Result addIndustryAssociation(IndustryAssociation industryAssociation,String onepassword,String twopassword) {
		try { 
		if(industryAssociation==null){
			return SpringMVCUtils.returnError("行业公会参数不能为空");
		}
		if(industryAssociation.getPhone()==null||"".equals(industryAssociation.getPhone())){
			return SpringMVCUtils.returnError("行业公会手机号不能为空");
		}
		if(industryAssociation.getName()==null||"".equals(industryAssociation.getName().trim())){
			return SpringMVCUtils.returnError("行业公会名称不能为空");
		}
		if(onepassword==null||"".equals(onepassword)){
			return SpringMVCUtils.returnError("请输入初始密码");
		}
		if(twopassword==null||"".equals(twopassword)){
			return SpringMVCUtils.returnError("请输入确认登录密码");
		}
		if(!onepassword.equals(twopassword)){
			return SpringMVCUtils.returnError("两次密码不一致，请重新输入");
		}
		if(industryAssociation.getPayname()==null||"".equals(industryAssociation.getPayname().trim())){
			return SpringMVCUtils.returnError("收款人姓名不能为空");
		}
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("name", industryAssociation.getName().trim());
		if(myIndustryAssociationService.findAssociationLikeName(params)>0){
			return SpringMVCUtils.returnError("该行业公会名称已存在");
		}
		// 判断手机是否符合判断格式
	/*	Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(industryAssociation.getPhone());
		if (!m.matches()) {
			return SpringMVCUtils.returnError("手机号格式不正确");
		} */
		String regex="^1[34578]\\d{9}$";
		if(!industryAssociation.getPhone().matches(regex)){
			return SpringMVCUtils.returnError("手机号格式不正确");
		}
		//身份证
		Pattern p1=Pattern.compile("(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)");
		Matcher m1 = p1.matcher(industryAssociation.getCard());
		if (!m1.matches()) {
			return SpringMVCUtils.returnError("身份证号格式化不正确");
		}
		User user= userService.getUserByName(industryAssociation.getPhone());
		if(user!=null){
			return SpringMVCUtils.returnError("该行业公会账号已存在");
		}
		//=============================================姓名身份证验证====================================================
		String two_element_url = (String) initParamPc.getProperties().get("two_element_url");// 获取姓名身份证验证地址
		// 初始接口参数
		List<NameValuePair> callpayParame = new ArrayList<NameValuePair>();
		callpayParame.add(new BasicNameValuePair("name", industryAssociation.getPayname()));
		callpayParame.add(new BasicNameValuePair("idCard", industryAssociation.getCard()));
	
		try {
			
			logger.info("运营专员添加行业公会：" + callpayParame.toString() + ",姓名身份证验证地址：" + two_element_url
					+ "/mobile/view/checkInfoCard/checkUserInfo");
			
			String checkResult = HttpClientObject.sendGet(two_element_url + "/mobile/view/checkInfoCard/checkUserInfo",callpayParame);
			
			logger.info("运营专员添加行业公会参数：" + callpayParame.toString() + ",返回值："
					+ checkResult.toString());
			
			if (!checkResult.equals("true")) {
				logger.info("运营专员添加行业公会：验证姓名身份证失败，请核对信息！");
				return SpringMVCUtils.returnError("验证姓名身份证失败，请核对信息！");
			}
			
		} catch (Exception e) {
			for (StackTraceElement st : e.getStackTrace()) {
				logger.error(st.toString());
			}
			logger.error("姓名身份证验证接口异常,错误信息：" + e.getMessage() + "时间：" + new Date());
			return SpringMVCUtils.returnError("姓名身份证验证接口异常,错误信息：" + e.getMessage());
		}
		
		String password1 = passwordEncoder.encode(onepassword);
		industryAssociation.setPassword(password1);
		//===============================添加===============================
		if(industryAssociation.getId()==null){
			
			if(this.checkHasPhone(industryAssociation.getPhone())!=null){
				return SpringMVCUtils.returnError("该行业公会手机号已存在");
			}
			this.saveAndModify(industryAssociation);
			User user1 = new User();
			user1.setUserName(industryAssociation.getPhone());
			user1.setUserPassword(password1);
			user1.setMobile(industryAssociation.getPhone());
			user1.setTel(industryAssociation.getPhone());
			user1.setRealName(industryAssociation.getName());
			Role role = new Role();
			role.setId(6L);//此id为对应的行业角色id
			user1.setRole(role);
			user1.setAddress("无");
			
			userService.saveAndModify(user1);
			
		}
		return SpringMVCUtils.returnSuccess("行业公会添加成功");
		//===============================编辑===============================
//		if(industryAssociation.getId()!=null){
//			IndustryAssociation industryAssociationdb = this.get(industryAssociation.getId());
//		
//		}
	}catch (Exception e) {
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
		logger.error("添加行业公会"+e.getMessage());
			return SpringMVCUtils.returnError("行业公会添加失败");
		}
		
	}

	/**
	 * 行业公会后台--安全设置-修改银行卡   手机号发送验证码   PHPF2017070308
	 * @param updatePhone
	 * @return
	 */
	@Override
	public Result sendMsgForUpdatePhone(String updatePhone, String codeType, HttpServletRequest request) {
		
		Result result = new Result();
		
		//验证手机号
		if(StringUtils.isEmptyOrWhitespaceOnly(updatePhone)){
			return ResultUtils.returnError("修改银行卡手机号不能为空");
		}

		String regex="^1[34578]\\d{9}$";
		if(!updatePhone.matches(regex)){
			return ResultUtils.returnError("请输入正确的手机号码,1(3,4,5,7,8)开头");
		}
		
		//登录信息取值判断手机号是否是该行业公会
		User user=(User)request.getSession().getAttribute("user");
		String phone=user.getUserName();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("phone",phone);
		IndustryAssociation industryAssociationLog=myIndustryAssociationService.getAssociationByPhone(param);
		if(null==industryAssociationLog){
			return ResultUtils.returnError("登录信息有误");
		}
		if(!updatePhone.equals(industryAssociationLog.getPhone())){
			return ResultUtils.returnError("修改银行卡手机号与用户信息不一致");
		}
		
		try {
			String msg_url = this.initParamPc.getProperties().getProperty("send_msg_url");//发送短信路径
			String url = msg_url+"?phone="+updatePhone+"&codeType="+codeType;
			String sendGet = HttpClientUtils.getHttpsToGet(url);
			JSONObject json = JSON.parseObject(sendGet);
			result.setCode(Integer.parseInt(json.get("code").toString()));
			result.setMsg(json.get("msg").toString());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("调用发送验证码接口异常"+e.getMessage());
			
			result.setCode(0);
			result.setMsg("绑定银行卡手机号验证码发送失败");
		}
		
		return result;
	}
	/**
	 * 行业公会后台--安全设置-修改银行卡
	 * @return
	 */
	@Override
	@Transactional
	public Result updateindustryassociationbankcard(BankCard bankCard, String updatePhone, String phoneCode,
			IndustryAssociation industryAssociation, int updateCount, int updateCountSelect) {
			
		Result result=new Result();
		
			if(updateCount!=updateCountSelect){
				return ResultUtils.returnError("修改次数不一致");
			}
			if(updateCountSelect>=2){
				
				return ResultUtils.returnError("每月修改次数只能2次");
			}
		
		
		 	if("".equals(updatePhone)){
				return ResultUtils.returnError("手机号码不能为空");
		 	}
		 	if("".equals(phoneCode)){
				return ResultUtils.returnError("验证码不能为空");
		 	}
		 	
		//去除空格
			String bankNo=bankCard.getBankNo().replaceAll(" ", "");//银行卡号
			 String card=bankCard.getCard().replaceAll(" ", "");//身份证号
			 String name=bankCard.getName().replaceAll(" ", "");//开户人姓名
			 String bank=bankCard.getBank().replaceAll(" ", "");//开户银行
			
			 if(null == bankNo || bankNo.equals("")){
					return ResultUtils.returnError("银行卡号不能为空");
				}else{
					String regex="^\\d{16}|\\d{18}|\\d{19}$";
					if(!bankNo.matches(regex)){
						return ResultUtils.returnError("请输入正确的银行卡号16,18,19位");
					}
				}
			 if(null == card || card.equals("")){
				 	return ResultUtils.returnError("身份证号不能为空");
				}
			 if(null == name || name.equals("")){
				 	return ResultUtils.returnError("开户人姓名不能为空");
				}else{
					if(name.length()<1 || name.length()>20){
						return ResultUtils.returnError("开户人姓名请输入1-20位字符");
					}
				}
			 if(null == bank || bank.equals("")){
				 	return ResultUtils.returnError("开户银行不能为空");
				}
			 
			 //验证身份
				String industryAssociationName=industryAssociation.getPayname();//姓名
				String industryAssociationCard=industryAssociation.getCard();//身份证号
				
				if(null==industryAssociationName||null==industryAssociationCard){
					return ResultUtils.returnError("您的信息不完善，请先进行身份认证");
				}else if(industryAssociationName.equals("")||industryAssociationCard.equals("")){
					return ResultUtils.returnError("您的信息不完善，请先进行身份认证");
				}
			
			 if (!name.equals(industryAssociationName)) {
					return ResultUtils.returnError("用户姓名与开户人姓名不一致！");
				}
			 if (!card.toUpperCase().equals(industryAssociationCard.toUpperCase())) {
					return ResultUtils.returnError("用户身份证号与输入身份证号不一致！");
				}
			
			//三要素验证
				String three_element_url = (String) initParamPc.getProperties().get("three_element_url");// 获取三要素地址
				// 初始接口参数
				List<NameValuePair> callpayParame = new ArrayList<NameValuePair>();
				callpayParame.add(new BasicNameValuePair("name", name));//姓名
				callpayParame.add(new BasicNameValuePair("cardNum", card));//身份证号
				callpayParame.add(new BasicNameValuePair("bankCardNo", bankNo));//银行卡号
				Long industryAssociation_id=industryAssociation.getId();//批发商id
				try {
					
					logger.info("行业公会ID：" + industryAssociation_id + "，参数：" +callpayParame.toString() + ",三要素地址：" + three_element_url
							+ "/mobile/view/jdCheck/checkJDCard");
					String checkResult = HttpClientObject.sendGet(three_element_url + "/mobile/view/jdCheck/checkJDCard",
							callpayParame);
					logger.info("行业公会ID：" + industryAssociation_id + "，参数：" + callpayParame.toString() + ",返回值："
							+ checkResult.toString());
					JSONObject jsonObj = JSON.parseObject(checkResult);
					Integer result_code = jsonObj.getInteger("code");
					String msg = jsonObj.getString("msg");
					if (result_code != 1&&result_code != 3) {
						logger.info("验证三要素：" + msg.toString() == null ? "获取错误" : msg.toString());
						return ResultUtils.returnError("银行卡信息验证失败，请核对信息！");
					}
					
					
				} catch (Exception e) {
					for (StackTraceElement st : e.getStackTrace()) {
						logger.error(st.toString());
					}
					logger.error("三要素接口异常,错误信息：" + e.getMessage() + "时间：" + new Date());
					return ResultUtils.returnError("三要素接口异常,错误信息：" + e.getMessage());
				}
				

				//验证 验证码是否正确
				result=this.checkMsg(updatePhone, phoneCode, "PHPF2017070308");//绑卡手机号
				
				 if(result.getCode()!=1){	
					 
						return ResultUtils.returnError("修改银行卡手机号验证码有误");
				 }
				 
				 
			 //修改银行卡
			
			try {
				
				Long industryAssociationId=industryAssociation.getId();
				List<BankCard> list=bankCardService.findBankCardByindustryAssociationId(industryAssociationId);//根据行业公会id查询银行卡集合
				if(list.size()<=0){
					return ResultUtils.returnError("查无绑定银行卡");
				}
				
				String [] bids=new String[list.size()];
				for(int i=0;i<list.size();i++){
					bids[i]=list.get(i).getId().toString();
				}
				//修改已绑银行卡状态status  (0 绑卡记录 1正在绑定中的银行卡)
				
				int count=bankCardService.updateBankCardByHunterId(bids);
				if(count<=0){
					logger.error("修改银行卡状态status失败,本次修改共" + count + "条,行业公会id为"+industryAssociationId+"时间：" + new Date());
					return ResultUtils.returnError("修改银行卡状态失败");
				}
				logger.error("修改银行卡状态status,本次修改共" + count + "条,行业公会id为"+industryAssociationId+"时间：" + new Date());
			
				//保存新记录	
				BankCard bankCard2=new BankCard();
				
				 bankCard2.setBankNo(bankNo);
				 bankCard2.setBank(bank);
				 bankCard2.setName(name);
				 bankCard2.setCard(card);
				 bankCard2.setStatus(1);//0 绑卡记录 1正在绑定中的银行卡
				 bankCard2.setIndustryAssociationId(industryAssociation);//行业公会id
				 
				 this.bankCardService.saveAndModify(bankCard2);
				 result.setCode(1);
				 result.setMsg("修改银行卡成功");
				
			} catch (Exception e) {
				e.printStackTrace();
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
				logger.error("修改银行卡有误：" + e.getMessage() + "时间：" + new Date());
				return ResultUtils.returnError("修改银行卡有误：" + e.getMessage());
			}
			return result;
	}

	/**
	 * 查看是否存在该手机号
	 */
	@Override
	public IndustryAssociation checkHasPhone(String phone) {
		// TODO Auto-generated method stub
		return industryAssociationDao.checkHasPhone(phone);
	}

	@Override
	public IndustryAssociation getAssociationById(Long id) {
		// TODO Auto-generated method stub
		return industryAssociationDao.findOne(id);
	}

	@Override
	public IndustryAssociation getRowLock(Long id) {
		// TODO Auto-generated method stub
		return industryAssociationDao.getRowLock(id);
	}

	@Override
	public IndustryAssociation getIndustryAssociationByPhone(String phone) {
		return this.industryAssociationDao.getIndustryAssociationByPhone(phone);
	}

}
