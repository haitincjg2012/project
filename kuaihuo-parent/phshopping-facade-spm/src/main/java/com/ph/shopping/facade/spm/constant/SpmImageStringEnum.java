package com.ph.shopping.facade.spm.constant;

/**
 * 
 * phshopping-facade-spm
 *
 * @description：供应商图片类型，供应商专用
 *
 * @author：liuy
 *
 * @createTime：2017年4月26日
 *
 * @Copyright @2017 by liuy
 */
public enum SpmImageStringEnum {
	
	BUSINESS_IMAGE_TYPE("1", "营业执照图片类型"),
	IDCARD_IMAGE_TYPE("2", "身份证图片类型");
	
    private String code;
    private String msg;
    
	private SpmImageStringEnum(String code, String msg) {
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
