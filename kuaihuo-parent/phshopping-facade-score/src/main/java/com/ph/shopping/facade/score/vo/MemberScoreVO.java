package com.ph.shopping.facade.score.vo;

import java.io.Serializable;
/**
 * 
* @ClassName: MemberScoreVo
* @Description: 与前端交互VO
* @author 王强
* @date 2017年3月24日 下午3:32:11
 */
public class MemberScoreVO implements Serializable {
	
	private static final long serialVersionUID = 3815461764330893245L;
	
	private double enableScore1;//可用积分
	private double standbyscore1;//待用积分
	private double drawcashScore1;//已提现积分

	public double getEnableScore1() {
		return enableScore1;
	}

	public void setEnableScore1(double enableScore1) {
		this.enableScore1 = enableScore1;
	}

	public double getStandbyscore1() {
		return standbyscore1;
	}

	public void setStandbyscore1(double standbyscore1) {
		this.standbyscore1 = standbyscore1;
	}

	public double getDrawcashScore1() {
		return drawcashScore1;
	}

	public void setDrawcashScore1(double drawcashScore1) {
		this.drawcashScore1 = drawcashScore1;
	}
}
