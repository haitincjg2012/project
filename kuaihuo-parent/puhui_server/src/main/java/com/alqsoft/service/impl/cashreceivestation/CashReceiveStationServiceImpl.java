package com.alqsoft.service.impl.cashreceivestation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.net.html.HttpClientUtils;
import org.alqframework.pay.alipay.v48.util.UtilDate;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.alqframework.utils.DoubleUtils;
import org.alqframework.utils.UniqueUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alqsoft.dao.cashreceivestation.CashReceiveStationDao;
import com.alqsoft.entity.bankcard.BankCard;
import com.alqsoft.entity.cashreceivestation.CashReceiveStation;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.industryassociation.IndustryAssociation;
import com.alqsoft.entity.industrymoney.IndustryMoney;
import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.membermoney.MemberMoney;
import com.alqsoft.entity.user.User;
import com.alqsoft.init.InitParamPc;
import com.alqsoft.mybatis.service.industryassociation.MyIndustryAssociationService;
import com.alqsoft.rpc.pay.RpcPayService;
import com.alqsoft.service.bankcard.BankCardService;
import com.alqsoft.service.cashreceivestation.CashReceiveStationService;
import com.alqsoft.service.hunter.HunterService;
import com.alqsoft.service.industryassociation.IndustryAssociationService;
import com.alqsoft.service.industrymoney.IndustryMoneyService;
import com.alqsoft.service.member.MemberService;
import com.alqsoft.service.membermoney.MemberMoneyService;
import com.alqsoft.utils.CodeConstExt;
import com.alqsoft.utils.CommUtils;
import com.alqsoft.utils.CopyBeanUtil;
import com.mysql.jdbc.StringUtils;

@Service
@Transactional(readOnly=true)
public class CashReceiveStationServiceImpl implements CashReceiveStationService {

	private static Logger logger = LoggerFactory.getLogger(CashReceiveStationServiceImpl.class);
	
	@Autowired
	private CashReceiveStationDao cashReceiveStationDao;
	
	@Autowired
	private InitParamPc initParamPc;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BankCardService bankCardService;
	
	@Autowired
	private HunterService hunterService;
	
	@Autowired
	private RpcPayService rpcPayService;
	
	@Autowired
	private MemberMoneyService memberMoneyService;
	
	@Autowired
	private IndustryMoneyService industryMoneyService;
	
	@Autowired
	private IndustryAssociationService industryAssociationService;
	
	@Autowired
	private MyIndustryAssociationService myIndustryAssociationService;
	
	
	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CashReceiveStation get(Long arg0) {
		// TODO Auto-generated method stub
		return cashReceiveStationDao.findOne(arg0);
	}

	@Override
	@Transactional
	public CashReceiveStation saveAndModify(CashReceiveStation arg0) {
		CashReceiveStation cashReceiveStation=null;
		try {
			cashReceiveStation=cashReceiveStationDao.save(arg0);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			
		}
		
		return cashReceiveStation;
	}
	

	/**
	 * 批发商个人中心-提现  手机号码发送验证码   PHPF2017070303
	 * @param moneyPhone
	 * @return
	 */
	@Override
	public Result sendMsgForMoneyPhone(Member member, String moneyPhone, String codeType) {
		
		Result result = new Result();
		
		Hunter hunter1=member.getHunter();
		
		if(hunter1==null){
			return ResultUtils.returnError("角色异常");
		}
		
		if(StringUtils.isEmptyOrWhitespaceOnly(moneyPhone)){
			return ResultUtils.returnError("提现手机号不能为空");
		}
		
		Member member1=memberService.get(member.getId());//获取用户信息

		if(null==member1){
			return ResultUtils.returnError("用户信息不存在");
		}
		
		if("".equals(member1.getPhone()) || null==member1.getPhone()){
			return ResultUtils.returnError("用户手机号为空");
		}
		
		if(!moneyPhone.equals(member1.getPhone())){
			return ResultUtils.returnError("和您原手机号信息不符");
		}
		
		try {
			String msg_url = this.initParamPc.getProperties().getProperty("send_msg_url");//发送短信路径
			String url = msg_url+"?phone="+moneyPhone+"&codeType="+codeType;
			String sendGet = HttpClientUtils.getHttpsToGet(url);
			JSONObject json = JSON.parseObject(sendGet);
			result.setCode(Integer.parseInt(json.get("code").toString()));
			result.setMsg(json.get("msg").toString());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("调用发送验证码接口异常"+e.getMessage());
			return ResultUtils.returnError("发送失败");
		}
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
			return ResultUtils.returnError("调用验证验证码接口异常");
		}
	}
	
	/**
	 * 获取批发商流水号
	 * 拼接"H" 保证唯一
	 * @return
	 */
	private String getMerSeqId(){
		String merSeqId = "KHPFTX"+UtilDate.getOrderNum()+UtilDate.getThree();
		return merSeqId;
	}
	/**
	 * 获取行业协会流水号
	 * 拼接"I" 保证唯一
	 * @return
	 */
	private String getInduetryAssociationMerSeqId(){
		String merSeqId = "PHPFIC"+UniqueUtils.getOrder();
		return merSeqId;
	}
	/**
	 * 生成时间
	 */
	private  Date getMerDateYYYY() throws ParseException{
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String merDate = sf1.format(date);
		Date date2 = sf1.parse(merDate);
		return date2;
	}
	/**
	 * 批发商个人中心--提现
	 * @param phone
	 * @param code
	 * @param cashReceiveStation
	 * @return 
	 * @throws ParseException 
	 */
	@Override
	@Transactional
	public Result cashReceiveStationMoney(CashReceiveStation cashReceiveStation, Double moneyD, String phone, String code, Long cardId){
		
		logger.info("进入server服务提现方法   用户ip:"+cashReceiveStation.getCreatedIp()+"用户ID为："+cashReceiveStation.getCssId()+"手机号："+phone+"银行卡号："+cardId+"时间："+new Date());
		
		Result result=new Result();
		CashReceiveStation crsEntity=new CashReceiveStation();
		String ipAddr=cashReceiveStation.getCreatedIp();//用户ip
		Map<String,String> parem = new HashMap<String, String>();
		CashReceiveStation cashReceiveStation1=null;
		Hunter hunter1=null;
		Hunter hunter=null;
		MemberMoney memberMoney1=null;
		MemberMoney memberMoney=new MemberMoney();
		Long leftDepositMoney=0L;
		Long money=0L;
		Long haveDepositMoney=0L;
		try {
			logger.info("进入参数验证方法,时间:"+new Date()+",手机号："+phone);
				//(1)验证验证码
				 result=this.checkMsg(phone, code, "PHPF2017070303");//提现手机号
				 if(!(result.getCode() == 1)){
					 logger.info(phone+"提现手机验证码有误"+code+",时间:"+new Date()+result.getCode());
					 return ResultUtils.returnError("提现手机验证码有误");
				 }
				 
				 //(2)验证是否注入pay工程
				 if (rpcPayService==null) {
						logger.info("pay工程请求失败,时间:"+new Date());
					 return ResultUtils.returnError("pay工程请求失败");
				 }
				//(3)验证身份
				Long hunterId=cashReceiveStation.getCssId().longValue();//用户id
				
				hunter=hunterService.getRowLock(hunterId);//获取批发商信息
				if(null==hunter){//用户信息是否存在
					logger.info("用户信息不存在,时间:"+new Date());
					return ResultUtils.returnError("用户信息不存在");
				}
					String card=hunter.getCard();//身份证号
					String name=hunter.getName();//姓名
					
					if("".equals(card) || "".equals(name)){//是否认证了身份
						logger.info("未进行身份认证,时间:"+new Date());
						return ResultUtils.returnError("您未进行身份认证，请先进行身份认证");
					}
					
				//(4)验证金额	
					money = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(moneyD*100, 0))))+"");//用户输入提现金额，以分为单位
				
				Long money1=hunter.getLeftDepositMoney();//剩余可提现金额
				
				if(null==money1){ money1=0L;}
				
				Long money2=money1-money;
				if(money2<0){
					logger.info("用户剩余可提现金额不足,用户输入提现金额 为："+money+"单位 分,"+"剩余可提现金额为："+money1+"单位 分,"+"可提现减输入金额的差额为："+money2+"单位 分,"+"时间:"+new Date());
					 return ResultUtils.returnError("剩余可提现金额不足");
				 }
				//*******判断单笔提现限额5万 5000000 单位 分*******
				Long dateMoney=5000000L;
				if(money>dateMoney){
					logger.info("单笔提现金额不能超过5万,金额为:"+money+"单位 分,"+"时间:"+new Date());
					 return ResultUtils.returnError("单笔提现金额不能超过5万");
				 }
				//************判断单日限额*************提现金额加今日已提现金额不能超过10000000 单位分 十万
				Long Single_day_limit_hunter=10000000L;//10万 单位 分
				
				//设置日期格式
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");//设置日期格式
				String today = df.format(new Date());
				Date date = df.parse(today);
			  	
			   //查询今日已提现金额
				Long todayCash = cashReceiveStationDao.getTodayCashMoneyByDate(Integer.parseInt(cashReceiveStation.getCssId()+""), date);
					logger.info("查询今日已提现金额为:"+todayCash+"单位 分,"+"时间:"+new Date());
				if(null==todayCash){ todayCash=0L;}
				
				Long totalMoney=money+todayCash;//
				if (totalMoney > Single_day_limit_hunter) {
					logger.info("单日提现金额不能超过10万,提现金额为:"+money+"单位 分,"+"今日已提现金额为:"+todayCash+"单位 分,"+"提现金额加今日已提现金额为:"+totalMoney+"单位 分,"+"时间:"+new Date());
					 return ResultUtils.returnError("单日提现金额不能超过10万,您已超额");
				}
				
				
				//生成订单号
					String merSeqId = getMerSeqId();
						
				//(5)验证（银行信息是否正确）
				BankCard bankCard=bankCardService.get(cardId);//根据银行卡id获取银行卡信息
				if(null == bankCard){
					logger.info("未绑定银行卡,银行卡ID为："+cardId+"的信息不存在,时间:"+new Date());
					return ResultUtils.returnError("请先绑定银行卡");
				}
				
				
				
				String cardNo = bankCard.getBankNo();//银行卡号
			    String bankName = bankCard.getBank();//银行名称
			    String personName = bankCard.getName();//开户人姓名
			    String bankAddr = bankCard.getBankAddr();//银行缩写
			    
			   //******************保存记录表信息******************// 
			    //1.提现记录表（回调之前）
			    if(hunterId.longValue() != bankCard.getHunter().getId().longValue()){//比较信息是否一致
					crsEntity.setCssType(1);//类型1批发商 2行业协会
					crsEntity.setCssId(cashReceiveStation.getCssId());//用户id
					crsEntity.setName(hunter.getName());//用户姓名
					crsEntity.setStatus(3);//1已申请 2打款成功 3打款失败
					crsEntity.setCreatedIp(ipAddr);//用户ip
					crsEntity.setMerSeqId(merSeqId);//订单号
					crsEntity.setCreatedTime(new Date());
					crsEntity.setUpdateTime(new Date());//完成时间
					/*try {
						crsEntity.setCreateTime(getMerDateYYYY());//创建时间
					} catch (ParseException e) {
						e.printStackTrace();
					}*/
					
					crsEntity.setTel(hunter.getPhone());//电话
					crsEntity.setMoney((Integer)money.intValue());//提现金额
					crsEntity.setFeeIncome(500);//手续费5块
					
				//提现时余额------------------
					crsEntity.setCurrentBalance(money1);//提现时余额
					crsEntity.setBankAbbr(bankAddr);//银行缩写
					crsEntity.setFromSystem(CodeConstExt.PAY_SYSTEM_FROM_PHPF);//发起来源
					crsEntity.setComments(hunter.getCard());//身份证号
					
					crsEntity.setDescriber("非法操作,用户银行信息与传递信息不符.");
					this.saveAndModify(crsEntity);
					logger.info("非法操作,用户银行信息与传递信息不符,银行卡ID为："+cardId+"用户ID为："+hunterId+"银行卡绑定的用户ID为："+bankCard.getHunter().getId()+",时间:"+new Date());
					return ResultUtils.returnError("非法操作,用户银行信息与传递信息不符");
				}else{
			    	crsEntity.setBankName(bankName);//银行名称
			    	crsEntity.setPersonName(personName);//收款人姓名
			    	crsEntity.setCardNo(cardNo);//银行卡号
				}
				
		
			    crsEntity.setCssType(1);//类型1批发商 2行业协会
				crsEntity.setCssId(cashReceiveStation.getCssId());//用户id
				crsEntity.setName(hunter.getName());//用户姓名
				crsEntity.setStatus(1);//1已申请 2打款成功 3打款失败
				crsEntity.setDescriber("提现申请中");
				crsEntity.setCreatedIp(ipAddr);//用户ip
				crsEntity.setMerSeqId(merSeqId);//订单号
				crsEntity.setCreatedTime(new Date());
				crsEntity.setUpdateTime(new Date());//完成时间
				/*try {
					crsEntity.setCreateTime(getMerDateYYYY());//创建时间
				} catch (ParseException e) {
					e.printStackTrace();
				}*/
				crsEntity.setTel(hunter.getPhone());//电话
				crsEntity.setFeeIncome(500);//手续费5块
				crsEntity.setMoney((Integer)money.intValue());//提现金额
				
				
			// 提现时余额--------------------
				crsEntity.setCurrentBalance(money1);//提现时余额
				crsEntity.setBankAbbr(bankAddr);//银行缩写
				crsEntity.setFromSystem(CodeConstExt.PAY_SYSTEM_FROM_PHPF);//发起来源
				crsEntity.setComments(hunter.getCard());//身份证号
				
				
				 cashReceiveStation1=this.saveAndModify(crsEntity);//保存提现记录表
				if(null==cashReceiveStation1){
					logger.error("保存提现记录表发生异常,用户ID为:"+crsEntity.getCssId()+"姓名："+crsEntity.getPersonName()+",时间:"+new Date());
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return ResultUtils.returnError("保存提现记录表发生异常");
				}else{
					logger.info("保存提现记录表成功,用户ID为："+crsEntity.getCssId()+"姓名："+crsEntity.getPersonName()+",时间:"+new Date());
				}
					
				//（2）扣除用户金额  保存用户信息（回调之前）
				leftDepositMoney=hunter.getLeftDepositMoney();//剩余可提现
				if(null==leftDepositMoney){ leftDepositMoney=0L;}
				leftDepositMoney=leftDepositMoney-money;
				
				haveDepositMoney=hunter.getHaveDepositMoney();//已提现
				if(null==haveDepositMoney){ haveDepositMoney=0L;}
				
				haveDepositMoney=haveDepositMoney+money;
				hunter.setLeftDepositMoney(leftDepositMoney);
				hunter.setHaveDepositMoney(haveDepositMoney);
				hunter1=hunterService.saveAndModify(hunter);//保存用户信息
				if(null==hunter1){
					logger.error("保存用户提现变动金额发生异常,用户ID为:"+hunter.getId()+"姓名："+hunter.getName()+",时间:"+new Date());
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return ResultUtils.returnError("保存提现记录表发生异常");
				}else{
					logger.info("保存用户提现变动金额成功,用户ID为："+hunter.getId()+"姓名："+hunter.getName()+",时间:"+new Date());
				}
				
				//(3)维护收支表（回调之前）
				memberMoney.setHunter(hunter);//批发商id
				memberMoney.setFee(500L);//手续费5块
				memberMoney.setType(4);//0 支付订单  1支付定金  2定金退货  3退货  4提现  5分润
				memberMoney.setStatus(0);//状态 0申请中  1成功   2失败
				memberMoney.setMoney(money);//金额
				memberMoney.setOrderNo(merSeqId);//订单号
				memberMoney.setAfterMoney(money2);//变动后金额
				memberMoney.setBeforeMoney(money1);//变动前金额
				memberMoney1=memberMoneyService.saveAndModify(memberMoney);//保存
				if(null==memberMoney1){
					logger.error("保存用户收支明细表发生异常,用户ID为:"+memberMoney.getHunter().getId()+"姓名："+memberMoney.getHunter().getName()+",时间:"+new Date());
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return ResultUtils.returnError("保存提现记录表发生异常");
				}else{
					logger.info("保存用户收支明细表成功,用户ID为："+memberMoney.getHunter().getId()+"姓名："+memberMoney.getHunter().getName()+",时间:"+new Date());
				}
				
				//crsEntity.setThirdPayType(CodeConstExt.YLcash);//易联接口
				crsEntity.setFromSystem(CodeConstExt.PAY_SYSTEM_FROM_PHPF);//发起来源
				crsEntity.setCurrentBalance(money1);//提现时余额
				
				
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.info("调用pay工程之前，保存记录 出现异常，京东代付,方法异常,提现事务回滚,订单号:"+crsEntity.getMerSeqId()+
					",卡号"+crsEntity.getCardNo()+",用户id:"+crsEntity.getCssId());
			logger.error("订单号:"+crsEntity.getMerSeqId()+",提现发生异常:"+e.getMessage());
			return ResultUtils.returnError("申请提现失败");
		}
		
		try{
			

			//调用打款方法
			logger.info("调用pay工程打款方法 订单号:"+crsEntity.getMerSeqId()+"提现金额："+crsEntity.getMoney()+"用户ID为："+crsEntity.getCssId()+"用户ip:"+crsEntity.getCreatedIp()
						+"用户姓名："+hunter.getName()+"银行卡号："+crsEntity.getCardNo()+"时间："+new Date());
			
				CashReceiveStation crs = new CashReceiveStation();
				
				CopyBeanUtil.Copy(crs, crsEntity, true);
				
				crs.setMoney(crs.getMoney()-crs.getFeeIncome());//扣除手续费
				result=rpcPayService.cashService(crs,parem);
				logger.info("调用pay工程,请求返回结果处理开始**********");
				if (null !=result) {
					//回调结果
					if (1 == result.getCode().intValue()) {//请求失败（0 成功 ; 1 失败）

						logger.info("调用pay工程，提现异常,用户ID为：" + hunter.getId() + "返回参数Code：" + result.getCode() + "返回参数Msg："
								+ result.getMsg() + "返回参数Content:" + result.getContent() + ",时间:" + new Date());
						
						//CashReceiveStation cashReceiveStation2 = (CashReceiveStation) result.getContent();
						//1.提现记录表(修改状态)
						cashReceiveStation1.setStatus(3);//1已申请 2打款成功 3打款失败
						cashReceiveStation1.setDescriber("打款失败");
						this.saveAndModify(cashReceiveStation1);
						logger.info("调用pay工程请求失败业务处理，保存提现记录表成功,ID为:" + cashReceiveStation1.getId() + ",订单号："
								+ cashReceiveStation1.getMerSeqId());
						//2.用户表(给用户加钱)
						Long leftDepositMoney1 = hunter1.getLeftDepositMoney();//剩余可提现
						if (null == leftDepositMoney1) {
							leftDepositMoney1 = 0L;
						}
						leftDepositMoney = leftDepositMoney1 + money;

						Long haveDepositMoney1 = hunter1.getHaveDepositMoney();//已提现
						if (null == haveDepositMoney1) {
							haveDepositMoney1 = 0L;
						}
						haveDepositMoney = haveDepositMoney1 - money;

						hunter1.setLeftDepositMoney(leftDepositMoney);
						hunter1.setHaveDepositMoney(haveDepositMoney);
						hunterService.saveAndModify(hunter1);
						logger.info("调用pay工程请求失败业务处理，保存用户信息成功,用户ID:" + hunter1.getId() + "用户姓名：" + hunter1.getName());
						//3.收支明细表(修改状态，修改变动金额)
						memberMoney1.setFee(500L);//手续费
						memberMoney1.setType(4);//0 支付订单  1支付定金  2定金退货  3退货  4提现  5分润
						memberMoney1.setStatus(2);//状态 0申请中  1成功   2失败
						memberMoneyService.saveAndModify(memberMoney1);
						logger.info("调用pay工程请求失败业务处理，保存收支明细表成功,ID:" + memberMoney1.getId() + "用户ID"
								+ memberMoney1.getHunter().getId());

						return ResultUtils.returnError("交易失败");
					} else if (0 == result.getCode().intValue()) {//请求成功（0 成功 ; 1 失败）
						
						logger.info("调用pay工程，请求成功,用户ID为：" + hunter.getId() + "返回参数Code：" + result.getCode() + "返回参数Msg："
								+ result.getMsg() + "返回参数Content:" + result.getContent() + ",时间:" + new Date());
						
						String msg=result.getMsg().toLowerCase();
						
						logger.info("调用pay工程，请求成功,msg转换小写为:" +msg);
						
						if("success".equals(msg)){//请求成功
							
							//CashReceiveStation cashReceiveStation2 = (CashReceiveStation) result.getContent();
							//成功
							logger.info("调用pay工程，提现成功,用户ID为：" + hunter.getId() + "返回参数Code：" + result.getCode() + "返回参数Msg："
									+ result.getMsg() + "返回参数Content:" + result.getContent() + ",时间:" + new Date());
							//1.提现记录表(修改状态)
							cashReceiveStation1.setStatus(1);//1已申请 2打款成功 3打款失败
							cashReceiveStation1.setDescriber("提现申请中");
							this.saveAndModify(cashReceiveStation1);
							logger.info("调用pay工程请求成功业务处理，保存提现记录表成功,ID为:" + cashReceiveStation1.getId() + ",订单号："
									+ cashReceiveStation1.getMerSeqId());
							//2.用户表(不变)

							//3.收支明细表(修改状态)
							memberMoney.setType(4);//0 支付订单  1支付定金  2定金退货  3退货  4提现  5分润
							memberMoney.setStatus(0);//状态 0申请中  1成功   2失败
							memberMoneyService.saveAndModify(memberMoney);
							logger.info("调用pay工程请求成功业务处理，保存收支明细表成功,ID:" + memberMoney1.getId() + "用户ID"
									+ memberMoney1.getHunter().getId());

							result.setCode(1);//1成功 0失败
							result.setMsg("提现申请处理中");
							
						}else if("fail".equals(msg)){//请求申请失败
							
							//CashReceiveStation cashReceiveStation2 = (CashReceiveStation) result.getContent();
							//1.提现记录表(修改状态)
							cashReceiveStation1.setStatus(3);//1已申请 2打款成功 3打款失败
							cashReceiveStation1.setDescriber("打款失败");
							this.saveAndModify(cashReceiveStation1);
							logger.info("调用pay工程请求失败业务处理，保存提现记录表成功,ID为:" + cashReceiveStation1.getId() + ",订单号："
									+ cashReceiveStation1.getMerSeqId());
							//2.用户表(给用户加钱)
							Long leftDepositMoney1 = hunter1.getLeftDepositMoney();//剩余可提现
							if (null == leftDepositMoney1) {
								leftDepositMoney1 = 0L;
							}
							leftDepositMoney = leftDepositMoney1 + money;

							Long haveDepositMoney1 = hunter1.getHaveDepositMoney();//已提现
							if (null == haveDepositMoney1) {
								haveDepositMoney1 = 0L;
							}
							haveDepositMoney = haveDepositMoney1 - money;

							hunter1.setLeftDepositMoney(leftDepositMoney);
							hunter1.setHaveDepositMoney(haveDepositMoney);
							hunterService.saveAndModify(hunter1);
							logger.info("调用pay工程请求失败业务处理，保存用户信息成功,用户ID:" + hunter1.getId() + "用户姓名：" + hunter1.getName());
							//3.收支明细表(修改状态，修改变动金额)
							memberMoney1.setFee(500L);//手续费
							memberMoney1.setType(4);//0 支付订单  1支付定金  2定金退货  3退货  4提现  5分润
							memberMoney1.setStatus(2);//状态 0申请中  1成功   2失败
							memberMoneyService.saveAndModify(memberMoney1);
							logger.info("调用pay工程请求失败业务处理，保存收支明细表成功,ID:" + memberMoney1.getId() + "用户ID"
									+ memberMoney1.getHunter().getId());

							return ResultUtils.returnError("交易失败");
							
						}else{//其他情况 
							//CashReceiveStation cashReceiveStation2 = (CashReceiveStation) result.getContent();
							//成功
							logger.info("调用pay工程，提现成功,用户ID为：" + hunter.getId() + "返回参数Code：" + result.getCode() + "返回参数Msg："
									+ result.getMsg() + "返回参数Content:" + result.getContent() + ",时间:" + new Date());
							//1.提现记录表(修改状态)
							cashReceiveStation1.setStatus(1);//1已申请 2打款成功 3打款失败
							cashReceiveStation1.setDescriber("提现申请中");
							this.saveAndModify(cashReceiveStation1);
							logger.info("调用pay工程请求成功业务处理，保存提现记录表成功,ID为:" + cashReceiveStation1.getId() + ",订单号："
									+ cashReceiveStation1.getMerSeqId());
							//2.用户表(不变)

							//3.收支明细表(修改状态)
							memberMoney.setType(4);//0 支付订单  1支付定金  2定金退货  3退货  4提现  5分润
							memberMoney.setStatus(0);//状态 0申请中  1成功   2失败
							memberMoneyService.saveAndModify(memberMoney);
							logger.info("调用pay工程请求成功业务处理，保存收支明细表成功,ID:" + memberMoney1.getId() + "用户ID"
									+ memberMoney1.getHunter().getId());

							result.setCode(1);
							result.setMsg("提现申请处理中");
						}
						
						
					}else{
						
						logger.info("调用pay工程,请求返回结果状态code有误，(1:请求失败;0:请求成功)code为："+result.getCode());
					return ResultUtils.returnError("调用pay工程,请求返回结果状态code有误");
					} 
					
					
				}else {
					//失败处理
					logger.info("调用pay工程,请求返回结果result为空");
					return ResultUtils.returnError("调用pay工程,请求返回结果result为空");
				}
				
		}catch(Exception e){
			e.printStackTrace();
			logger.info("调用pay工程之后  出现异常，京东代付,方法异常,提现事务回滚,订单号:"+crsEntity.getMerSeqId()+
					",卡号"+crsEntity.getCardNo()+",用户id:"+crsEntity.getCssId());
			logger.error("订单号:"+crsEntity.getMerSeqId()+",提现发生异常:"+e.getMessage());
			return ResultUtils.returnError("申请提现失败");
		}
		
		return result;
	}

	/**
	 * 根据订单号获取记录ID
	 */
	@Override
	public Long getIDByMerSeqId(String custOrderId) {
		// TODO Auto-generated method stub
		return cashReceiveStationDao.getIDByMerSeqId(custOrderId);
	}
	/**
	 * 提现加锁
	 */
	@Override
	public CashReceiveStation getRowLock(Long id) {
		
		return cashReceiveStationDao.getRowLock(id);
	}
	/**
	 * 行业协会后台--提现 手机号发送验证码   PHPF2017070304
	 * @param tixianPhone
	 * @return
	 */
	@Override
	public Result sendMsgForUpdatePhone(String tixianPhone, String codeType, HttpServletRequest request) {
		Result result = new Result();
		
		//验证手机号
		if(StringUtils.isEmptyOrWhitespaceOnly(tixianPhone)){
			return ResultUtils.returnError("提现手机号不能为空");
		}

		String regex="^1[34578]\\d{9}$";
		if(!tixianPhone.matches(regex)){
			return ResultUtils.returnError("请输入正确的手机号码,1(3,4,5,7,8)开头");
		}
		
		//登录信息取值判断手机号是否是该行业协会
		User user=(User)request.getSession().getAttribute("user");
		String phone=user.getUserName();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("phone",phone);
		IndustryAssociation industryAssociation=myIndustryAssociationService.getAssociationByPhone(param);
		if(null==industryAssociation){
			return ResultUtils.returnError("登录信息有误");
		}
		
		if(!tixianPhone.equals(industryAssociation.getPhone())){
			return ResultUtils.returnError("提现手机号不一致");
		}
		
		try {
			String msg_url = this.initParamPc.getProperties().getProperty("send_msg_url");//发送短信路径
			String url = msg_url+"?phone="+tixianPhone+"&codeType="+codeType;
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
	 *  行业协会后台--提现
	 */
	@Override
	@Transactional
	@Async
	public Result industryAssociationCashmoney(String tixianPhone, String feephonecode, Long bankCardId,
			Long industryAssociationId, Double moneyD, String bankNo, String name, String card, String bank,
			HttpServletRequest request) {
		//参数验证
		
		//验证角色登录是否异常（待做）
				User user=(User)request.getSession().getAttribute("user");
				String phone=user.getUserName();
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("phone",phone);
				IndustryAssociation industryAssociationLog=myIndustryAssociationService.getAssociationByPhone(param);
				if(null==industryAssociationLog){
					return ResultUtils.returnError("登录信息有误");
				}
		
		
		if("".equals(tixianPhone)){
			return ResultUtils.returnError("手机号码不能为空");
		}
		if("".equals(feephonecode)){
			return ResultUtils.returnError("验证码不能为空");
		}
		
		if(industryAssociationId==null||"".equals(industryAssociationId.toString())){
			return ResultUtils.returnError("用户id不能为空");
		}
		Long industryAssociationId1=industryAssociationLog.getId();//登录的行业协会id
		
		if(industryAssociationId.intValue() != industryAssociationId1.intValue()){
			return ResultUtils.returnError("用户id异常");
		}
		if("".equals(bankNo)){
			return ResultUtils.returnError("银行卡号不能为空");
		}
		if("".equals(name)){
			return ResultUtils.returnError("开户人姓名不能为空");
		}
		if("".equals(card)){
			return ResultUtils.returnError("身份证号不能为空");
		}
		if("".equals(bank)){
			return ResultUtils.returnError("银行名称不能为空");
		}

		if(null==bankCardId){
			return ResultUtils.returnError("银行卡id不能为空");
		}
		BankCard bankCard1=bankCardService.get(bankCardId);//获取银行卡信息
		if(null==bankCard1){
			
			return ResultUtils.returnError("银行卡信息不存在");
		}
		Integer status=bankCard1.getStatus();
		if(null==status){
			status=0;
		}
		if(1!=status){
			
			return ResultUtils.returnError("银行卡已被禁用");
		}
		
		if(moneyD==null||"".equals(moneyD.toString())){
			return ResultUtils.returnError("提现金额不能为空");
		}
		if(moneyD<=0){
			return ResultUtils.returnError("提现金额不能为负数");
		}
		if(moneyD<10){
			return ResultUtils.returnError("提现金额请最少输入10元");
		}
		
		String ipAddr = CommUtils.getIpAddr(request);//ip
		
		CashReceiveStation cashReceiveStation=new CashReceiveStation();
			
			cashReceiveStation.setCssId(industryAssociationId.intValue());//用户id
			cashReceiveStation.setCreatedIp(ipAddr);;//用户ip
			
			Result result=new Result();
			CashReceiveStation crsEntity=new CashReceiveStation();
			
			Map<String,String> parem = new HashMap<String, String>();
			CashReceiveStation cashReceiveStation1=null;
			IndustryAssociation industryAssociation1=null;
			IndustryAssociation industryAssociation=null;
			IndustryMoney industryMoney1=null;
			IndustryMoney industryMoney=new IndustryMoney();
			Long leftDepositMoney=0L;
			Long money=0L;
			Long haveDepositMoney=0L;
			try {
			
					//(1)验证验证码
					 result=this.checkMsg(tixianPhone, feephonecode, "PHPF2017070304");//提现手机号
					 
					 if(result.getCode().intValue()!=1){
						 logger.info(tixianPhone+"提现手机验证码有误"+feephonecode+",时间:"+new Date());
						 return ResultUtils.returnError("提现手机验证码有误");
					 }
					 
					 //(2)验证是否注入pay工程
					 if (rpcPayService==null) {
							logger.info("pay工程请求失败,时间:"+new Date());
						 return ResultUtils.returnError("pay工程请求失败");
					 }
					 
					//(3)验证身份
					
					industryAssociation=industryAssociationService.get(industryAssociationId);//获取行业协会信息
					if(null==industryAssociation){//用户信息是否存在
						logger.info("用户信息不存在,时间:"+new Date());
						return ResultUtils.returnError("用户信息不存在");
					}
						String card1=industryAssociation.getCard();//身份证号
						String name1=industryAssociation.getPayname();//收款人姓名
						
						if("".equals(card1) || "".equals(name1)){//是否认证了身份
							logger.info("未进行身份认证,时间:"+new Date());
							return ResultUtils.returnError("您未进行身份认证，请先进行身份认证");
						}
						if(!name1.equals(name)){
							return ResultUtils.returnError("用户姓名不一致");
						}
						if(!card1.equals(card)){
							return ResultUtils.returnError("身份证号不一致");
						}
					//(4)验证金额	
					money=Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(moneyD*100, 0))))+"");//用户输入提现金额   金额，以分为单位
					
					Long money1=industryAssociation.getLeftDepositMoney();//剩余可提现金额
					
					if(null==money1){ money1=0L;}
					
					Long money2=money1-money;
					if(money2<0){
						logger.info("用户剩余可提现金额不足,用户输入提现金额 为："+money+"单位 分,"+"剩余可提现金额为："+money1+"单位 分,"+"可提现减输入金额的差额为："+money2+"单位 分,"+"时间:"+new Date());
						 return ResultUtils.returnError("剩余可提现金额不足");
					 }
					//*******判断单笔提现限额5万 5000000 单位 分*******
					Long dateMoney=5000000L;
					if(money>dateMoney){
						logger.info("单笔提现金额不能超过5万,金额为:"+money+"单位 分,"+"时间:"+new Date());
						 return ResultUtils.returnError("单笔提现金额不能超过5万");
					 }
					//************判断单日限额*************提现金额加今日已提现金额不能超过10000000 单位分 十万
					Long Single_day_limit_hunter=10000000L;//10万 单位 分
					
					//设置日期格式
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");//设置日期格式
					String today = df.format(new Date());
					Date date = df.parse(today);
				  	
				   //查询今日已提现金额
					Long todayCash = cashReceiveStationDao.getTodayCashMoneyByDateIndustryAssociation(Integer.parseInt(cashReceiveStation.getCssId()+""), date);
						logger.info("查询今日已提现金额为:"+todayCash+"单位 分,"+"时间:"+new Date());
					if(null==todayCash){ todayCash=0L;}
					
					Long totalMoney=money+todayCash;//
					if (totalMoney > Single_day_limit_hunter) {
						logger.info("单日提现金额不能超过10万,提现金额为:"+money+"单位 分,"+"今日已提现金额为:"+todayCash+"单位 分,"+"提现金额加今日已提现金额为:"+totalMoney+"单位 分,"+"时间:"+new Date());
						 return ResultUtils.returnError("单日提现金额不能超过10万,您已超额");
					}
					
					
					//生成订单号
						String merSeqId = getInduetryAssociationMerSeqId();
							
					//(5)验证（银行信息是否正确）
					BankCard bankCard=bankCardService.get(bankCardId);//根据银行卡id获取银行卡信息
					if(null == bankCard){
						logger.info("未绑定银行卡,银行卡ID为："+bankCardId+"的信息不存在,时间:"+new Date());
						return ResultUtils.returnError("请先绑定银行卡");
					}
					
					String cardNo = bankCard.getBankNo();//银行卡号
				    String bankName = bankCard.getBank();//银行名称
				    String personName = bankCard.getName();//开户人姓名
				    if(!bankNo.equals(cardNo)){
						return ResultUtils.returnError("银行卡号不一致");
					}
				    if(!bank.equals(bankName)){
						return ResultUtils.returnError("银行名称不一致");
					}
				    if(!name.equals(personName)){
						return ResultUtils.returnError("开户人姓名不一致");
					}
				    
				    Long bankIndustryAssociationId=bankCard.getIndustryAssociationId().getId();
				   //******************保存记录表信息******************// 
				    //1.提现记录表（回调之前）
				    if(industryAssociationId.longValue() != bankIndustryAssociationId.longValue()){//比较信息是否一致
						crsEntity.setCssType(2);//类型1批发商 2行业协会
						crsEntity.setCssId(cashReceiveStation.getCssId());//用户id
						crsEntity.setName(industryAssociation.getPayname());//行业协会名称
						crsEntity.setStatus(3);//1已申请 2打款成功 3打款失败
						crsEntity.setCreatedIp(ipAddr);//用户ip
						crsEntity.setMerSeqId(merSeqId);//订单号
						crsEntity.setCreatedTime(new Date());
						crsEntity.setUpdateTime(new Date());//完成时间
						/*try {
							crsEntity.setCreateTime(getMerDateYYYY());//创建时间
						} catch (ParseException e) {
							e.printStackTrace();
						}*/
						crsEntity.setTel(industryAssociation.getPhone());//电话
						crsEntity.setMoney((Integer)money.intValue());//提现金额
						crsEntity.setFeeIncome(500);//手续费5块
						crsEntity.setDescriber("非法操作,用户银行信息与传递信息不符.");
						this.saveAndModify(crsEntity);
						logger.info("非法操作,用户银行信息与传递信息不符,银行卡ID为："+bankCardId+"用户ID为："+industryAssociationId+"银行卡绑定的用户ID为："+bankCard.getIndustryAssociationId().getId()+",时间:"+new Date());
						return ResultUtils.returnError("非法操作,用户银行信息与传递信息不符");
					}else{
				    	crsEntity.setBankName(bankName);//银行名称
				    	crsEntity.setPersonName(personName);//收款人姓名
				    	crsEntity.setCardNo(cardNo);//银行卡号
					}
					
			
				    crsEntity.setCssType(2);//类型1批发商 2行业协会
					crsEntity.setCssId(cashReceiveStation.getCssId());//用户id
					crsEntity.setName(industryAssociation.getPayname());//行业协会名称
					crsEntity.setStatus(1);//1已申请 2打款成功 3打款失败
					crsEntity.setDescriber("提现申请中");
					crsEntity.setCreatedIp(ipAddr);//用户ip
					crsEntity.setMerSeqId(merSeqId);//订单号
					crsEntity.setCreatedTime(new Date());
					crsEntity.setUpdateTime(new Date());//完成时间
					/*try {
						crsEntity.setCreateTime(getMerDateYYYY());//创建时间
					} catch (ParseException e) {
						e.printStackTrace();
					}*/
					crsEntity.setTel(industryAssociation.getPhone());//电话
					crsEntity.setFeeIncome(500);//手续费5块
					crsEntity.setMoney((Integer)money.intValue());//提现金额
					
					
					 cashReceiveStation1=this.saveAndModify(crsEntity);//保存提现记录表
					if(null==cashReceiveStation1){
						logger.error("保存提现记录表发生异常,用户ID为:"+crsEntity.getCssId()+"姓名："+crsEntity.getPersonName()+",时间:"+new Date());
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return ResultUtils.returnError("保存提现记录表发生异常");
					}else{
						logger.info("保存提现记录表成功,用户ID为："+crsEntity.getCssId()+"姓名："+crsEntity.getPersonName()+",时间:"+new Date());
					}
						
					//（2）扣除用户金额  保存用户信息（回调之前）
					leftDepositMoney=industryAssociation.getLeftDepositMoney();//剩余可提现
					if(null==leftDepositMoney){ leftDepositMoney=0L;}
					leftDepositMoney=leftDepositMoney-money;
					
					haveDepositMoney=industryAssociation.getHaveDepositMoney();//已提现
					if(null==haveDepositMoney){ haveDepositMoney=0L;}
					
					haveDepositMoney=haveDepositMoney+money;
					industryAssociation.setLeftDepositMoney(leftDepositMoney);
					industryAssociation.setHaveDepositMoney(haveDepositMoney);
					industryAssociation1=industryAssociationService.saveAndModify(industryAssociation);//保存用户信息
					if(null==industryAssociation1){
						logger.error("保存用户提现变动金额发生异常,用户ID为:"+industryAssociation.getId()+"姓名："+industryAssociation.getPayname()+",时间:"+new Date());
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return ResultUtils.returnError("保存提现记录表发生异常");
					}else{
						logger.info("保存用户提现变动金额成功,用户ID为："+industryAssociation.getId()+"姓名："+industryAssociation.getPayname()+",时间:"+new Date());
					}
					
					//(3)维护收支表（回调之前）
					industryMoney.setIndustryAssociation(industryAssociation);//批发商id
					industryMoney.setFee(500L);//手续费5块
					industryMoney.setType(4);//0 支付订单  1支付定金  2定金退货  3退货  4提现  5分润
					industryMoney.setStatus(0);//状态 0申请中  1成功   2失败
					industryMoney.setMoney(money);//金额
					industryMoney.setOrderNo(merSeqId);//订单号
					industryMoney.setAfterMoney(money2);//变动后金额
					industryMoney.setBeforeMoney(money1);//变动前金额
					industryMoney1=industryMoneyService.saveAndModify(industryMoney);//保存
					if(null==industryMoney1){
						logger.error("保存用户收支明细表发生异常,用户ID为:"+industryMoney.getIndustryAssociation().getId()+"姓名："+industryMoney.getIndustryAssociation().getPayname()+",时间:"+new Date());
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return ResultUtils.returnError("保存提现记录表发生异常");
					}else{
						logger.info("保存用户收支明细表成功,用户ID为："+industryMoney.getIndustryAssociation().getId()+"姓名："+industryMoney.getIndustryAssociation().getPayname()+",时间:"+new Date());
					}
					
					//crsEntity.setThirdPayType(CodeConstExt.GZYLpay);//接口
					crsEntity.setFromSystem(CodeConstExt.PAY_SYSTEM_FROM_LT);//发起来源
					crsEntity.setCurrentBalance(money1);//提现时余额
					
					
			} catch (Exception e) {
				e.printStackTrace();
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				logger.info("调用pay工程之前，保存记录 出现异常，京东代付,方法异常,提现事务回滚,订单号:"+crsEntity.getMerSeqId()+
						",卡号"+crsEntity.getCardNo()+",用户id:"+crsEntity.getCssId());
				logger.error("订单号:"+crsEntity.getMerSeqId()+",提现发生异常:"+e.getMessage());
				return ResultUtils.returnError("申请提现失败");
			}
			
			
			
			try{
				

				//调用打款方法
				logger.info("调用pay工程打款方法 订单号:"+crsEntity.getMerSeqId()+"提现金额："+crsEntity.getMoney()+"用户ID为："+crsEntity.getCssId()+",用户手机号："+crsEntity.getTel()+",用户ip:"+crsEntity.getCreatedIp()
							+"用户姓名："+crsEntity.getPersonName()+"银行卡号："+crsEntity.getCardNo()+"时间："+new Date());
					
					
					result=rpcPayService.cashService(crsEntity,parem);
					
					logger.info("调用pay工程,请求返回结果处理开始**********");
					if (null !=result) {
						//回调结果
						if (1 == result.getCode().intValue()) {

							logger.info("调用pay工程，提现异常,用户ID为：" + industryAssociation.getId() + "返回参数Code：" + result.getCode() + "返回参数Msg："
									+ result.getMsg() + "返回参数Content:" + result.getContent() + ",时间:" + new Date());
							//失败
							CashReceiveStation cashReceiveStation2 = (CashReceiveStation) result.getContent();
							//1.提现记录表(修改状态)
							cashReceiveStation1.setStatus(3);//1已申请 2打款成功 3打款失败
							cashReceiveStation1.setDescriber("打款失败");
							this.saveAndModify(cashReceiveStation1);
							logger.info("调用pay工程请求失败业务处理，保存提现记录表成功,ID为:" + cashReceiveStation1.getId() + ",订单号："
									+ cashReceiveStation1.getMerSeqId());
							//2.用户表(给用户加钱)
							Long leftDepositMoney1 = industryAssociation1.getLeftDepositMoney();//剩余可提现
							if (null == leftDepositMoney1) {
								leftDepositMoney1 = 0L;
							}
							leftDepositMoney = leftDepositMoney1 + money;

							Long haveDepositMoney1 = industryAssociation1.getHaveDepositMoney();//已提现
							if (null == haveDepositMoney1) {
								haveDepositMoney1 = 0L;
							}
							haveDepositMoney = haveDepositMoney1 - money;

							industryAssociation1.setLeftDepositMoney(leftDepositMoney);
							industryAssociation1.setHaveDepositMoney(haveDepositMoney);
							industryAssociationService.saveAndModify(industryAssociation1);
							logger.info("调用pay工程请求失败业务处理，保存用户信息成功,用户ID:" + industryAssociation1.getId() + "用户姓名：" + industryAssociation1.getPayname());
							//3.收支明细表(修改状态，修改变动金额)
							industryMoney1.setFee(0L);//手续费
							industryMoney1.setType(4);//0 支付订单  1支付定金  2定金退货  3退货  4提现  5分润
							industryMoney1.setStatus(2);//状态 0申请中  1成功   2失败
							industryMoneyService.saveAndModify(industryMoney1);
							logger.info("调用pay工程请求失败业务处理，保存收支明细表成功,ID:" + industryMoney1.getId() + "用户ID"
									+ industryMoney1.getIndustryAssociation().getId());

							return ResultUtils.returnError("提现发生异常");
						} else if (0 == result.getCode().intValue()) {//请求成功（0 成功 ; 1 失败）
							
							logger.info("调用pay工程，请求成功,用户ID为：" + industryAssociation.getId() + "返回参数Code：" + result.getCode() + "返回参数Msg："
									+ result.getMsg() + "返回参数Content:" + result.getContent() + ",时间:" + new Date());
							
							String msg=result.getMsg().toLowerCase();
							
							logger.info("调用pay工程，请求成功,msg转换小写为:" +msg);
							
								if("success".equals(msg)){//请求成功
									
									CashReceiveStation cashReceiveStation2 = (CashReceiveStation) result.getContent();
									//成功
									logger.info("调用pay工程，请求成功,用户ID为：" + industryAssociation.getId() + "返回参数Code：" + result.getCode() + "返回参数Msg："
											+ result.getMsg() + "返回参数Content:" + result.getContent() + ",时间:" + new Date());
									//1.提现记录表(修改状态)
									cashReceiveStation1.setStatus(1);//1已申请 2打款成功 3打款失败
									cashReceiveStation1.setDescriber("提现申请中");
									this.saveAndModify(cashReceiveStation1);
									logger.info("调用pay工程请求成功业务处理，保存提现记录表成功,ID为:" + cashReceiveStation1.getId() + ",订单号："
											+ cashReceiveStation1.getMerSeqId());
									//2.用户表(不变)
		
									//3.收支明细表(修改状态)
									industryMoney.setType(4);//0 支付订单  1支付定金  2定金退货  3退货  4提现  5分润
									industryMoney.setStatus(0);//状态 0申请中  1成功   2失败
									industryMoneyService.saveAndModify(industryMoney);
									logger.info("调用pay工程请求成功业务处理，保存收支明细表成功,ID:" + industryMoney1.getId() + "用户ID"
											+ industryMoney1.getIndustryAssociation().getId());
		
									result.setCode(1);
									result.setMsg("提现申请成功");
								}else if("fail".equals(msg)){//请求申请失败
									logger.info("调用pay工程，请求失败,用户ID为：" + industryAssociation.getId() + "返回参数Code：" + result.getCode() + "返回参数Msg："
											+ result.getMsg() + "返回参数Content:" + result.getContent() + ",时间:" + new Date());
									//失败
									CashReceiveStation cashReceiveStation2 = (CashReceiveStation) result.getContent();
									//1.提现记录表(修改状态)
									cashReceiveStation1.setStatus(3);//1已申请 2打款成功 3打款失败
									cashReceiveStation1.setDescriber("打款失败");
									this.saveAndModify(cashReceiveStation1);
									logger.info("调用pay工程请求失败业务处理，保存提现记录表成功,ID为:" + cashReceiveStation1.getId() + ",订单号："
											+ cashReceiveStation1.getMerSeqId());
									//2.用户表(给用户加钱)
									Long leftDepositMoney1 = industryAssociation1.getLeftDepositMoney();//剩余可提现
									if (null == leftDepositMoney1) {
										leftDepositMoney1 = 0L;
									}
									leftDepositMoney = leftDepositMoney1 + money;

									Long haveDepositMoney1 = industryAssociation1.getHaveDepositMoney();//已提现
									if (null == haveDepositMoney1) {
										haveDepositMoney1 = 0L;
									}
									haveDepositMoney = haveDepositMoney1 - money;

									industryAssociation1.setLeftDepositMoney(leftDepositMoney);
									industryAssociation1.setHaveDepositMoney(haveDepositMoney);
									industryAssociationService.saveAndModify(industryAssociation1);
									logger.info("调用pay工程请求失败业务处理，保存用户信息成功,用户ID:" + industryAssociation1.getId() + "用户姓名：" + industryAssociation1.getPayname());
									//3.收支明细表(修改状态，修改变动金额)
									industryMoney1.setFee(0L);//手续费
									industryMoney1.setType(4);//0 支付订单  1支付定金  2定金退货  3退货  4提现  5分润
									industryMoney1.setStatus(2);//状态 0申请中  1成功   2失败
									industryMoneyService.saveAndModify(industryMoney1);
									logger.info("调用pay工程请求失败业务处理，保存收支明细表成功,ID:" + industryMoney1.getId() + "用户ID"
											+ industryMoney1.getIndustryAssociation().getId());

									return ResultUtils.returnError("提现申请异常");
									
								}else{//其他情况
									
									CashReceiveStation cashReceiveStation2 = (CashReceiveStation) result.getContent();
									//成功
									logger.info("调用pay工程，请求成功,用户ID为：" + industryAssociation.getId() + "返回参数Code：" + result.getCode() + "返回参数Msg："
											+ result.getMsg() + "返回参数Content:" + result.getContent() + ",时间:" + new Date());
									//1.提现记录表(修改状态)
									cashReceiveStation1.setStatus(1);//1已申请 2打款成功 3打款失败
									cashReceiveStation1.setDescriber("提现申请中");
									this.saveAndModify(cashReceiveStation1);
									logger.info("调用pay工程请求成功业务处理，保存提现记录表成功,ID为:" + cashReceiveStation1.getId() + ",订单号："
											+ cashReceiveStation1.getMerSeqId());
									//2.用户表(不变)
		
									//3.收支明细表(修改状态)
									industryMoney.setType(4);//0 支付订单  1支付定金  2定金退货  3退货  4提现  5分润
									industryMoney.setStatus(0);//状态 0申请中  1成功   2失败
									industryMoneyService.saveAndModify(industryMoney);
									logger.info("调用pay工程请求成功业务处理，保存收支明细表成功,ID:" + industryMoney1.getId() + "用户ID"
											+ industryMoney1.getIndustryAssociation().getId());
		
									result.setCode(1);
									result.setMsg("提现申请处理中");
									
								}
								
						}else{
							
							logger.info("调用pay工程,请求返回结果状态code有误，(1:请求失败;2:请求成功)code为："+result.getCode());
						return ResultUtils.returnError("调用pay工程,请求返回结果状态code有误");
						} 
						
						
					}else {
						//失败处理
						logger.info("调用pay工程,请求返回结果result为空");
						return ResultUtils.returnError("调用pay工程,请求返回结果result为空");
					}
					
			}catch(Exception e){
				e.printStackTrace();
				logger.info("调用pay工程之后  出现异常，京东代付,方法异常,提现事务回滚,订单号:"+crsEntity.getMerSeqId()+
						",卡号"+crsEntity.getCardNo()+",用户id:"+crsEntity.getCssId());
				logger.error("订单号:"+crsEntity.getMerSeqId()+",提现发生异常:"+e.getMessage());
				return ResultUtils.returnError("申请提现失败");
			}
			
			
			return result;
			
	}
	

}
