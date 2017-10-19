package com.ph.shopping.facade.profit.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 会员余额实体
* @ClassName: MemberScoreDetailed
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年7月5日 下午3:49:45
 */
public class MemberScoreDetailed  implements Serializable{

	private static final long serialVersionUID = 6098284288527605657L;

	private Long id;//主键id
	
	private Long memberId;//会员id
	
	private String telPhone;//会员账号
	
	private Byte isMarketing;//会员类型     是否是推广师;1=是；2=否
	
	private String memberName;//会员名字
	
	private Long balanceDifference;//平衡差
		
	private Long standbyScore;//待用积分
	
	private Long enableScore;//可用积分
	
	private Long drawcashScore;//已经体现积分  
	
	private Long payTotalScore;//已经支付积分
	
	private Long profitScore;//分润积分
	
	private Long returnScore;//待用积分转可用积分
	
	private Long returnScoreOnline;//线上订单退款的积分

	private Date createTime;//创建时间
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Long getBalanceDifference() {
		return balanceDifference;
	}

	public void setBalanceDifference(Long balanceDifference) {
		this.balanceDifference = balanceDifference;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
