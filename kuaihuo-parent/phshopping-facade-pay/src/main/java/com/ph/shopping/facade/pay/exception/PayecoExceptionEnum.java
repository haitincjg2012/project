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
public enum PayecoExceptionEnum implements RespCode{

	PAYECO_RETURN_EXCEPTION("110001","易联支付回调更新订单异常"),
	PAYECO_AMOUNT_EXCEPTION("110002","易联支付回调金额错误"),
	PAYECO_MD5_EXCEPTION("110003","加密传不一致");

	PayecoExceptionEnum(String code, String msg) {
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
