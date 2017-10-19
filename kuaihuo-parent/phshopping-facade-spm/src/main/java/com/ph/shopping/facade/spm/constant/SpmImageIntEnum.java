package com.ph.shopping.facade.spm.constant;

/**
 * 
 * phshopping-facade-spm
 *
 * @description：代理商、商户、供应商图片枚举（不包括供应商图片类型（String））
 *
 * @author：liuy
 *
 * @createTime：2017年4月26日
 *
 * @Copyright @2017 by liuy
 */
public enum SpmImageIntEnum {
	BUSINESS_IMAGE_INT_TYPE(1, "营业执照图片int类型"),
	IDCARD_IMAGE_INT_TYPE(2, "身份证图片int类型"),
	SHOP_IMAGE_INT_TYPE(3, "店铺图片int类型"),
	BUSINESS_IMAGE_SORT(1, "营业执照图片排序"),
	IDCARD1_IMAGE_SORT(2, "身份证图片排序"),
	IDCARD2_IMAGE_SORT(3, "身份证图片排序"),
	SHOP_IMAGE_SORT(3, "店铺图片排序");

	private int code;
	private String msg;
	private SpmImageIntEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
