package com.ph.shopping.facade.pay.service.impl;

import cm.ph.shopping.facade.order.entity.PurchaseSubOrder;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.ph.shopping.common.core.config.pay.PayConfiguration;
import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.common.core.exception.BizException;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.DateUtil;
import com.ph.shopping.common.util.order.OrderBizCode;
import com.ph.shopping.common.util.order.OrderUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.rsa.MD5;
import com.ph.shopping.facade.mapper.MemberDrawCashRecordMapper;
import com.ph.shopping.facade.mapper.MemberScoreMapper;
import com.ph.shopping.facade.mapper.PurchaseOrderMapper;
import com.ph.shopping.facade.mapper.ScoreTotalTradeMapper;
import com.ph.shopping.facade.member.entity.MemberDrawcashRecord;
import com.ph.shopping.facade.member.menum.MemberResultEnum;
import com.ph.shopping.facade.member.vo.MemberInfoByCashVO;
import com.ph.shopping.facade.pay.config.AlipayConfig;
import com.ph.shopping.facade.pay.config.UnionPayConfig;
import com.ph.shopping.facade.pay.constant.CashConstant;
import com.ph.shopping.facade.pay.dto.AlipayRefundDTO;
import com.ph.shopping.facade.pay.dto.DefrayDTO;
import com.ph.shopping.facade.pay.dto.HunterDefrayDTO;
import com.ph.shopping.facade.pay.entity.MemberScore;
import com.ph.shopping.facade.pay.entity.ScoreTotalTrade;
import com.ph.shopping.facade.pay.exception.AlipayException;
import com.ph.shopping.facade.pay.exception.AlipayExceptionEnum;
import com.ph.shopping.facade.pay.exception.CashException;
import com.ph.shopping.facade.pay.exception.CashExceptionEnum;
import com.ph.shopping.facade.pay.service.ICashService;
import com.ph.shopping.facade.pay.service.impl.order.PurchaseOrderService;
import com.ph.shopping.facade.pay.service.impl.score.ScoreService;
import com.ph.shopping.facade.pay.utils.union.*;
import com.ph.shopping.facade.profit.service.IMemberDrawCashService;
import com.ph.shopping.facade.profit.service.IUserDrawCashService;
import com.ph.shopping.facade.spm.entity.UserDrawcash;
import com.ph.shopping.facade.spm.vo.ManageBankCardInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import sun.security.krb5.SCDynamicStoreConfig;

import java.math.BigDecimal;
import java.rmi.MarshalException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @项目：phshopping-facade-member
 * @描述：会员提现service @作者： Mr.chang
 * @创建时间：2017年4月6日 @Copyright @2017 by Mr.chang
 */
@Component
@Service(version = "1.0.0")
public class CashServiceImpl implements ICashService {

	private static final Logger log = LoggerFactory.getLogger(CashServiceImpl.class);

	@Autowired
	private MemberDrawCashRecordMapper memberDrawCashRecordMapper;

	@Autowired
	PurchaseOrderMapper purchaseOrderMapper;

	@Autowired
	PurchaseOrderService purchaseOrderService;

	@Reference(version = "1.0.0")
	IUserDrawCashService iUserDrawCashService;

	@Reference(version = "1.0.0")
	IMemberDrawCashService iMemberDrawCashService;

	@Autowired
	ScoreService scoreService;

	@Autowired
	ScoreTotalTradeMapper scoreTotalTradeMapper;

	@Autowired
	MemberScoreMapper memberScoreMapper;



	@Autowired
	private PayConfiguration payConfiguration;


	/**
	 * 提现回调
	 * @param map
	 * @return
	 */
	@Transactional
	public String verifySingNotifyForYPay(Map<String, String> map) {
        log.info("提现验证结果入参:{}",JSON.toJSON(map));
		String orderNum =map.get("orderNum");
		String payStatus =map.get("payStatus");
		String moneyPay =map.get("money");
		if("".equals(orderNum) || payStatus == null){
			log.info("回调方法执行中,订单号:{},接收回调参数异常",orderNum);
			return "statuserror";
		}
		log.info("回调方法中返回的状态:{},回调方法第一步,订单号:{}",payStatus,orderNum);
		// 根据订单号获取提现记录信息
		MemberDrawcashRecord mdr = null;
		UserDrawcash userDrawCash = null;
		Long id;
		try {
			String prefix = orderNum.substring(0,6);
			if("KHSHTX".equals(prefix)){
				id = memberDrawCashRecordMapper.getUserCashIdByOrderNo(orderNum);
				userDrawCash = memberDrawCashRecordMapper.getCashTradeRowLock(id);
				if(userDrawCash == null || userDrawCash.getStatus() == null){
					log.info("回调方法执行中,订单号"+orderNum+",此订单未查询到");
					return "statuserror";
				}
			}else {
				id = memberDrawCashRecordMapper.getIdByOrderNo(orderNum);
				mdr = memberDrawCashRecordMapper.getRowLock(id);
				if(mdr == null || mdr.getStatus() == null){
					log.info("回调方法执行中,订单号"+orderNum+",此订单未查询到");
					return "statuserror";
				}
			}
		}catch (Exception e){
			log.info("回调方法执行中,订单号：{},此订单未查询到",orderNum);
			log.error(e.getMessage(),e);
			return "statuserror";
		}
		log.info("交易结果处理方法中-处理交易结果,"+"异步回调返回状态"+payStatus+"流水号"+orderNum);
		try{
			if("KHSHTX".equals(orderNum.substring(0,6))){
				Long moneyCash = userDrawCash.getScore().longValue();
				Long handingCharge = userDrawCash.getHandingCharge().longValue();
				log.info("moneyCash={},handingCharge={}",moneyPay,handingCharge);
				if(BigDecimal.valueOf(Long.valueOf(moneyPay)).compareTo(BigDecimal.valueOf(moneyCash).divide(BigDecimal.valueOf(100))) != 0){
					log.info("提现金额不一致,订单号:{},pay返回金额(单位:分):{},记录表中提现金额(未处理,单位为分的分):{}",orderNum,moneyPay,moneyCash);
					return "statuserror";
				}
				//判断订单状态是否异常
				byte status = userDrawCash.getStatus();//订单状态
				log.info("回调方法执行中,订单号:{},此订单的状态:{}",orderNum,status);
				if (status == 1 || status == 2) {
					log.info("回调方法执行中,订单号={},此订单的状态={},不在回调执行范围内,返回statuserror",orderNum,status);
					return "success";
				}
				if("2".equals(payStatus)){
					memberDrawCashRecordMapper.updateUserCashStatus(1,orderNum,"SUCCESS");
				} else if("3".equals(payStatus)){
					memberDrawCashRecordMapper.updateUserCashStatus(2,orderNum,"FAIL");
					userDrawCash.setScore(new BigDecimal(userDrawCash.getScore().longValue()+userDrawCash.getHandingCharge().longValue()));
					memberDrawCashRecordMapper.addUserBalance(userDrawCash);
					memberDrawCashRecordMapper.addUserBalanceTrade(userDrawCash);

				}
			} else {
				Long moneyCash = mdr.getScore().longValue();
				Long handingCharge = mdr.getHandingCharge();
				log.info("moneyCash={},handingCharge={}",moneyPay,handingCharge);
				if (BigDecimal.valueOf(Long.valueOf(moneyPay)).compareTo(BigDecimal.valueOf(moneyCash).divide(BigDecimal.valueOf(100))) != 0) {
					log.info("提现金额不一致,订单号:{},pay返回金额(单位:分):{},记录表中提现金额(未处理,单位为分的分):{}",orderNum,moneyPay,moneyCash);
					return "statuserror";
				}
				//判断订单状态是否异常
				byte status = mdr.getStatus();//订单状态
				log.info("回调方法执行中,订单号={},此订单的状态:{}" , orderNum ,status);
				if (status == 1 || status == 2) {
					log.info("回调方法执行中,订单号={},此订单的状态={},不在回调执行范围内,返回statuserror",orderNum,status);
					return "success";
				}
				// 如果成功
				if ("2".equals(payStatus)) {
					memberDrawCashRecordMapper.updateStatus(1, mdr.getOrderNo(), "SUCCESS");
					//失败
				} else if ("3".equals(payStatus)) {
					memberDrawCashRecordMapper.updateStatus(2, mdr.getOrderNo(), "FAIL");
					mdr.setScore(new BigDecimal(mdr.getScore().longValue()).add(BigDecimal.valueOf(CashConstant.HANDING_CHARGE)));
					memberDrawCashRecordMapper.addMemberBalance(mdr);
					memberDrawCashRecordMapper.addScoreIncomeTrade(mdr);
					MemberScore memberScore = memberScoreMapper.getMemberScoreByMemberId(mdr.getMemberId().toString());
                    Integer code =  mdr.getTransCode();
					ScoreTotalTrade scoreTotalTrade = new ScoreTotalTrade();
                    scoreTotalTrade.setMemberId(mdr.getMemberId());
                    scoreTotalTrade.setCreateTime(new Date());
                    scoreTotalTrade.setScore(mdr.getScore().longValue()-CashConstant.HANDING_CHARGE);
					scoreTotalTrade.setFee(CashConstant.HANDING_CHARGE);
					scoreTotalTrade.setOrderNo(null);
                    if (Objects.equals(code,2002)){
						scoreTotalTrade.setTransCode(2012);
						scoreTotalTrade.setTotalScore(memberScore.getShareMerchantScore());
					}
					else if (Objects.equals(code,2003)){
						scoreTotalTrade.setTransCode(2013);
						scoreTotalTrade.setTotalScore(memberScore.getShareMemberScore());
					}else{
						scoreTotalTrade.setTransCode(2014);
						scoreTotalTrade.setTotalScore(memberScore.getStoreManagerScore());
					}

					scoreTotalTradeMapper.insertSelective(scoreTotalTrade);

				}
			}
		}catch(Exception e){
			log.info("订单号:{},更新状态是异常:{}",orderNum,e.getMessage());
			log.error(e.getMessage(),e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "statuserror";
		}
		return "success";
	}



	@Override
	public Result defray(DefrayDTO defrayDTO) {
		log.info("============================开始提现流程================================");
		log.info("提现接口传入参数：" + JSON.toJSONString(defrayDTO));
		Result result = null;
		try {
			String message = defrayDTO.validateForm();
			// 校验提现传入参数
			if (Objects.nonNull(message)) {
				result = new Result(false, "", message);
				return result;
			}
			// 组装提现参数
			JSONObject param = new JSONObject();
			param.put("custOrderId", defrayDTO.getOrderNo());// 订单号
			param.put("partnerFlowNum", defrayDTO.getOrderNo());// 流水号
			// 格式化提现金额
			BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(defrayDTO.getAmount().trim()));
			int bankMoney = amount.intValue() / 100;
			param.put("orderAmount", bankMoney);// 提现金额
			param.put("payeeAcctNum", defrayDTO.getBankNo());// 收款方银行卡号
			param.put("payeeAcctName", defrayDTO.getAccountName());// 收款方户名
			param.put("payeeAcctType", UnionPayConfig.PAYEE_ACCT_TYPE); // PERSONAL
																		// 对私 ，
																		// BUSINESS
																		// 对公
			param.put("remarks", "提现操作");// 备注
			param.put("custId", UnionPayConfig.CUST_ID);// 商户号
			param.put("partnerId", UnionPayConfig.PARTNER_ID);// 合作渠道ID
			param.put("notifyUrl", UnionPayConfig.NOTIFY_URL); // 异步通知地址

			String jsb_url = UnionPayConfig.JSB_UNION_URL;// 贵州银联提现地址
			String privateKey = UnionPayConfig.PRIVATE_KEY;// 私钥

			// 签名
			String sign = "no_sign";
			sign = SignUtils.signBySoft(privateKey, SignUtils.getSignData(param));
			param.put("sign", sign);// 组装签名数据

			// 执行代付请求
			String reqMsgHasSign = param.toJSONString();
			log.info("贵州银联代付请求报文：" + reqMsgHasSign);
			// 发送请求报文
			String respStr = HttpUtils.execute(jsb_url, reqMsgHasSign, 60000);
			log.info("贵州银联代付响应报文：" + respStr);

			JSONObject respJson = JSONObject.parseObject(respStr);
			if (!SystemRetCode.SUCCESS.toString()
					.equalsIgnoreCase(respJson.getString(SystemRetField.RET_CODE.toString()))) {
				log.info(SystemRetField.RET_CODE.toString() + SystemRetCode.COM_ERROR.toString()
						+ SystemRetField.RET_DESC.toString() + "通讯异常，请查看日志");
				result = new Result(false, CashExceptionEnum.JSB_DEFRAY_ERROR.getCode(),
						CashExceptionEnum.JSB_DEFRAY_ERROR.getMsg());
				return result;
			}
			// 解析返回的json串，验签
			String respParamsStr = respJson.getString(SystemRetField.RET_BODY.toString());
			JSONObject respParamsJson = JSONObject.parseObject(respParamsStr);
			log.info("贵州银联提现接口返回数据：" + SystemRetField.RET_CODE.toString() + SystemRetCode.COM_ERROR.toString()
					+ SystemRetField.RET_DESC.toString() + respParamsJson);
			String retSign = respParamsJson.remove("sign") + "";
			log.info("代付接口返回签名值sign:" + retSign);
			// 验证平台签名 公钥
			boolean signResult = SignUtils.verifyingSign(UnionPayConfig.PUBLIC_KEY, retSign,
					SignUtils.getSignData(respParamsJson));
			log.info("返回验签结果验证：" + signResult);
			if (!signResult) {
				log.info(SystemRetField.RET_CODE.toString() + SystemRetCode.COM_ERROR.toString()
						+ SystemRetField.RET_DESC.toString() + "商户验平台签名失败，请检查公钥是否正确或者数据是否被篡改");
				result = new Result(false, CashExceptionEnum.DEFRAY_PAY_SIGN_ERROR.getCode(),
						CashExceptionEnum.DEFRAY_PAY_SIGN_ERROR.getMsg());
				return result;
			} else {
				// 正常情况一次成功的通讯
				result = doDefrayBus(respParamsJson.toJSONString(), defrayDTO);
				log.info("处理返回结果result:" + JSON.toJSONString(result));
			}
		} catch (Exception e) {
			log.error("提现业务操作异常：e={}" , e);
			throw new CashException(CashExceptionEnum.CASH_EXCEPTION);
		}
		log.info("============================结束提现流程================================");
		return result;
	}

	/**
	 * 提现业务处理
	 *
	 * @param respMsg
	 * @param defrayDTO
	 * @return
	 * @author Mr.Chang
	 */
	public Result doDefrayBus(String respMsg, DefrayDTO defrayDTO) {
		Result result = null;
		try {
			// 截取订单业务编码
			String bizCode = OrderUtil.getOrderBizCode(defrayDTO.getOrderNo());
			JSONObject respParamsJson = JSONObject.parseObject(respMsg);
			String retCode = respParamsJson.remove("retCode") + "";
			switch (bizCode) {
			// 商户、供应商、代理商提现业务处理
			case OrderBizCode.DRAW_CASH_NO:
				result = doSPMTradeBus(defrayDTO, retCode, bizCode);
				break;
			// 会员提现业务处理
			case OrderBizCode.MEMBER_DRAW_CASH:
				result = doMemberTradeBus(defrayDTO, retCode, bizCode);
				break;
			// 供应链进货订单退款业务处理
			case OrderBizCode.PURCHASE_GOODS:
				result = doPurchaseOrderTradeBus(defrayDTO, retCode, bizCode);

			}
		} catch (Exception e) {
			log.error("提现业务操作异常：e{}",  e);
			throw new CashException(CashExceptionEnum.CASH_EXCEPTION);
		}
		return result;
	}

	/**
	 * 处理商户、供应商、代理商交易业务
	 * 
	 * @param defrayDTO
	 * @param retCode
	 * @param bizCode
	 * @return
	 * @author Mr.Chang
	 */
	public Result doSPMTradeBus(DefrayDTO defrayDTO, String retCode, String bizCode) {
		try {
			log.info("处理提现用户ID:" + defrayDTO.getId() + ",订单号:" + defrayDTO.getOrderNo() + ",请求返回状态:" + retCode);
			if (SystemRetCode.SUCCESS.toString().equals(retCode)) {
//				log.info("===========================进入商户、供应商、代理商提现交易成功业务处理==================================");
//				iUserDrawCashService.updStatus(1, defrayDTO.getOrderNo(), retCode);
//				log.info("===========================商户、供应商、代理商提现业务处理成功==================================");
				return ResultUtil.getResult(RespCode.Code.SUCCESS);
			} else if ("FAIL".equals(retCode) || "TIMEOUT".equals(retCode) || "EXCPETION".equals(retCode)) {
				log.info("===========================进入商户、供应商、代理商提现交易失败业务处理==================================");
				iUserDrawCashService.updStatus(2, defrayDTO.getOrderNo(), retCode);
				iUserDrawCashService.backUserScore(defrayDTO.getOrderNo());
				log.info("贵州银联代付交易失败订单号:" + defrayDTO.getOrderNo());
				return ResultUtil.getResult(RespCode.Code.SUCCESS, retCode);
			} else {
				log.info("===========================进入商户、供应商、代理商提现交易未知业务处理==================================");
				log.info("贵州银联代付交易失败订单号:" + defrayDTO.getOrderNo() + "交易金额：" + defrayDTO.getAmount());
				return ResultUtil.getResult(RespCode.Code.FAIL, retCode);
			}
		} catch (Exception e) {
			log.error("更新提现交易状态异常：e={}" , e);
			throw new CashException(CashExceptionEnum.CASH_EXCEPTION);
		}
	}

	/**
	 * 处理会员交易业务逻辑
	 * 
	 * @param defrayDTO
	 * @param retCode
	 * @return
	 * @author Mr.Chang
	 */
	public Result doMemberTradeBus(DefrayDTO defrayDTO, String retCode, String bizCode) {
		try {
			log.info("请求参数 : {defrayDTO:" + JSON.toJSONString(defrayDTO) + ",retCode:" + retCode + ",bizCode:" + bizCode + "}");
			if (SystemRetCode.SUCCESS.toString().equals(retCode)) {
				log.info("===========================进入会员提现交易成功业务处理==================================");
				
//				return iMemberDrawCashService.updStatus(1, defrayDTO.getOrderNo(), retCode);
				return ResultUtil.getResult(RespCode.Code.SUCCESS);
			} else if ("FAIL".equals(retCode) || "TIMEOUT".equals(retCode) || "EXCPETION".equals(retCode)) {
				log.info("===========================进入会员提现交易失败业务处理==================================");
				//返回会员积
				return iMemberDrawCashService.updStatus(2, defrayDTO.getOrderNo(), retCode);
//				iMemberDrawCashService.backMemberScore(defrayDTO.getOrderNo());
//				return ResultUtil.getResult(RespCode.Code.SUCCESS);
			} else {
				log.info("===========================处理未知业务状态==================================");
				return ResultUtil.getResult(RespCode.Code.FAIL);
			}
		} catch (BizException e) {
			log.error("更新会员提现交易状态异常：e={}" , e);
			throw new BizException(e);
		}
	}

	@Override
	public Result defayCallBack(String encReq) {
		log.info("=========================进入提现异步回调验证方法=============================");
		Result result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			log.info("贵州银联回调序列化密文为:" + encReq);
			encReq = (String) objectMapper.readValue(encReq, TypeFactory.rawClass(String.class));

			JSONObject respJson = JSONObject.parseObject(encReq);
			String custOrderId = respJson.get("custOrderId") + "";// 订单Id
			String transStatus = respJson.get("transStatus") + "";// 交易状态
			String reqMsg = respJson.toJSONString();// 回调状态码

			log.info("贵州银联异步回调返回交易状态:" + transStatus + "订单号:" + custOrderId);
			if ("".equals(custOrderId) || custOrderId == null) {
				log.info("回调订单号" + custOrderId + "参数异常");
				result = new Result(false, CashExceptionEnum.DFRAY_CALL_BACK_PARAM_ERROR.getCode(),
						CashExceptionEnum.DFRAY_CALL_BACK_PARAM_ERROR.getMsg());
				return result;
			}

			String retSign = respJson.remove("sign") + "";
			log.info("获取贵州银联返回的验签sign:" + retSign);
			// 回调验签
			boolean signResult = SignUtils.verifyingSign(UnionPayConfig.PUBLIC_KEY, retSign, SignUtils.getSignData(respJson));
			log.info("贵州银联回调验签结果：" + signResult);
			if (!signResult) {
				log.info(SystemRetField.RET_CODE.toString() + SystemRetCode.COM_ERROR.toString()
						+ SystemRetField.RET_DESC.toString() + "商户验平台签名失败，请检查公钥是否正确或者数据是否被篡改");
				result = new Result(false, CashExceptionEnum.DEFRAY_PAY_SIGN_ERROR.getCode(),
						CashExceptionEnum.DEFRAY_PAY_SIGN_ERROR.getMsg());
				return result;
			} else {
				// 调用代付回调业务处理
				result = doCallbackBus(custOrderId, reqMsg);
			}
		} catch (Exception e) {
			log.error("贵州银联代付回调处理业务发生异常：e={}" , e);
			throw new CashException(CashExceptionEnum.DFRAY_CALL_BACK_EXCEPTION);
		}
		return result;
	}

	/**
	 * 处理贵州银联回调业务
	 * 
	 * @param custOrderId
	 * @param reqMsg
	 * @return
	 * @author Mr.Chang
	 */
	public Result doCallbackBus(String custOrderId, String reqMsg) {
		Result result = null;
		try {
			// 截取操作的业务代码
			String bizCode = OrderUtil.getOrderBizCode(custOrderId);
			log.info("贵州银联回调截取的订单业务码：" + bizCode);
			switch (bizCode) {
			// 商户提现业务处理
			case OrderBizCode.DRAW_CASH_NO:
				result = doSPMCallback(custOrderId, reqMsg, bizCode);
				break;
			// 会员提现业务处理
			case OrderBizCode.MEMBER_DRAW_CASH:
				result = doMemberCallback(custOrderId, reqMsg, bizCode);
				break;
			//供应链进货业务处理
			case OrderBizCode.PURCHASE_GOODS:
				result = doPurchaseCallback(custOrderId, reqMsg, bizCode);
				break;
			}
		} catch (Exception e) {
			log.error("贵州银联代付回调业务处理异常：e={}" , e);
			throw new BizException(e);
		}
		return result;
	}

	/**
	 * 处理商户回调业务
	 * 
	 * @param custOrderId
	 * @param reqMsg
	 * @return
	 * @author Mr.Chang
	 */
	public Result doSPMCallback(String custOrderId, String reqMsg, String bizCode) {
		try {
			log.info("============进入贵州银联商户、供应商、代理商提现回调交易成功业务订单处理=============");
			JSONObject jsonParam = JSONObject.parseObject(reqMsg);
			String transStatus = jsonParam.getString("transStatus");
			Result result = ResultUtil.getResult(RespCode.Code.SUCCESS);
			log.info("贵州银联异步回调返回状态" + transStatus + "流水号" + custOrderId);
			// 根据返回状态码做对应的业务操作
			// 更新提现订单
			if (transStatus.equals(SystemRetCode.SUCCESS.toString())) {
				log.info("============进入贵州银联商户、供应商、代理商提现回调交易成功业务订单处理=============");
				result = iUserDrawCashService.updStatus(1, custOrderId, transStatus);
				log.info("============完成贵州银联商户提现回调交易成功业务订单处理完成=============");
			} else {
				// 失败发货用户余额
				iUserDrawCashService.updStatus(2, custOrderId, transStatus);
				iUserDrawCashService.backUserScore(custOrderId);
				log.info("============提现失败，退款用户余额=============");
			}
			return result;
		} catch (Exception e) {
			log.error("贵州银联异步回调更新交易状态异常: e={}" , e);
			throw new CashException(CashExceptionEnum.CASH_EXCEPTION);
		}
	}

	/**
	 * 处理会员提现回调业务
	 * 
	 * @param custOrderId
	 * @param reqMsg
	 * @param bizCode
	 * @return
	 * @author Mr.Chang
	 */
	public Result doMemberCallback(String custOrderId, String reqMsg, String bizCode) {
		Result result = ResultUtil.getResult(RespCode.Code.SUCCESS);
		try {
			// // 根据回调码判断业务处理
			JSONObject jsonParam = JSONObject.parseObject(reqMsg);
			String transStatus = jsonParam.getString("transStatus");
			log.info("回调方法执行中,订单号" + custOrderId + ",返回的状态码:" + transStatus);
			// 处理返回数据
			// 根据返回状态码做对应的业务操作
			// 更新提现订单
			if (transStatus.equals(SystemRetCode.SUCCESS.toString())) {
				result = iMemberDrawCashService.updStatus(1, custOrderId, transStatus);
				log.info("============完成贵州银联会员提现回调交易成功业务订单处理完成=============");

			} else {
				// 失败返回会员积分
				result = iMemberDrawCashService.updStatus(2, custOrderId, transStatus);
//				iMemberDrawCashService.backMemberScore(custOrderId);
				log.info("============提现失败，退款会员余额=============");
			}
			return result;
		} catch (Exception e) {
			log.error("会员异步回调更新业务异常: e={}" , e);
			throw new BizException(e);
		}
	}

	/**
	 * @methodname updateCallbackBus 的描述：回调更新业务处理
	 * @param obj
	 * @param transStatus
	 * @param bizCode
	 * @param payNo
	 * @return com.ph.shopping.common.util.result.Result
	 * @author 郑朋
	 * @create 2017/6/7
	 */
	public Result updateCallbackBus(Object obj, String transStatus, String bizCode, String payNo) {
		Result result = ResultUtil.getResult(RespCode.Code.FAIL);
		try {
			switch (bizCode) {
			// 供应链进货退款
			case OrderBizCode.PURCHASE_GOODS:
				/**
				 * zhengpeng version-2.1
				 */
				PurchaseSubOrder purchaseSubOrder = (PurchaseSubOrder) obj;
				ManageBankCardInfoVO cardInfoVO = purchaseOrderService.getBankInfo(purchaseSubOrder.getPurchaserId());
				result = purchaseOrderService.orderRefund(purchaseSubOrder.getId().toString(), transStatus,
						cardInfoVO.getCardNo(), payNo);
				break;
			}
		} catch (Exception e) {
			log.error("异步回调更新交易状态异常: e={}", e);
			throw new CashException(CashExceptionEnum.CASH_EXCEPTION);
		}
		return result;
	}

	/**
	 * @methodname doPurchaseOrderTradeBus 的描述：进货订单退款业务处理
	 * @param defrayDTO
	 * @param retCode
	 * @param payNo
	 * @return com.ph.shopping.common.util.result.Result
	 * @author 郑朋
	 * @create 2017/6/6
	 */
	public Result doPurchaseOrderTradeBus(DefrayDTO defrayDTO, String retCode, String payNo) {
		Result result = ResultUtil.getResult(RespCode.Code.FAIL);
		result.setMessage("供应链进货订单退款失败");
		try {
			result = purchaseOrderService.orderRefund(defrayDTO.getId(), retCode, defrayDTO.getBankNo(), payNo);
		} catch (Exception e) {
			log.error("更新提现交易状态异常：e={}" , e);
			throw new CashException(CashExceptionEnum.CASH_EXCEPTION);
		}
		return result;

	}

	/**
	 * @methodname doPurchaseCallback 的描述：供应链支付回调业务处理
	 * @param custOrderNo
	 *            订单号
	 * @param reqMsg
	 * @param bizCode
	 * @return com.ph.shopping.common.util.result.Result
	 * @author 郑朋
	 * @create 2017/6/7
	 */
	public Result doPurchaseCallback(String custOrderNo, String reqMsg, String bizCode) {
		Result result = ResultUtil.getResult(RespCode.Code.FAIL);
		result.setMessage("供应链支付失败");
		try {
			PurchaseSubOrder purchaseSubOrder = purchaseOrderMapper.selPurSubOrderByOrderNo(custOrderNo);
			if (StringUtils.isEmpty(purchaseSubOrder)) {
				log.error("回调方法执行中,订单号 " + custOrderNo + ",此订单未查询到");
				throw new CashException(CashExceptionEnum.DFRAY_ORDER_NOT_EXIST);
			}
			Byte status = purchaseSubOrder.getRefundStatus();
			// 根据回调码判断业务处理
			JSONObject jsonParam = JSONObject.parseObject(reqMsg);
			String transStatus = jsonParam.getString("transStatus");
			// 银行返回流水号
			String payNo = jsonParam.getString("orderId");
			// 如果当期订单退款状态未是退款中，则不进行数据处理
			if (status != 1) {
				log.info("回调方法执行中,订单号 " + custOrderNo + ",此订单的退款状态: " + status + "不在回调执行范围内");
				result = new Result(false, CashExceptionEnum.DEFRAY_PAY_ORDER_NOT_RANGE.getCode(),
						CashExceptionEnum.DEFRAY_PAY_ORDER_NOT_RANGE.getMsg());
				return result;
			}
			// 处理返回数据
			result = updateCallbackBus(purchaseSubOrder, transStatus, bizCode, payNo);
		} catch (Exception e) {
			log.error("进货订单退款业务处理异常,e={}", e);
			throw new BizException(e);
		}
		return result;
	}

	@Override
	public Result alipayRefund(AlipayRefundDTO ard) {
		Result result;
		log.info("=====================开始支付宝退款业务处理============================");
		log.info("支付宝退款参数：" + JSON.toJSONString(ard));
		String message = ard.validateForm();
		// 校验提现传入参数
		if (Objects.nonNull(message)) {
			result = ResultUtil.getResult(AlipayExceptionEnum.PARAM_ERROR);
			return result;
		}
		try {
			// 获得初始化的AlipayClient
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GATEWAY_URL, AlipayConfig.APP_ID,
					AlipayConfig.RSA_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,
					AlipayConfig.SIGNTYPE);
			// 设置请求参数
			AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
			// 封装请求支付信息
			AlipayTradeRefundModel model = new AlipayTradeRefundModel();
			model.setOutTradeNo(ard.getOut_trade_no());
			model.setRefundAmount(ard.getAmount());
			model.setRefundReason(ard.getReason());
			alipayRequest.setBizModel(model);
			// 设置异步通知地址
			AlipayTradeRefundResponse response = alipayClient.execute(alipayRequest);
			if (response.isSuccess()) {
				// 业务处理 TODO
				result = new Result(true, response.getCode(), "支付宝退款成功");
			} else {
				result = new Result(false, response.getCode(), "支付宝退款失败");
			}
		} catch (Exception e) {
			log.error("支付宝退款异常：e={}" , e);
			throw new AlipayException(AlipayExceptionEnum.ALIPAY_REFUND_ERROR.getCode(),
					AlipayExceptionEnum.ALIPAY_REFUND_ERROR.getMsg());
		}
		log.info("=====================完成支付宝退款业务处理============================");
		return result;
	}



	@Transactional
	@Override
	public Result hunterDefray(HunterDefrayDTO hunterDefrayDTO) {
		log.info("批发商会员积分提现流程参数，hunterDefrayDTO={}", JSON.toJSONString(hunterDefrayDTO));
		Result result = ResultUtil.getResult(MemberResultEnum.Code.FAIL);
		String message = hunterDefrayDTO.validateForm();
		// 校验提现传入参数
		if (Objects.nonNull(message)) {
			return ResultUtil.getResult(MemberResultEnum.Code.REQUEST_DATA_ERROR);
		}
		MemberInfoByCashVO mch = memberDrawCashRecordMapper.getMemberInfo(hunterDefrayDTO.getId());
		try {
			//判断是否绑定银行卡
			if (StringUtils.isEmpty(mch.getBankCard())) {
				return  ResultUtil.getResult(CashExceptionEnum.NO_CHECKBIND_EXCEPTION);
			}
			//判断是否设置支付密码
			if (org.springframework.util.StringUtils.isEmpty(mch.getPayPwd())) {
				return  ResultUtil.getResult(CashExceptionEnum.PAY_PWD_EMPTY);
			}
			// 查询是否已进行实名认证
			if (mch.getCertification() != 2) {
				return ResultUtil.getResult(MemberResultEnum.ID_NOT_CERTIFIED);
			}
			long cashScore = hunterDefrayDTO.getCashScore() * 10000l;
			//判断提现积分是否大于可用积分
			if (cashScore > mch.getScore().longValue()) {
				return  ResultUtil.getResult(CashExceptionEnum.CASH_MONEY_PASS_ENABLE_ERROR);
			}
			//判断当前提现积分是否大于单笔最小金额以及提现积分是否小于单笔最大金额
			if (hunterDefrayDTO.getCashScore().longValue() * 10000 < CashConstant.MIN_CASH_MONEY || hunterDefrayDTO.getCashScore() * 10000 > CashConstant.MAX_CASH_MONEY) {
				return  ResultUtil.getResult(CashExceptionEnum.CASH_MONEY_PASS_MAX_ERROR);
			}
			//查询改用户当天提现的积分总和
			Long cashedScore = memberDrawCashRecordMapper.getSumScoreThisDay(hunterDefrayDTO.getId(),getCurrentDate());
			cashedScore += cashScore;
			if (cashedScore.longValue() > CashConstant.MAX_CASH_MONEY_EVERY_DAY) {
				return  ResultUtil.getResult(CashExceptionEnum.CASH_MONEY_PASS_MAX_ERROR);
			}
			//判断支付密码是否正确
			if (!MD5.getMD5Str(hunterDefrayDTO.getPayPwd().trim()).equals(mch.getPayPwd())) {
				return  ResultUtil.getResult(CashExceptionEnum.PAY_PWD_ERROR);
			}

			//生成代付交易批次号
			String batchNo = UniqueUtils.getBathNo();
			Long score = cashScore - CashConstant.HANDING_CHARGE;
			//新增提现记录
			com.ph.shopping.facade.member.entity.MemberDrawcashRecord mdr = new com.ph.shopping.facade.member.entity.MemberDrawcashRecord();
			mdr.setMemberId(hunterDefrayDTO.getId());
			mdr.setOrderNo(OrderUtil.createOrderCode(OrderBizCode.MEMBER_DRAW_CASH));
			mdr.setScore(new BigDecimal(score));
			mdr.setHandingCharge(CashConstant.HANDING_CHARGE);
			mdr.setMemberName(mch.getRealName());
			mdr.setBankCardNo(mch.getBankCard());
			mdr.setBatchNo(batchNo);
			mdr.setStatus((byte)0);
			mdr.setRequestIp(hunterDefrayDTO.getRequestIP());
			mdr.setAuditState((byte)0);
			mdr.setCreaterId(hunterDefrayDTO.getId());
			mdr.setCreateTime(new Date());
			memberDrawCashRecordMapper.insertUseGeneratedKeys(mdr);
			//扣积分
			long count = scoreService.memberDrawCashTrade(hunterDefrayDTO.getId(), TransCodeEnum.MEMBER_DRAWCASH,score,mdr.getOrderNo(),CashConstant.HANDING_CHARGE);
			log.info("批发商会员调用积分扣除返回结果：count={}", count);
			if (count <= 0) {
				throw new CashException("批发商会员调用积分扣除失败");
			}
			/*DefrayDTO defrayDTO = new DefrayDTO(mdr.getId().toString(), batchNo, mch.getBankCard(), mch.getRealName(),
					score.toString(), mch.getBankName(), mdr.getOrderNo());
			log.info("批发商会员调用提现接口入参，defrayDTO={}",JSON.toJSONString(defrayDTO));
			//提现
			result = hunterDefray(defrayDTO);
			if (RespCode.Code.REQUEST_DATA_ERROR.getCode().equals(result.getCode())) {
				throw new CashException("请求参数不全");
			}
			log.info("批发商调用提现接口返回值，result={}",JSON.toJSONString(result));*/
			result = ResultUtil.getResult(CashExceptionEnum.Code.SUCCESS);
		} catch (Exception e) {
			log.error("批发商会员调用积分提现异常, e={}", e);
			//事物手动回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		log.info("批发商会员积分提现返回结果, result={}", JSON.toJSONString(result));
		return result;
	}


	private Date getCurrentDate() throws ParseException {
		Date date = new Date();
		return DateUtil.parseDate(DateUtil.dateToStr(date,DateUtil.YMR_SLASH)) ;
	}

	private Result hunterDefray(DefrayDTO defrayDTO) {
		log.info("============================批发商会员开始提现流程================================");
		log.info("批发商会员提现接口传入参数：" + JSON.toJSONString(defrayDTO));
		Result result = ResultUtil.getResult(RespCode.Code.FAIL);
		try {
			String message = defrayDTO.validateForm();
			// 校验提现传入参数
			if (Objects.nonNull(message)) {
				return ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
			}
			// 组装提现参数
			JSONObject param = new JSONObject();
			param.put("custOrderId", defrayDTO.getOrderNo());// 订单号
			param.put("partnerFlowNum", defrayDTO.getOrderNo());// 流水号
			// 格式化提现金额
			BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(defrayDTO.getAmount().trim()));
			int bankMoney = amount.intValue() / 100;
			param.put("orderAmount", bankMoney);// 提现金额
			param.put("payeeAcctNum", defrayDTO.getBankNo());// 收款方银行卡号
			param.put("payeeAcctName", defrayDTO.getAccountName());// 收款方户名
			param.put("payeeAcctType", UnionPayConfig.PAYEE_ACCT_TYPE); // PERSONAL
			// 对私 ，
			// BUSINESS
			// 对公
			param.put("remarks", "提现操作");// 备注
			param.put("custId", UnionPayConfig.CUST_ID);// 商户号
			param.put("partnerId", UnionPayConfig.PARTNER_ID);// 合作渠道ID
			param.put("notifyUrl", payConfiguration.getCashNOTIFYURL()); // 异步通知地址

			String jsb_url = payConfiguration.getJsbUnionUrl();// 贵州银联提现地址
			String privateKey = UnionPayConfig.PRIVATE_KEY;// 私钥

			// 签名
			String sign = "no_sign";
			sign = SignUtils.signBySoft(privateKey, SignUtils.getSignData(param));
			param.put("sign", sign);// 组装签名数据

			// 执行代付请求
			String reqMsgHasSign = param.toJSONString();
			log.info("贵州银联代付请求报文：" + reqMsgHasSign);
			// 发送请求报文
			String respStr = HttpUtils.execute(jsb_url, reqMsgHasSign, 60000);
			log.info("贵州银联代付响应报文：" + respStr);

			JSONObject respJson = JSONObject.parseObject(respStr);
			if (!SystemRetCode.SUCCESS.toString()
					.equalsIgnoreCase(respJson.getString(SystemRetField.RET_CODE.toString()))) {
				log.info(SystemRetField.RET_CODE.toString() + SystemRetCode.COM_ERROR.toString()
						+ SystemRetField.RET_DESC.toString() + "通讯异常，请查看日志");
				result = new Result(false, CashExceptionEnum.JSB_DEFRAY_ERROR.getCode(),
						CashExceptionEnum.JSB_DEFRAY_ERROR.getMsg());
				return result;
			}
			// 解析返回的json串，验签
			String respParamsStr = respJson.getString(SystemRetField.RET_BODY.toString());
			JSONObject respParamsJson = JSONObject.parseObject(respParamsStr);
			log.info("贵州银联提现接口返回数据：" + SystemRetField.RET_CODE.toString() + SystemRetCode.COM_ERROR.toString()
					+ SystemRetField.RET_DESC.toString() + respParamsJson);
			String retSign = respParamsJson.remove("sign") + "";
			log.info("代付接口返回签名值sign:" + retSign);
			// 验证平台签名 公钥
			boolean signResult = SignUtils.verifyingSign(UnionPayConfig.PUBLIC_KEY, retSign,
					SignUtils.getSignData(respParamsJson));
			log.info("返回验签结果验证：" + signResult);
			if (!signResult) {
				log.info(SystemRetField.RET_CODE.toString() + SystemRetCode.COM_ERROR.toString()
						+ SystemRetField.RET_DESC.toString() + "商户验平台签名失败，请检查公钥是否正确或者数据是否被篡改");
				result = new Result(false, CashExceptionEnum.DEFRAY_PAY_SIGN_ERROR.getCode(),
						CashExceptionEnum.DEFRAY_PAY_SIGN_ERROR.getMsg());
				return result;
			} else {
				// 正常情况一次成功的通讯
				// 截取订单业务编码
				String bizCode = OrderUtil.getOrderBizCode(defrayDTO.getOrderNo());
				JSONObject respParamsJson1 = JSONObject.parseObject(respParamsJson.toJSONString());
				String retCode = respParamsJson1.remove("retCode") + "";
				//处理业务
				doHunterMemberTradeBus(defrayDTO,retCode,bizCode);
				log.info("处理返回结果result:" + JSON.toJSONString(result));
			}
		} catch (Exception e) {
			log.error("提现业务操作异常：e={}" , e);
			throw new CashException(CashExceptionEnum.CASH_EXCEPTION);
		}
		log.info("============================结束提现流程================================");
		return result;
	}


	/**
	 * 处理会员交易业务逻辑
	 *
	 * @param defrayDTO
	 * @param retCode
	 * @return
	 * @author Mr.Chang
	 */
	public void doHunterMemberTradeBus(DefrayDTO defrayDTO, String retCode, String bizCode) {
		log.info("请求参数 : {defrayDTO:" + JSON.toJSONString(defrayDTO) + ",retCode:" + retCode + ",bizCode:" + bizCode + "}");
		if (SystemRetCode.SUCCESS.toString().equals(retCode)) {
			log.info("===========================进入会员提现交易成功业务处理==================================");
			//scoreService.updStatus(1, defrayDTO.getOrderNo(), retCode);
		} else if ("FAIL".equals(retCode) || "TIMEOUT".equals(retCode) || "EXCPETION".equals(retCode)) {
			log.info("===========================进入会员提现交易失败业务处理==================================");
			//返回会员积
			scoreService.updStatus(2, defrayDTO.getOrderNo(), retCode);
			scoreService.backMemberScore(defrayDTO.getOrderNo());
		} else {
			log.info("===========================处理未知业务状态==================================");
		}
	}


	public static void main(String[] args) {

		Long moneyCash = 950000L;
		String moneyPay ="9500";
		if(BigDecimal.valueOf(Long.valueOf(moneyPay)).compareTo(BigDecimal.valueOf(moneyCash).divide(BigDecimal.valueOf(100))) != 0){
			System.out.println("fail");
		}else{
			System.out.println("succe");
		}
	}



}
