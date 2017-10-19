package com.ph.shopping.facade.spm.constant;

/**
 * 
 * phshopping-facade-spm
 *
 * @description：代理、商户、供应商状态
 *
 * @author：liuy
 *
 * @createTime：2017年4月26日
 *
 * @Copyright @2017 by liuy
 */
public enum SpmStatusEnum {
	CHECK_PENDING(0, "待审核"),
	EXAMINE_YES(1, "审核通过"),
	EXAMINE_NO(2, "审核不通过"),
	ISABLE_YES(1, "解冻"),
	ISABLE_NO(3, "冻结");

    private int code;
    private String msg;
	private SpmStatusEnum(int code, String msg) {
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
