package com.ph.shopping.facade.pay.utils.commonpay;

import java.util.HashMap;
import java.util.Map;

public class CodeConstExt {
	public static final String JDcash="京东代付";
	public static final String JDpay="京东支付";
	public static final String YLpay="易联支付";
	public static final String GZYLpay="贵州银联代付";
	public static final String YLcash="易联代付";
	public static final String WXpay="微信支付";
	public static final String ALIpay="支付宝支付";
	public static final String YSBPay="银生宝支付";
	public static final String YSBcash="银生宝代付";
	public static final String SYXpay="商银信支付";
	public static final String SYXcash="商银信代付";
	public static final String BFpay="宝付支付";
	public static final String BFcash="宝付代付";
	public static final String PFSMpay="浦发扫码支付";
	public static final String RETURN_PARAM_NULL="RETURN_PARAM_NULL";//返回数据为null
	public static final String SYSTEM_ERROR="SYSTEM_ERROR";
	public static final String OUT_TRADE_NO_EXIST="OUT_TRADE_NO_EXIST";
	public static final String TRADE_NOT_EXIST="TRADE_NOT_EXIST";
	public static final String PAY_FAIL_EXCEPTION="FAIL";
	public static final String SUCCESS="0000";
	
	public static final String TRADE_FINI="FINI";
	public static final String TRADE_CLOS="CLOS";
	public static final String TRADE_WPAR="WPAR";
	public static final String TRADE_BUID="BUID";
	public static final String TRADE_ACSU="ACSU";
	
	public static final String PAY_ERROR_NULL="PAY_ERROR_NULL";
	public static final String PAY_ERROR_MONEY_ERROR="PAY_ERROR_MONEY_ERROR";
	public static final String PAY_ERROR_BALANCE_ERROR="PAY_ERROR_BALANCE_ERROR";
	
	public static final String PAY_SYSTEM_FROM_PHSHOP_M="PAY_SYSTEM_FROM_PHSHOP_M";//普惠商城手机端
	public static final String PAY_SYSTEM_FROM_PHSHOP="PAY_SYSTEM_FROM_PHSHOP";//普惠
	public static final String PAY_SYSTEM_FROM_YDSHOP="PAY_SYSTEM_FROM_YDSHOP";//易订商城
	public static final String PAY_SYSTEM_FROM_BDSHOP="PAY_SYSTEM_FROM_BDSHOP";//本地电商
	public static final String PAY_SYSTEM_FROM_T="PAY_SYSTEM_FROM_T";//通权
	public static final String PAY_SYSTEM_FROM_BDBD="PAY_SYSTEM_FROM_BDBD";//JAVA本地报单
	public static final String PAY_SYSTEM_FROM_LT="PAY_SYSTEM_FROM_LT";//批发商
	public static final String PAY_SYSTEM_FROM_JQ="PAY_SYSTEM_FROM_JQ";//健桥
	public static final String PAY_SYSTEM_FROM_NET="PAY_SYSTEM_FROM_NET";//NET 本地
	public static final String PAY_SYSTEM_FROM_NET_XYT="PAY_SYSTEM_FROM_NET_XYT";//NET新业态
	public static final String PAY_SYSTEM_FROM_CW="PAY_SYSTEM_FROM_CW";//财务后台
	public static final String PAY_SYSTEM_FROM_KHAPP="PAY_SYSTEM_FROM_KHAPP";//快火App
	public static final String PAY_SYSTEM_FROM_KHAPP_ZG="PAY_SYSTEM_FROM_KHAPP_ZG";//快火掌柜
	public static final String PAY_SYSTEM_FROM_KHAPP_PF="PAY_SYSTEM_FROM_KHAPP_PF";//快火批发

	
	/**
	 * 返回结果描述
	 */
	public static final Integer FAIL_CODE=1;//失败
	public static final Integer SUCCESS_CODE=0;//成功
	
	public static final String ENTITY_NULL_EXCEPTION="实体空异常";
	public static final String PARAM_NULL_EXCEPTION="参数空异常";
	
	public static final String TIME_EXCEPTION="当前时间无法操作";
	
	public static final String MONEY_EXCEPTION="提现金额异常";
	public static final String BALANCE_EXCEPTION="当前余额异常";
	
	public static final String PAY_EXCEPTION="服务器通信异常";
	
	public static final String PAY_SUEECSS="success";
	public static final String PAY_FAIL="fail";
	public static final String PAY_STATUS_ERROR="statuserror";
	
	private static Map<String,String> codeMap = new HashMap<String, String>();
	static{
		codeMap.put("0000","成功");
		codeMap.put("EXPARTNER_INFO_UNCORRECT","传入商户接口信息不正确"); 
		codeMap.put("ILLEGAL_SIGN","签名验证出错");
		codeMap.put("ILLEGAL_ARGUMENT","输入参数有错误"); 
		codeMap.put("ILLEGAL_AUTHORITY","权限不正确");
		codeMap.put("CUSTOMER_NOT_EXIST","提交会员不存在"); 
		codeMap.put("ILLEGAL_CHARSET","字符集不合法"); 
		codeMap.put("ILLEGAL_CLIENT_IP","客户端IP地址无权访问服务"); 
		codeMap.put("SYSTEM_ERROR","系统错误");//（非失败，不能按失败处理）
		codeMap.put("OUT_TRADE_NO_EXIST","外部交易号已经存在");// （非失败，不能按失败处理）
		codeMap.put("TRADE_NOT_EXIST","交易不存在");// （如果查询返回此信息，不能以失败处理）
		codeMap.put("ILLEGAL_TRADE_TYPE","无效交易类型"); 
		codeMap.put("BUYER_USER_NOT_EXIST","买家会员不存在"); 
		codeMap.put("SELLER_USER_NOT_EXIST","卖家会员不存在"); 
		codeMap.put("BUYER_SELLER_EQUAL","买家、卖家是同一帐户 ");
		codeMap.put("USER_STATE_ERROR","会员状态不正确");
		codeMap.put("COMMISION_ID_NOT_EXIST","佣金收取帐户不存在"); 
		codeMap.put("COMMISION_SELLER_DUPLICATE","收取佣金帐户和卖家是同一帐户"); 
		codeMap.put("COMMISION_FEE_OUT_OF_RANGE","佣金金额超出范围"); 
		codeMap.put("TOTAL_FEE_OUT_OF_RANGE","交易总金额超出范围"); 
		codeMap.put("ILLEGAL_AMOUNT_FORMAT","非法金额格式");
		codeMap.put("ILLEGAL_TRADE_AMMOUT","交易金额不正确");
		codeMap.put("ILLEGAL_TRADE_CURRENCY","交易币种不正确");
		codeMap.put("SELF_TIMEOUT_NOT_SUPPORT","不支持自定义超时"); 
		codeMap.put("COMMISION_NOT_SUPPORT","不支持佣金 ");
		codeMap.put("VIRTUAL_NOT_SUPPORT","不支持虚拟収货方式"); 
		codeMap.put("PAYMENT_LIMITED","支付受限");
		codeMap.put("ILLEGAL_BANK_CARD_NO","卡号不正确");
		codeMap.put("ILLEGAL_BANK_CARD_VALID_PERIOD","卡有效期不正确"); 
		codeMap.put("ILLEGAL_ID_CARD_NO","身份证号码不正确");
		codeMap.put("ILLEGAL_BANK_CARD_NAME","持卡人姓名不正确");
		codeMap.put("ILLEGAL_BANK_CARD_TYPE","卡类型不正确 ");
		codeMap.put("REFUND_FAILED","退款失败");
		codeMap.put("CURRENT_PAY_CANNOT_REVOKE","当前支付请求状态无法撤销");
		codeMap.put("CURRENT_USER_DIFFERENT_FROM","当前用户和已登录绑定用户不一致");
		codeMap.put("ILLEGAL_PAY_TYPE","无效支付类型");
		codeMap.put("USER_STATE_ERROR","用户状态错误");
		codeMap.put("ACCOUNT_BALANCE_NOT_ENOUGH","账户余额不足");
	}
	
/*	public static boolean isContainCode(String code){
		if(StrShyUtil.empty(code)){
			return false;
		}
		return codeMap.containsKey(code);
		
	}*/

}
