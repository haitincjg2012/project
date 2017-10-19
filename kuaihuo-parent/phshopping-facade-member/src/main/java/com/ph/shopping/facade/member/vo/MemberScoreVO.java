package com.ph.shopping.facade.member.vo;

import java.io.Serializable;
/**
 * 
 * @ClassName:  MemberScoreVO   
 * @Description:会员积分  
 * @author: 李杰
 * @date:   2017年4月25日 上午11:23:44     
 * @Copyright: 2017
 */
public class MemberScoreVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -6921891801394327384L;

	private double enablescore;
	
	private double standbyscore;
	
	private double drawcashScore;

	public double getEnablescore() {
		return enablescore;
	}

	public void setEnablescore(double enablescore) {
		this.enablescore = enablescore;
	}

	public double getStandbyscore() {
		return standbyscore;
	}

	public void setStandbyscore(double standbyscore) {
		this.standbyscore = standbyscore;
	}

	public double getDrawcashScore() {
		return drawcashScore;
	}

	public void setDrawcashScore(double drawcashScore) {
		this.drawcashScore = drawcashScore;
	}

	
}
