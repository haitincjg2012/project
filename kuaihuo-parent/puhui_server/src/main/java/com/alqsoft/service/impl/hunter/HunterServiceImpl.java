package com.alqsoft.service.impl.hunter;

import java.util.*;

import com.alqsoft.mybatis.dao.position.PositionDao;
import com.alqsoft.vo.PositionVO;
import org.alqframework.net.html.HttpClientUtils;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alqsoft.dao.hunter.HunterDao;
import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.entity.bankcard.BankCard;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.hunterindustrytype.HunterIndustryType;
import com.alqsoft.entity.industryassociation.IndustryAssociation;
import com.alqsoft.entity.industrytype.IndustryType;
import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.order.Order;
import com.alqsoft.entity.ordercomment.OrderComment;
import com.alqsoft.entity.phhunterruleattachment.PhHunterRuleAttachment;
import com.alqsoft.init.InitParamPc;
import com.alqsoft.service.attachment.AttachmentService;
import com.alqsoft.service.bankcard.BankCardService;
import com.alqsoft.service.hunter.HunterService;
import com.alqsoft.service.hunterindustrytype.HunterIndustryTypeService;
import com.alqsoft.service.industryassociation.IndustryAssociationService;
import com.alqsoft.service.industrytype.IndustryTypeService;
import com.alqsoft.service.member.MemberService;
import com.alqsoft.service.ordercomment.OrderCommentService;
import com.alqsoft.service.phHunterRuleAttachment.PhHunterRuleAttachmentService;
import com.alqsoft.utils.HttpClientObject;
import com.alqsoft.vo.PhHunterRuleAttachmentVO;
import com.mysql.jdbc.StringUtils;

@Service
@Transactional(readOnly = true)
public class HunterServiceImpl implements HunterService {

	private static Logger logger = LoggerFactory.getLogger(HunterServiceImpl.class);

	@Autowired
	private PositionDao positionDao;
	@Autowired
	private HunterDao hunterDao;
	@Autowired
	private MemberService memberService;
	@Autowired
	private BankCardService bankCardService;
	@Autowired
	private InitParamPc initParamPc;
	@Autowired
	private IndustryAssociationService industryAssociationService;
	@Autowired
	private OrderCommentService orderCommentService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private IndustryTypeService industryTypeService;
	@Autowired
	private HunterIndustryTypeService hunterIndustryTypeService;
	@Autowired
	private PhHunterRuleAttachmentService phHunterRuleAttachmentService;

	/**
	 * 删除
	 * 
	 * @param arg0
	 * @return
	 */
	@CacheEvict(key = "'alq_server_hunter'+#arg0", value = "alq_server_hunter")
	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub

		return false;
	}

	/**
	 * 查询
	 * 
	 * @param arg0
	 * @return
	 */
	@Cacheable(key = "'alq_server_hunter'+#arg0", value = "alq_server_hunter")
	@Override
	public Hunter get(Long arg0) {
		// TODO Auto-generated method stub
		return hunterDao.findOne(arg0);
	}

	/**
	 * 添加（修改）
	 * 
	 * @param arg0
	 * @return
	 */
	@CacheEvict(key = "'alq_server_hunter'+#arg0.id", value = "alq_server_hunter")
	@Override
	public Hunter saveAndModify(Hunter arg0) {
		Hunter hunter = null;

		try {
			hunter = hunterDao.save(arg0);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
		}

		return hunter;
	}

	/**
	 * 驻地区域接口
	 * 
	 * @return
	 */
	@Transactional
	@Override
	public Result updateHunterArea(Hunter hunter) {
		Result result = new Result();
		try {
			Hunter hunter1 = this.get(hunter.getId());

			hunter1.setPhProvinceId(hunter.getPhProvinceId());// 省级id
			hunter1.setProvinceName(hunter.getProvinceName());// 省级名称
			hunter1.setPhCityId(hunter.getPhCityId());// 市级id
			hunter1.setCityName(hunter.getCityName());// 市级名称

			// hunter1.setPhTownId(hunter.getPhTownId());//乡镇id
			// hunter1.setTownName(hunter.getTownName());//乡镇名称 wangzhen
			// 2017年7月19日11:02:59 注释原因：需求更改，不需要乡镇

			hunter1.setLongitude(hunter.getLongitude());// 经度
			hunter1.setLatitude(hunter.getLatitude());// 维度
			hunter1.setDetail(hunter.getDetail());// 详细地址
			hunter1.setPositionLevel(hunter.getPositionLevel());// 等级
			hunter1.setStation(hunter.getStation());

			hunter1.setPhTownId(null);
			hunter1.setTownName(null);
			hunter1.setDistricts(null);
			if (hunter.getPositionLevel().intValue() == 1) {

				hunter1.setPhCountyId(null);
				hunter1.setCountyName(null);

			} else if (hunter.getPositionLevel().intValue() == 2) {

				hunter1.setPhCountyId(hunter.getPhCountyId());// 县级id
				hunter1.setCountyName(hunter.getCountyName());// 县级名称
			}
			this.saveAndModify(hunter1);
			result.setCode(1);
			result.setMsg("驻地区域信息修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
			logger.error("驻地区域信息修改发生异常:" + e.getMessage());
			result.setCode(0);
			result.setMsg("驻地区域信息修改失败");
		}

		return result;
	}

	/**
	 * 注册-完善批发商信息接口
	 * 
	 * @return
	 */
	@Override
	@Transactional
	public Result addHunter(Long merberId, Hunter hunter, PhHunterRuleAttachmentVO phVO) {
		Result result = new Result();
		try {

			Member member = memberService.get(merberId);// 根据用户id获取信息
			if (null == member) {
				return ResultUtils.returnError("会员member不存在");
			}
			if(null==phVO){
				return ResultUtils.returnError("照片为空");
			}
			// 同步会员表里的身份证和姓名
			String name = member.getName();// 姓名
			String card = member.getCard();// 身份证号
			/*if (null == name || null == card) {
				return ResultUtils.returnError("您的信息不完善，请先进行身份认证");
			} else if (name.equals("") || card.equals("")) {
				return ResultUtils.returnError("您的信息不完善，请先进行身份认证");
			}*/
			hunter.setName(name);
			hunter.setCard(card);

			Hunter hunter11 = this.saveAndModify(hunter);// 保存批发商信息

			member.setHunter(hunter11);// 关联member表
			Member saveAndModify = memberService.saveAndModify(member);// 保存member表

			Long hunterId = hunter11.getId();
			// 关联商户
			List<String> ruleList = phVO.getRuleAttachmentList();
			for (String id : ruleList) {
				if(id!=null){
					
				PhHunterRuleAttachment ph = phHunterRuleAttachmentService.get(Long.valueOf(id));
				if(ph==null){
					return  ResultUtils.returnError("照片不存在");
				}
				ph.setHunterId(hunterId);
				phHunterRuleAttachmentService.saveAndModify(ph);
				}
			}

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("hunterId", hunterId);
			result.setCode(1);
			result.setMsg("批发商信息提交成功");
			result.setContent(data);

		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
			logger.error("批发商信息提交改发生异常:" + e.getMessage());
			result.setCode(0);
			result.setMsg("批发商信息提交失败");
		}

		return result;
	}

	/**
	 * 安全设置-批发商身份认证
	 * 
	 * @param name
	 * @param card
	 * @return
	 */
	@Override
	@Transactional
	public Result checkHunterNameAndCard(String name, String card, Member member) {
		Result result = new Result();

		Hunter hunter = this.get(member.getHunter().getId());// 获取批发商信息

		if (null == hunter) {

			return ResultUtils.returnError("批发商信息不存在");
		} else {

			hunter.setName(name);
			hunter.setCard(card);

			try {

				this.saveAndModify(hunter);
				result.setCode(1);
				result.setMsg("身份认证提交成功");

			} catch (Exception e) {
				e.printStackTrace();
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
				logger.error("身份认证提交改发生异常:" + e.getMessage());

				result.setCode(0);
				result.setMsg("身份认证提交失败");
			}
		}

		return result;
	}

	@Override
	public Hunter getRowLock(Long id) {
		// TODO Auto-generated method stub
		return hunterDao.getRowLock(id);
	}

	/**
	 * 安全设置-添加银行卡
	 * 
	 * @param bankCard
	 * @param member
	 * @return
	 */
	@Override
	@Transactional
	public Result addHunterBankCard(BankCard bankCard, Member member) {
		Result result = new Result();
		// 去除空格
		String bankNo = bankCard.getBankNo().replaceAll(" ", "");// 银行卡号
		String card = bankCard.getCard().replaceAll(" ", "");// 身份证号
		String name = bankCard.getName().replaceAll(" ", "");// 开户人姓名
		String bank = bankCard.getBank().replaceAll(" ", "");// 开户银行
		String bankAddr = bankCard.getBankAddr().replaceAll(" ", "");// 开户行缩略

		Hunter hunter2 = member.getHunter();// 获取登录批发商信息
		if (null == hunter2) {
			return ResultUtils.returnError("角色异常");
		}

		if (null == hunter2.getId()) {
			return ResultUtils.returnError("角色异常");
		}
		Hunter hunter = hunterDao.findOne(hunter2.getId());// 根据id获取批发商信息

		String hunterName = hunter.getName();// 姓名
		String hunterCard = hunter.getCard();// 身份证号
		if (null == hunterName || null == hunterCard) {
			return ResultUtils.returnError("您的信息不完善，请先进行身份认证");
		} else if (hunterName.equals("") || hunterCard.equals("")) {
			return ResultUtils.returnError("您的信息不完善，请先进行身份认证");
		}

		if (null == bankNo || bankNo.equals("")) {
			return ResultUtils.returnError("银行卡号不能为空");
		} else {
			String regex = "^\\d{16}|\\d{18}|\\d{19}$";
			if (!bankNo.matches(regex)) {
				return ResultUtils.returnError("请输入正确的银行卡号16,18,19位");
			}
		}
		if (null == card || card.equals("")) {
			return ResultUtils.returnError("身份证号不能为空");
		}
		if (null == name || name.equals("")) {
			return ResultUtils.returnError("开户人姓名不能为空");
		} else {
			if (name.length() < 1 || name.length() > 20) {
				return ResultUtils.returnError("开户人姓名请输入1-20位字符");
			}
		}
		if (null == bank || bank.equals("")) {
			return ResultUtils.returnError("开户银行不能为空");
		}
		if (null == bankAddr || bankAddr.equals("")) {
			return ResultUtils.returnError("开户缩略不能为空");
		}
		if (!name.equals(hunterName)) {
			return ResultUtils.returnError("用户姓名与开户人姓名不一致！");
		}
		if (!card.toUpperCase().equals(hunterCard.toUpperCase())) {
			return ResultUtils.returnError("用户身份证号与输入身份证号不一致！");
		}

		// 三要素验证
		String three_element_url = (String) initParamPc.getProperties().get("three_element_url");// 获取三要素地址
		// 初始接口参数
		List<NameValuePair> callpayParame = new ArrayList<NameValuePair>();
		callpayParame.add(new BasicNameValuePair("name", name));// 姓名
		callpayParame.add(new BasicNameValuePair("cardNum", card));// 身份证号
		callpayParame.add(new BasicNameValuePair("bankCardNo", bankNo));// 银行卡号
		Long hunter_id = hunter.getId();// 批发商id
		try {

			logger.info("批发商ID：" + hunter_id + "，参数：" + callpayParame.toString() + ",三要素地址：" + three_element_url
					+ "/mobile/view/jdCheck/checkJDCard");
			String checkResult = HttpClientObject.sendGet(three_element_url + "/mobile/view/jdCheck/checkJDCard",
					callpayParame);
			logger.info("批发商ID：" + hunter_id + "，参数：" + callpayParame.toString() + ",返回值：" + checkResult.toString());
			JSONObject jsonObj = JSON.parseObject(checkResult);
			Integer result_code = jsonObj.getInteger("code");
			String msg = jsonObj.getString("msg");
			if (result_code != 1 && result_code != 3) {
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

		// 绑定银行卡
		try {
			BankCard bankc = new BankCard();
			bankc.setBank(bank);// 开户银行名称
			bankc.setBankAddr(bankAddr);// 开户行缩略
			bankc.setBankNo(bankNo);// 银行卡号
			bankc.setName(name);// 开户人姓名
			bankc.setCard(card);// 身份证号

			bankc.setHunter(hunter);// 批发商id
			bankc.setStatus(1);// 0 绑卡记录 1正在绑定中的银行卡
			this.bankCardService.saveAndModify(bankc);
			result.setCode(1);
			result.setMsg("绑定银行卡成功");

		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
			logger.error("绑定银行卡有误：" + e.getMessage() + "时间：" + new Date());
			return ResultUtils.returnError("绑定银行卡有误：" + e.getMessage());
		}

		return result;
	}

	/**
	 * 安全设置-修改手机号 原手机号码发送验证码 PHPF2017070301
	 * 
	 * @param phone
	 * @return
	 */
	@Override
	public Result sendMsgForOldPhone(Member member, String oldphone, String codeType) {
		Result result = new Result();

		if (StringUtils.isEmptyOrWhitespaceOnly(oldphone)) {
			return ResultUtils.returnError("原手机号不能为空");
		}

		Hunter hunter1 = member.getHunter();

		if (hunter1 == null) {
			return ResultUtils.returnError("角色异常");
		}

		Member member1 = memberService.get(member.getId());

		if ("".equals(member1.getPhone()) || null == member1.getPhone()) {
			return ResultUtils.returnError("用户手机号为空");
		}

		if (!member1.getPhone().equals(oldphone)) {
			return ResultUtils.returnError("和您原手机信息不符");
		}

		try {
			String msg_url = this.initParamPc.getProperties().getProperty("send_msg_url");// 发送短信路径
			String url = msg_url + "?phone=" + oldphone + "&codeType=" + codeType;
			String sendGet = HttpClientUtils.getHttpsToGet(url);
			JSONObject json = JSON.parseObject(sendGet);
			result.setCode(Integer.parseInt(json.get("code").toString()));
			result.setMsg(json.get("msg").toString());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("调用发送验证码接口异常" + e.getMessage());
			return ResultUtils.returnError("发送失败");
		}

	}

	/**
	 * 安全设置-修改手机号 新手机号码发送验证码 PHPF2017070302
	 * 
	 * @param newphone
	 * @return
	 */
	@Override
	public Result sendMsgForNewPhone(Member member, String newphone, String codeType) {
		Result result = new Result();

		try {
			String msg_url = this.initParamPc.getProperties().getProperty("send_msg_url");// 发送短信路径
			String url = msg_url + "?phone=" + newphone + "&codeType=" + codeType;
			String sendGet = HttpClientUtils.getHttpsToGet(url);
			JSONObject json = JSON.parseObject(sendGet);
			result.setCode(Integer.parseInt(json.get("code").toString()));
			result.setMsg(json.get("msg").toString());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("调用发送验证码接口异常" + e.getMessage());
			return ResultUtils.returnError("发送失败");
		}
	}

	/**
	 * 验证验证码
	 * 
	 * @param phone
	 * @param code
	 * @param codeType
	 * @return
	 */
	public Result checkMsg(String phone, String code, String codeType) {
		Result result = new Result();
		try {
			String msg_url = this.initParamPc.getProperties().getProperty("check_msg_url");// 发送短信路径
			String url = msg_url + "?phone=" + phone + "&code=" + code + "&codeType=" + codeType;
			String sendGet = HttpClientUtils.getHttpsToGet(url);
			JSONObject json = JSON.parseObject(sendGet);
			result.setCode(Integer.parseInt(json.get("code").toString()));
			result.setMsg(json.get("msg").toString());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("调用验证验证码接口异常" + e.getMessage());
			return ResultUtils.returnError("调用验证验证码接口异常");
		}
	}

	/**
	 * 安全设置-修改手机号
	 * 
	 * @param oldphone
	 * @param oldcode
	 * @param newphone
	 * @param newcode
	 * @return
	 */
	@Override
	@Transactional
	public Result updatePhone(String oldphone, String oldcode, String newphone, String newcode, Member member,
			String identifilter, String imPassword, String userSign) {
		Result result = new Result();

		// 验证 验证码是否正确
		result = this.checkMsg(oldphone, oldcode, "PHPF2017070301");// 旧手机号

		if (result.getCode() == 1) {

			result = this.checkMsg(newphone, newcode, "PHPF2017070302");// 新手机号

			if (result.getCode() == 1) {

				// 修改手机号
				Member member1 = memberService.get(member.getId());
				member1.setPhone(newphone);
				member1.setImId(identifilter);
				member1.setImPassword(imPassword);
				member1.setImSign(userSign);
				try {

					memberService.saveAndModify(member1);
					result.setCode(1);
					result.setMsg("手机号修改成功");

				} catch (Exception e) {

					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
					logger.error("手机号修改有误：" + e.getMessage() + "时间：" + new Date());
					return ResultUtils.returnError("手机号修改有误：" + e.getMessage());

				}

			} else {

				return ResultUtils.returnError("新手机号验证码有误");
			}

		} else {

			return ResultUtils.returnError("旧手机号验证码有误");

		}

		return result;
	}

	/**
	 * 安全设置-修改银行卡
	 */
	@Override
	@Transactional
	public Result updateHunterBankCard(BankCard bankCard, Member member) {
		Result result = new Result();
		// 去除空格
		String bankNo = bankCard.getBankNo().replaceAll(" ", "");// 银行卡号
		String card = bankCard.getCard().replaceAll(" ", "");// 身份证号
		String name = bankCard.getName().replaceAll(" ", "");// 开户人姓名
		String bank = bankCard.getBank().replaceAll(" ", "");// 开户银行
		String bankAddr = bankCard.getBankAddr().replaceAll(" ", "");// 开户行缩略

		Hunter hunter2 = member.getHunter();// 获取登录批发商信息
		Hunter hunter = hunterDao.findOne(hunter2.getId());// 根据id获取批发商信息

		if (null == bankCard.getId()) {
			return ResultUtils.returnError("银行卡id不能为空");
		}
		BankCard bankCard1 = bankCardService.get(bankCard.getId());// 根据银行卡id获取信息

		if (bankCard1.getHunter().getId().longValue() != hunter2.getId().longValue()) {// 比较用户id与银行卡信息中是否一致
			return ResultUtils.returnError("银行卡与用户信息不符");
		}

		String hunterName = hunter.getName();// 姓名
		String hunterCard = hunter.getCard();// 身份证号
		if (null == hunterName || null == hunterCard) {
			return ResultUtils.returnError("您的信息不完善，请先进行身份认证");
		} else if (hunterName.equals("") || hunterCard.equals("")) {
			return ResultUtils.returnError("您的信息不完善，请先进行身份认证");
		}

		if (null == card || card.equals("")) {
			return ResultUtils.returnError("身份证号不能为空");
		}
		if (null == bankNo || bankNo.equals("")) {
			return ResultUtils.returnError("银行卡号不能为空");
		} else {
			String regex = "^\\d{16}|\\d{18}|\\d{19}$";
			if (!bankNo.matches(regex)) {
				return ResultUtils.returnError("请输入正确的银行卡号16,18,19位");
			}
		}

		if (null == name || name.equals("")) {
			return ResultUtils.returnError("开户人姓名不能为空");
		} else {
			if (name.length() < 1 || name.length() > 20) {
				return ResultUtils.returnError("开户人姓名请输入1-20位字符");
			}
		}
		if (null == bank || bank.equals("")) {
			return ResultUtils.returnError("开户银行不能为空");
		}
		if (null == bankAddr || bankAddr.equals("")) {
			return ResultUtils.returnError("开户缩略不能为空");
		}
		if (!name.equals(hunterName)) {
			return ResultUtils.returnError("用户姓名与开户人姓名不一致！");
		}
		if (!card.toUpperCase().equals(hunterCard.toUpperCase())) {
			return ResultUtils.returnError("用户身份证号与输入身份证号不一致！");
		}

		// 三要素验证
		String three_element_url = (String) initParamPc.getProperties().get("three_element_url");// 获取三要素地址
		// 初始接口参数
		List<NameValuePair> callpayParame = new ArrayList<NameValuePair>();
		callpayParame.add(new BasicNameValuePair("name", name));// 姓名
		callpayParame.add(new BasicNameValuePair("cardNum", card));// 身份证号
		callpayParame.add(new BasicNameValuePair("bankCardNo", bankNo));// 银行卡号
		Long hunter_id = hunter.getId();// 批发商id
		try {

			logger.info("批发商ID：" + hunter_id + "，参数：" + callpayParame.toString() + ",三要素地址：" + three_element_url
					+ "/mobile/view/jdCheck/checkJDCard");
			String checkResult = HttpClientObject.sendGet(three_element_url + "/mobile/view/jdCheck/checkJDCard",
					callpayParame);
			logger.info("批发商ID：" + hunter_id + "，参数：" + callpayParame.toString() + ",返回值：" + checkResult.toString());
			JSONObject jsonObj = JSON.parseObject(checkResult);
			Integer result_code = jsonObj.getInteger("code");
			String msg = jsonObj.getString("msg");
			if (result_code != 1 && result_code != 3) {
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

		try {

			Long hunterId = hunter.getId();
			List<BankCard> list = bankCardService.findBankCardByHunterId(hunterId);// 根据批发商id查询银行卡集合
			String[] bids = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				bids[i] = list.get(i).getId().toString();
			}
			// 修改已绑银行卡状态status (0 绑卡记录 1正在绑定中的银行卡)
			int count = bankCardService.updateBankCardByHunterId(bids);
			logger.error("修改银行卡状态status,本次修改共" + count + "条,批发商id为" + hunterId + "时间：" + new Date());

			// 保存新记录
			BankCard bankCard2 = new BankCard();

			bankCard2.setBankNo(bankNo);// 银行卡号
			bankCard2.setBank(bank);// 开户银行名称
			bankCard2.setBankAddr(bankAddr);// 开户行缩略
			bankCard2.setName(name);// 开户人姓名
			bankCard2.setCard(card);// 身份证号

			bankCard2.setStatus(1);// 0 绑卡记录 1正在绑定中的银行卡
			bankCard2.setHunter(hunter);// 批发商id

			this.bankCardService.saveAndModify(bankCard2);
			result.setCode(1);
			result.setMsg("修改银行卡成功");

		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
			logger.error("修改银行卡有误：" + e.getMessage() + "时间：" + new Date());
			return ResultUtils.returnError("修改银行卡有误：" + e.getMessage());
		}

		return result;
	}

	/**
	 * 安全设置-删除银行卡
	 * 
	 * @param id
	 * @return
	 */
	@Override
	@Transactional
	public Result deleteHunterBankCard(Long id, Member member) {
		Result result = new Result();

		Hunter hunter = member.getHunter();// 获取登录批发商信息
		if (null == hunter) {

			return ResultUtils.returnError("角色异常");
		}
		Long id1 = hunter.getId();// 批发商ID
		if (null == id) {

			return ResultUtils.returnError("银行卡id不能为空");
		}
		BankCard bankCard = bankCardService.get(id);

		if (null == bankCard) {

			return ResultUtils.returnError("银行卡信息不存在");
		}
		Long id2 = bankCard.getHunter().getId();// 银行卡绑定批发商ID

		if (id1.longValue() != id2.longValue()) {

			return ResultUtils.returnError("银行卡所属批发商信息有误");
		}

		try {

			bankCard.setStatus(0);// 0 绑卡记录 1正在绑定中的银行卡
			bankCardService.saveAndModify(bankCard);
			result.setCode(1);
			result.setMsg("删除银行卡成功");

		} catch (Exception e) {
			result.setCode(0);
			result.setMsg("删除银行卡失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
		}

		return result;
	}

	/**
	 * 批发商个人中心--所属协会编辑
	 * 
	 * @return
	 */
	@Override
	@Transactional
	public Result updateIndustryAssociation(Long id, Member member) {

		Result result = new Result();

		Hunter hunter = this.get(member.getHunter().getId());// 获取批发商信息

		IndustryAssociation industryAssociation = industryAssociationService.get(id);// 根据ID获取行业协会信息

		if (null == industryAssociation) {

			return ResultUtils.returnError("改id所属行业公会不存在");
		}

		hunter.setIndustryAssociation(industryAssociation);// 保存批发商的行业协会

		try {

			this.saveAndModify(hunter);
			result.setCode(1);
			result.setMsg("行业公会修改成功");
		} catch (Exception e) {

			result.setCode(0);
			result.setMsg("行业公会修改失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
		}

		return result;
	}

	@Override
	public Map<String, Object> checkHunterLevel(Long orderMoney, Long goodCommentNum) {
		if (Objects.isNull(orderMoney)) {
			orderMoney = 0L;
		}
		if (Objects.isNull(goodCommentNum)) {
			goodCommentNum = 0L;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (orderMoney.longValue() < 1000000 || goodCommentNum.longValue() < 30) {
			map.put("star", 0);
			map.put("level", 0);
		} else if ((orderMoney.longValue() >= 1000000 && orderMoney.longValue() < 2000000)
				|| (goodCommentNum.longValue() >= 30 && goodCommentNum.longValue() < 60)) {
			map.put("star", 1);
			map.put("level", 0);
		} else if ((orderMoney.longValue() >= 2000000 && orderMoney.longValue() < 4000000)
				|| (goodCommentNum.longValue() >= 60 && goodCommentNum.longValue() < 120)) {
			map.put("star", 2);
			map.put("level", 0);
		} else if ((orderMoney.longValue() >= 4000000 && orderMoney.longValue() < 8000000)
				|| (goodCommentNum.longValue() >= 120 && goodCommentNum.longValue() < 240)) {
			map.put("star", 3);
			map.put("level", 0);
		} else if ((orderMoney.longValue() >= 8000000 && orderMoney.longValue() < 16000000)
				|| (goodCommentNum.longValue() >= 240 && goodCommentNum.longValue() < 480)) {
			map.put("star", 4);
			map.put("level", 0);
		} else if ((orderMoney.longValue() >= 16000000 && orderMoney.longValue() < 32000000)
				|| (goodCommentNum.longValue() >= 480 && goodCommentNum.longValue() < 960)) {
			map.put("star", 5);
			map.put("level", 0);
		} else if ((orderMoney.longValue() >= 32000000 && orderMoney.longValue() < 64000000)
				|| (goodCommentNum.longValue() >= 960 && goodCommentNum.longValue() < 1920)) {
			map.put("star", 1);
			map.put("level", 1);
		} else if ((orderMoney.longValue() >= 64000000 && orderMoney.longValue() < 128000000)
				|| (goodCommentNum.longValue() >= 1920 && goodCommentNum.longValue() < 3840)) {
			map.put("star", 2);
			map.put("level", 1);
		} else if ((orderMoney.longValue() >= 128000000 && orderMoney.longValue() < 256000000)
				|| (goodCommentNum.longValue() >= 3840 && goodCommentNum.longValue() < 7680)) {
			map.put("star", 3);
			map.put("level", 1);
		} else if ((orderMoney.longValue() >= 256000000 && orderMoney.longValue() < 512000000)
				|| (goodCommentNum.longValue() >= 7680 && goodCommentNum.longValue() < 15360)) {
			map.put("star", 4);
			map.put("level", 1);
		} else if ((orderMoney.longValue() >= 512000000 && orderMoney.longValue() < 1024000000)
				|| (goodCommentNum.longValue() >= 15360 && goodCommentNum.longValue() < 30720)) {
			map.put("star", 5);
			map.put("level", 1);
		} else if ((orderMoney.longValue() >= 1024000000 && orderMoney.longValue() < 2048000000)
				|| (goodCommentNum.longValue() >= 30720 && goodCommentNum.longValue() < 61440)) {
			map.put("star", 1);
			map.put("level", 2);
		} else if ((orderMoney.longValue() >= 2048000000 && orderMoney.longValue() < 4096000000L)
				|| (goodCommentNum.longValue() >= 61440 && goodCommentNum.longValue() < 122880)) {
			map.put("star", 2);
			map.put("level", 2);
		} else if ((orderMoney.longValue() >= 4096000000L && orderMoney.longValue() < 8192000000L)
				|| (goodCommentNum.longValue() >= 122880 && goodCommentNum.longValue() < 245760)) {
			map.put("star", 3);
			map.put("level", 2);
		} else if ((orderMoney.longValue() >= 8192000000L && orderMoney.longValue() < 16384000000L)
				|| (goodCommentNum.longValue() >= 245760 && goodCommentNum.longValue() < 491520)) {
			map.put("star", 4);
			map.put("level", 2);
		} else if ((orderMoney.longValue() >= 16384000000L && orderMoney.longValue() < 32768000000L)
				|| (goodCommentNum.longValue() >= 491520 && goodCommentNum.longValue() < 983040)) {
			map.put("star", 5);
			map.put("level", 2);
		} else if ((orderMoney.longValue() >= 32768000000L && orderMoney.longValue() < 65536000000L)
				|| (goodCommentNum.longValue() >= 983040 && goodCommentNum.longValue() < 1966080)) {
			map.put("star", 1);
			map.put("level", 3);
		} else if ((orderMoney.longValue() >= 65536000000L && orderMoney.longValue() < 131072000000L)
				|| (goodCommentNum.longValue() >= 1966080 && goodCommentNum.longValue() < 3932160)) {
			map.put("star", 2);
			map.put("level", 3);
		} else if ((orderMoney.longValue() >= 131072000000L && orderMoney.longValue() < 262144000000L)
				|| (goodCommentNum.longValue() >= 3932160 && goodCommentNum.longValue() < 7864320)) {
			map.put("star", 3);
			map.put("level", 3);
		} else if ((orderMoney.longValue() >= 262144000000L && orderMoney.longValue() < 524288000000L)
				|| (goodCommentNum.longValue() >= 7864320 && goodCommentNum.longValue() < 15728640)) {
			map.put("star", 4);
			map.put("level", 3);
		} else {
			map.put("star", 5);
			map.put("level", 3);
		}
		return map;
	}

	/***
	 * 批发商个人中心--行业分类
	 * 
	 * @param cId
	 * @param hId
	 * @return
	 */
	@Transactional
	public void upateIndustryClassify(Long cId, Long hId) {
		hunterDao.upateIndustryClassify(cId, hId);
	}

	@Override
	@Transactional
	public void updateOrderMoneyByOrderId(Order order) {
		Hunter hunter = this.get(order.getHunter().getId());
		Long totalPrice = order.getTotalPrice();
		Long orderMoney = hunter.getOrderMoney() == null ? 0L : hunter.getOrderMoney();
		hunter.setOrderMoney(orderMoney.longValue() - totalPrice.longValue());
		Long num = hunter.getNum() == null ? 0L : hunter.getNum();
		hunter.setNum(num.longValue() - 1);
		this.saveAndModify(hunter);
		logger.info("订单:" + order.getId() + "退款,订单总额更新成功");
	}

	@Override
	@Transactional
	public void updateCommentNumOrder(Order order) {
		Hunter hunter = this.get(order.getHunter().getId());
		OrderComment orderComment = this.orderCommentService.getCommentByOrderId(order.getId());
		if (orderComment.getStartNum().intValue() == 5) {
			hunter.setGoodCommentNumOrder(hunter.getGoodCommentNumOrder().longValue() - 1);
		} else if (orderComment.getStartNum().intValue() >= 3 && orderComment.getStartNum().intValue() <= 4) {
			hunter.setCommentNumOrder(hunter.getCommentNumOrder().longValue() - 1);
		} else if (orderComment.getStartNum().intValue() >= 0 && orderComment.getStartNum().intValue() < 3) {
			hunter.setBadCommentNumOrder(hunter.getBadCommentNumOrder().longValue() - 1);
		}
		orderComment.setIsDelete(1);
		this.orderCommentService.saveAndModify(orderComment);
		this.saveAndModify(hunter);
		logger.info("订单:" + order.getId() + "退款,批发商评论更新成功");
	}

	/**
	 * 批发商个人中心--店铺管理--添加背景图
	 * 
	 * @return
	 */
	@Override
	@Transactional
	public Result addhunterbackgroundimage(Long hunterId, Long imageId) {

		Result result = new Result();

		Hunter hunter = this.get(hunterId);// 根据id查询批发商信息
		if (null == hunter) {
			return ResultUtils.returnError("批发商不存在");
		}

		Attachment backgroundImage = attachmentService.get(imageId);// 根据id查询背景图片信息
		if (null == backgroundImage) {
			return ResultUtils.returnError("背景图片不存在");
		}

		try {

			hunter.setBackgroundImage(backgroundImage);
			this.saveAndModify(hunter);
			result.setCode(1);
			result.setMsg("保存批发商店铺背景图片成功");

		} catch (Exception e) {

			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
			return ResultUtils.returnError("保存批发商店铺背景图片有误：" + e.getMessage());
		}

		return result;
	}

	/**
	 * 注册-完善批发商信息一期优化接口
	 * 
	 * @return
	 */
	@Override
	@Transactional
	public Result addhunterLabel(Long merberId, Hunter hunter, String industryTypeIdList) {

		Result result = new Result();
		try {

			Member member = memberService.get(merberId);// 根据用户id获取信息
			if (null == member) {
				return ResultUtils.returnError("会员member不存在");
			}
			// 保存批发商信息
			Hunter hunter11 = this.saveAndModify(hunter);
			// 保存member表
			member.setHunter(hunter11);// 关联member表
			memberService.saveAndModify(member);// 保存member表
			// 保存批发商与行业分类（标签）关联记录表
			String[] aa = industryTypeIdList.split(",");
			for (String type : aa) {

				// 根据二级分类id查询一级分类id
				IndustryType industryType = industryTypeService.get(Long.valueOf(type));// 根据id获取二级分类
				IndustryType oneLevelIndustryType = industryType.getParentId();// 根据二级分类获取一级分类

				HunterIndustryType hunterIndustryType = new HunterIndustryType();
				hunterIndustryType.setIndustryType(industryType);// 二级行业分类
				hunterIndustryType.setOneLevelIndustryType(oneLevelIndustryType);// 一级行业分类
				hunterIndustryType.setHunter(hunter11);// 关联批发商
				try {
					hunterIndustryTypeService.saveAndModify(hunterIndustryType);
				} catch (Exception e) {
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
					logger.error("保存批发商与行业分类关联记录表发生异常:" + e.getMessage());
					result.setCode(0);
					result.setMsg("保存批发商与行业分类关联关系失败");
				}

			}

			Long hunterId = hunter11.getId();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("hunterId", hunterId);
			result.setCode(1);
			result.setMsg("批发商信息提交成功");
			result.setContent(data);

		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
			logger.error("批发商信息提交改发生异常:" + e.getMessage());
			result.setCode(0);
			result.setMsg("批发商信息提交失败");
		}

		return result;
	}

	/**
	 * 批发商个人信息编辑- 标签分类接口
	 * 
	 * @return
	 */
	@Override
	@Transactional
	public Result updateHunterlabel(Long id, Member member, String industryTypeIdList) {

		Result result = new Result();

		Hunter hunter = member.getHunter();// 获取登录批发商信息
		if (null == hunter) {
			return ResultUtils.returnError("角色异常");
		}
		if (null == hunter.getId()) {
			return ResultUtils.returnError("角色异常");
		}
		if (id.longValue() != hunter.getId().longValue()) {
			return ResultUtils.returnError("信息不一致");
		}

		// 根据批发商id删除关联关系
		int deleteSearchKeyWord = hunterIndustryTypeService.deletehunterIndustryTypeById(id);
		if (deleteSearchKeyWord < 0) {
			return ResultUtils.returnError("删除批发商关联分类标签关系失败");
		}

		// 保存批发商与行业分类（标签）关联记录表
		String[] aa = industryTypeIdList.split(",");
		for (String type : aa) {

			// 根据二级分类id查询一级分类id
			IndustryType industryType = industryTypeService.get(Long.valueOf(type));// 根据id获取二级分类
			IndustryType oneLevelIndustryType = industryType.getParentId();// 根据二级分类获取一级分类

			HunterIndustryType hunterIndustryType = new HunterIndustryType();
			hunterIndustryType.setIndustryType(industryType);// 二级行业分类
			hunterIndustryType.setOneLevelIndustryType(oneLevelIndustryType);// 一级行业分类
			hunterIndustryType.setHunter(hunter);// 关联批发商
			try {
				hunterIndustryTypeService.saveAndModify(hunterIndustryType);
				result.setCode(1);
				result.setMsg("修改批发商与行业分类关联关系成功");
			} catch (Exception e) {
				e.printStackTrace();
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
				logger.error("修改批发商与行业分类关联记录表发生异常:" + e.getMessage());
				result.setCode(0);
				result.setMsg("修改批发商与行业分类关联关系失败");
			}

		}

		return result;
	}

	@Override
	@Transactional
	public Result addhunterLabelAndAttachment(Long merberId, Hunter hunter, String industryTypeIdList,
			PhHunterRuleAttachmentVO phVO) {
		Result result = new Result();
		try {

			Member member = memberService.get(merberId);// 根据用户id获取信息
			if (null == member) {
				return ResultUtils.returnError("会员member不存在");
			}
			// 保存批发商信息
			Hunter hunter11 = this.saveAndModify(hunter);
			// 保存member表
			member.setHunter(hunter11);// 关联member表
			memberService.saveAndModify(member);// 保存member表
			// 保存批发商与行业分类（标签）关联记录表
			String[] aa = industryTypeIdList.split(",");
			for (String type : aa) {

				// 根据二级分类id查询一级分类id
				IndustryType industryType = industryTypeService.get(Long.valueOf(type));// 根据id获取二级分类
				IndustryType oneLevelIndustryType = industryType.getParentId();// 根据二级分类获取一级分类

				HunterIndustryType hunterIndustryType = new HunterIndustryType();
				hunterIndustryType.setIndustryType(industryType);// 二级行业分类
				hunterIndustryType.setOneLevelIndustryType(oneLevelIndustryType);// 一级行业分类
				hunterIndustryType.setHunter(hunter11);// 关联批发商
				try {
					hunterIndustryTypeService.saveAndModify(hunterIndustryType);
				} catch (Exception e) {
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
					logger.error("保存批发商与行业分类关联记录表发生异常:" + e.getMessage());
					result.setCode(0);
					result.setMsg("保存批发商与行业分类关联关系失败");
				}

			}
			Long hunterId = hunter11.getId();
			List<String> ruleList = phVO.getRuleAttachmentList();
			for (String id : ruleList) {
				if(id!=null){
					
				PhHunterRuleAttachment ph = phHunterRuleAttachmentService.get(Long.valueOf(id));
				if(ph==null){
					return  ResultUtils.returnError("照片不存在");
				}
				ph.setHunterId(hunterId);
				phHunterRuleAttachmentService.saveAndModify(ph);
				}
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("hunterId", hunterId);
			result.setCode(1);
			result.setMsg("批发商信息提交成功");
			result.setContent(data);

		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
			logger.error("批发商信息提交改发生异常:" + e.getMessage());
			result.setCode(0);
			result.setMsg("批发商信息提交失败");
		}

		return result;
	
	}

	/**
	 * 查询批发商配送范围
	 *
	 * @return
	 */
	@Override
	public Result hunterarearang(Long hunterId) {
		Hunter hunter = hunterDao.findOne(hunterId);
		String districts = hunter.getDistricts();
		//地址ID通过,拼接
		String[] districtArr = districts.split(",");
		List<Long> districtList = new ArrayList<>();
		for (String district :districtArr) {
			districtList.add(Long.parseLong(district));
		}
		List<PositionVO> positions = positionDao.selectAreaRang(districtList);
		Result result = new Result();
		result.setContent(positions);
		result.setCode(1);
		result.setMsg("获取批发商配送范围成功");
		return result;
	}

	@Override
	@Transactional(readOnly = false)
	public void updatehunterarearang(Long districtId, String districts, Long hunterId) {
		hunterDao.updatehunterarearang(districtId,districts,hunterId);
	}
}
