package com.ph.shopping.facade.profit.constant;

/**
 * 
 * @ClassName: AuditEnum
 * @Description: 审核状态值
 * @author 王强
 * @date 2017年6月13日 下午2:59:17
 */
public enum AuditEnum {

	OPERATOR_PASSING(0, "[运]审核中"), TREASURER_PASSING(1, "[财]审核中"), OPERATOR_UNPASS(2, "[运]未通过"), TREASURER_UNPASS(3,
			"[财]未通过"), PASSED(4, "已通过");

	private AuditEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private int code;
	private String msg;

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
