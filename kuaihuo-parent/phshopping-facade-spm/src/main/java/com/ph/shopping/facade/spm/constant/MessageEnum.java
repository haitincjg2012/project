package com.ph.shopping.facade.spm.constant;

import com.ph.shopping.common.util.core.RespCode;

public enum MessageEnum implements RespCode {
	ADD_DRAWCASH_FAIL("20004","新增余额记录失败"),
	BALANCE_EXCEED("20003","已超过今天的提现额度"),
	BALANCE_BETWEEN("20002","提现金额必须在100~10000之间"),
	BALANCE_NOT_ENOUGH("20001","余额不足"),
	ERROR_USER("20003","用户信息不全，请补全余额信息"),
	TEMPORARILY_UNABLE_DRAWCASH("20004","暂不能提现");
	
	MessageEnum(String code, String msg) {
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
