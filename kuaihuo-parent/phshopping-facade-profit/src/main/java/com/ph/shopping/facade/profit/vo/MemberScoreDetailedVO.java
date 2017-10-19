package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;

/**
 * 会员余额详细
* @ClassName: MemberScoreDetailedVO
* @Description: TODO(账号结算模块下会员余额、涉及会员的积分相关)
* @author Mr.Dong
* @date 2017年6月12日 上午11:32:32
 */
public class MemberScoreDetailedVO implements Serializable  {

	private static final long serialVersionUID = -5730889573055087632L;

	private Long memberId;//会员id
	
	private String telPhone;//会员账号
	
	private Byte isMarketing;//会员类型     是否是推广师;1=是；2=否
	
	private String memberName;//会员名字
	
	private Byte status;//状态   未冻结 0 已冻结 1  积分冻结状态
	
	private Long balanceDifference;//平衡差
		
	private Long standbyScore;//待用积分
	
	private Long enableScore;//可用积分
	
	private Long drawcashScore;//已经体现积分  
	
	private Long payTotalScore;//已经支付积分
	
	private Long profitScore;//分润积分
	
	private Long returnScore;//待用积分转可用积分
	
	private Long returnScoreOnline;//线上订单退款的积分
	
	
	
	private Double balanceDifference1;//平衡差
	
	private Double standbyScore1;//待用积分
	
	private Double enableScore1;//可用积分
	
	private Double drawcashScore1;//已经体现积分  
	
	private Double payTotalScore1;//已经支付积分
	
	private Double profitScore1;//分润积分
	
	private Double returnScore1;//待用积分转可用积分
	
	private Double returnScoreOnline1;//线上订单退款的积分

	



	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public Byte getIsMarketing() {
		return isMarketing;
	}

	public void setIsMarketing(Byte isMarketing) {
		this.isMarketing = isMarketing;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	

	public Long getStandbyScore() {
		return standbyScore;
	}

	public void setStandbyScore(Long standbyScore) {
		this.standbyScore = standbyScore;
	}

	public Long getEnableScore() {
		return enableScore;
	}

	public void setEnableScore(Long enableScore) {
		this.enableScore = enableScore;
	}

	public Long getDrawcashScore() {
		return drawcashScore;
	}

	public void setDrawcashScore(Long drawcashScore) {
		this.drawcashScore = drawcashScore;
	}

	public Long getPayTotalScore() {
		return payTotalScore;
	}

	public void setPayTotalScore(Long payTotalScore) {
		this.payTotalScore = payTotalScore;
	}

	public Long getProfitScore() {
		return profitScore;
	}

	public void setProfitScore(Long profitScore) {
		this.profitScore = profitScore;
	}


	

	public Long getBalanceDifference() {
		return balanceDifference;
	}

	public void setBalanceDifference(Long balanceDifference) {
		this.balanceDifference = balanceDifference;
	}

	public Double getBalanceDifference1() {
		return balanceDifference1;
	}

	public void setBalanceDifference1(Double balanceDifference1) {
		this.balanceDifference1 = balanceDifference1;
	}

	public Double getStandbyScore1() {
		return standbyScore1;
	}

	public void setStandbyScore1(Double standbyScore1) {
		this.standbyScore1 = standbyScore1;
	}

	public Double getEnableScore1() {
		return enableScore1;
	}

	public void setEnableScore1(Double enableScore1) {
		this.enableScore1 = enableScore1;
	}

	public Double getDrawcashScore1() {
		return drawcashScore1;
	}

	public void setDrawcashScore1(Double drawcashScore1) {
		this.drawcashScore1 = drawcashScore1;
	}

	public Double getPayTotalScore1() {
		return payTotalScore1;
	}

	public void setPayTotalScore1(Double payTotalScore1) {
		this.payTotalScore1 = payTotalScore1;
	}

	public Double getProfitScore1() {
		return profitScore1;
	}

	public void setProfitScore1(Double profitScore1) {
		this.profitScore1 = profitScore1;
	}

	public Long getReturnScore() {
		return returnScore;
	}

	public void setReturnScore(Long returnScore) {
		this.returnScore = returnScore;
	}

	public Long getReturnScoreOnline() {
		return returnScoreOnline;
	}

	public void setReturnScoreOnline(Long returnScoreOnline) {
		this.returnScoreOnline = returnScoreOnline;
	}

	public Double getReturnScore1() {
		return returnScore1;
	}

	public void setReturnScore1(Double returnScore1) {
		this.returnScore1 = returnScore1;
	}

	public Double getReturnScoreOnline1() {
		return returnScoreOnline1;
	}

	public void setReturnScoreOnline1(Double returnScoreOnline1) {
		this.returnScoreOnline1 = returnScoreOnline1;
	}

}
