package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;

/**
 * 
 * @ClassName: BackDrawCashVO
 * @Description: 提现退款数据
 * @author 王强
 * @date 2017年6月24日 下午8:55:22
 */
public class BackMemberDrawCashVO implements Serializable {

	private static final long serialVersionUID = -2469965653746324035L;

	private Long memberId; // 会员id
	private Long score;// 金额

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

}
