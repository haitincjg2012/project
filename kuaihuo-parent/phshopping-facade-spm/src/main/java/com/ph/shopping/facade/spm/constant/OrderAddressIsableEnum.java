package com.ph.shopping.facade.spm.constant;

/**
 * 
 * phshopping-facade-spm
 *
 * @description：仓库地址是否启用枚举
 *
 * @author：liuy
 *
 * @createTime：2017年4月26日
 *
 * @Copyright @2017 by liuy
 */
public enum OrderAddressIsableEnum {
	ORDER_ADDRESS_ISABLE_YES(Byte.valueOf("1"), "仓库地址启用"),
	ORDER_ADDRESS_ISABLE_NO(Byte.valueOf("2"), "仓库地址删除");
	
	private Byte code;
	private String msg;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Byte getCode() {
		return code;
	}
	public void setCode(Byte code) {
		this.code = code;
	}
	private OrderAddressIsableEnum(Byte code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
