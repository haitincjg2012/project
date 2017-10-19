package com.ph.shopping.facade.pay.exception;

import com.ph.shopping.common.util.core.RespCode;

public enum CashExceptionEnum implements RespCode {
	ID_IS_EMPTY("140001","未绑定身份证"),
	NO_CHECKBIND_EXCEPTION("140002", "请绑定银行卡"),
	PAY_PWD_EMPTY("140003","请设置支付密码"),
	ID_NOT_CERTIFIED("140004","请实名认证"),
	CASH_MONEY_PASS_ENABLE_ERROR("140005","提现积分大于可用积分"),
	CASH_MONEY_PASS_MAX_ERROR("140006","每次提现最低100，最高10000，每天总额不超过10000"),
	SMS_CODE_EMPTY("140007","未发送短信验证码"),
	SMS_CODE_INVALID("140008","短信验证码失效"),
	SMS_CODE_ERROR("140009","短信验证码错误"),
	PAY_PWD_ERROR("140010","支付密码输入错误"),

	RESPONSE_SUCCESS("200","业务处理成功"),
	//提现
	JSB_DEFRAY_ERROR("150001","贵州银联提现错误"),
	DEFRAY_PAY_SIGN_ERROR("150002","贵州银联验签错误"),
	DEFRAY_PAY_TRADE_FAIL("150003",""),
	CASH_EXCEPTION("150004","提现异常"),
	DFRAY_CALL_BACK_PARAM_ERROR("150005","提现异常"),
	DFRAY_CALL_BACK_EXCEPTION("150006","提现回调异常"),
	DFRAY_ORDER_NOT_EXIST("150007","提现回调订单不存在异常"),
	DEFRAY_PAY_ORDER_NOT_RANGE("150008","订单不在回调执行范围");
	

	CashExceptionEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private String code;
	private String msg;


	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}


}
