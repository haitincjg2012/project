package com.ph.shopping.facade.pay.exception;

import com.ph.shopping.common.util.core.RespCode;

/**
 * @项目：phshopping-facade-pay
 *
 * @描述：易联支付异常码
 *
 * @作者： Mr.chang
 *
 * @创建时间：2017年4月26日
 *
 * @Copyright @2017 by Mr.chang
 */
public enum CommonPayExceptionEnum implements RespCode{

	COMMON_PAY_RETURN_EXCEPTION("170001","北京通用支付回调更新订单异常"),
	COMMON_PAY_AMOUNT_EXCEPTION("170002","北京通用支付回调金额错误"),
	COMMON_PAY_PARAM_EXCEPTION("170003","参数错误"),
	COMMON_PAY_ORDER_NOT_EXIST("170004","参数错误"),
	;

	CommonPayExceptionEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private String code;
	private String msg;


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
