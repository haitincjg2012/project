package com.ph.shopping.facade.score.vo;

import java.io.Serializable;

/**
 * 
* @ClassName: MemberScoreVo1
* @Description: 返回会员积分vo
* @author 王强
* @date 2017年4月24日 下午4:09:19
 */
public class MemberScoreVO1 implements Serializable {
	private static final long serialVersionUID = 1026589453592885797L;
	private long enablescore;//可用积分
	private long standbyscore;//待用积分
	private long drawcashScore;//已提现积分

	private long memberId;

	public long getEnablescore() {
		return enablescore;
	}

	public void setEnablescore(long enablescore) {
		this.enablescore = enablescore;
	}

	public long getStandbyscore() {
		return standbyscore;
	}

	public void setStandbyscore(long standbyscore) {
		this.standbyscore = standbyscore;
	}

	public long getDrawcashScore() {
		return drawcashScore;
	}

	public void setDrawcashScore(long drawcashScore) {
		this.drawcashScore = drawcashScore;
	}

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
}
