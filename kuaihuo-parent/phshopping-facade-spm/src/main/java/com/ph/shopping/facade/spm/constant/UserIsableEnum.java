package com.ph.shopping.facade.spm.constant;

/**
 * 
 * phshopping-facade-spm
 *
 * @description：用户是否启用枚举
 *
 * @author：liuy
 *
 * @createTime：2017年4月26日
 *
 * @Copyright @2017 by liuy
 */
public enum UserIsableEnum {
	USER_ISABLE_YES("1", "用户启用"),
	USER_ISABLE_NO("2", "用户冻结");
	
    private String code;
    private String msg;
	private UserIsableEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
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
