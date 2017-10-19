package com.ph.shopping.common.core.customenum;

/**
 * 
 * @项目：phshopping-common-core
 *
 * @描述：充值类型枚举
 *
 * @作者： liuy
 *
 * @创建时间：2017年4月1日
 *
 * @Copyright @2017 by liuy
 */
public enum ChargeEnum {
    CHARGE_TYPE(1,"充值"),
    SUPPLY_CHAIN_CHARGE(2,"供应链支付-银行"),
    ONLINE_ORDER(3,"线上订单支付-银行"),

    CHARGEING(1,"充值中"),
    CHARGE_SUCCESS(2,"充值成功"),
    CHARGE_FALIED(3,"充值失败");
	
	private int code;
	private String msg;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	private ChargeEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
