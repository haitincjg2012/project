package com.ph.shopping.facade.pay.enums;

/**
 * @项目：phshopping-facade-pay
 *
 * @描述：支付状态enum
 *
 * @作者： Mr.chang
 *
 * @创建时间：2017年3月28日
 *
 * @Copyright @2017 by Mr.chang
 */
public enum PayStatusEnum {

	PAY_SUCCESS(1,"支付成功"),
    PAY_FAILURE(2,"支付失败");

    private int code;
	private String name;
	  
	private PayStatusEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
