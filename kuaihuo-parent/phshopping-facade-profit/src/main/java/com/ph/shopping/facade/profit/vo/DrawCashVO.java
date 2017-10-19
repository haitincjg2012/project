package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;

public class DrawCashVO implements Serializable {
	
	private static final long serialVersionUID = -7889841917757953025L;
	
	private Long score;//提现金额
	
	private Long memberId;//会员id

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
}
