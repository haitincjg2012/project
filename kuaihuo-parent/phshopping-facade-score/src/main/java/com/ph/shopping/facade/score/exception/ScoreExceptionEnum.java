package com.ph.shopping.facade.score.exception;

public enum ScoreExceptionEnum {

	SCORE_EXCEPTION("90001","积分异常"),
	UPDATE_SCORE("90002","更新订单返积分模块失败"),
	UPDATE_MEMBER_SCORE_FAIL("90003","更新会员可用积分失败"),
	UPDATE_REWARDSCORE("90004","更新会员奖励积分失败");

	private ScoreExceptionEnum(String msg, String code) {
		this.msg = msg;
		this.code = code;
	}

	private String msg;
	private String code;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
