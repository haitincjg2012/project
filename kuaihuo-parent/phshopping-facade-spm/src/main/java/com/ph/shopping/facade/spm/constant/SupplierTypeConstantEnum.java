package com.ph.shopping.facade.spm.constant;

/**
 * 
 * phshopping-facade-spm
 *
 * @description：供应商类型枚举
 *
 * @author：liuy
 *
 * @createTime：2017年4月26日
 *
 * @Copyright @2017 by liuy
 */
public enum SupplierTypeConstantEnum {

	SUPPLIER_TYPE_TOTAL("1", "全国供应商"),
	SUPPLIER_TYPE_LOCAL("2", "本地供应商");
	
    private String code;
    private String msg;
	private SupplierTypeConstantEnum(String code, String msg) {
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
