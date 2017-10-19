/**  
 * @Title:  MyScoreVO.java   
 * @Package com.phshopping.api.controller.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月26日 上午11:41:18   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.phshopping.api.controller.vo;

import java.io.Serializable;

/**   
 * @ClassName:  MyScoreVO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年7月26日 上午11:41:18     
 * @Copyright: 2017
 */
public class MyScoreVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -3335232523833268535L;
	/**
	 * 会员ID
	 */
	private Long memberId;
	/**
	 * 现金余额
	 */
	private String cashScore;
	/**
	 * 可提现积分
	 */
	private String drawnScore;
	/**
	 * 待用积分
	 */
	private String standbyScore;
	/**
	 * 会员推广会员奖励积分
	 */
	private String memberRewardScore;
	/**
	 * 会员推广商户奖励积分
	 */
	private String merchantRewardScore;
	
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getCashScore() {
		return cashScore;
	}
	public void setCashScore(String cashScore) {
		this.cashScore = cashScore;
	}
	public String getDrawnScore() {
		return drawnScore;
	}
	public void setDrawnScore(String drawnScore) {
		this.drawnScore = drawnScore;
	}
	public String getStandbyScore() {
		return standbyScore;
	}
	public void setStandbyScore(String standbyScore) {
		this.standbyScore = standbyScore;
	}
	public String getMemberRewardScore() {
		return memberRewardScore;
	}
	public void setMemberRewardScore(String memberRewardScore) {
		this.memberRewardScore = memberRewardScore;
	}
	public String getMerchantRewardScore() {
		return merchantRewardScore;
	}
	public void setMerchantRewardScore(String merchantRewardScore) {
		this.merchantRewardScore = merchantRewardScore;
	}
}
