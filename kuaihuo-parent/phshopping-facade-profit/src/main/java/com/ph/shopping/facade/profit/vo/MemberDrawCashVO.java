package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @ClassName: MemberDrawCashVO
 * @Description: 会员提现VO
 * @author 王强
 * @date 2017年6月14日 上午9:49:29
 */
public class MemberDrawCashVO implements Serializable {
	private static final long serialVersionUID = -4562207755921117065L;
	private String orderNo;//订单号
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;//提交时间
	private String createTime1;
	
	private String telPhone;//会员账号
	private String isMarketing;//会员类型
	private String memberName;//会员姓名
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date operatorCheckTime;//运营审核时间
	private String operatorCheckTime1;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date treasurerCheckTime;//财务审核时间
	private String treasurerCheckTime1;
	
	private Long score1;
	private String score;//金额
	private Long handingCharge1;
	private String handingCharge;//手续费
	private String auditState;//审核状态
	private String status;//提现状态
	private String balanceDifference;// 平衡差
	private Long drawCashId;
	private Long userId;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getIsMarketing() {
		return isMarketing;
	}

	public void setIsMarketing(String isMarketing) {
		this.isMarketing = isMarketing;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Long getScore1() {
		return score1;
	}

	public void setScore1(Long score1) {
		this.score1 = score1;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Long getHandingCharge1() {
		return handingCharge1;
	}

	public void setHandingCharge1(Long handingCharge1) {
		this.handingCharge1 = handingCharge1;
	}

	public String getHandingCharge() {
		return handingCharge;
	}

	public void setHandingCharge(String handingCharge) {
		this.handingCharge = handingCharge;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBalanceDifference() {
		return balanceDifference;
	}

	public void setBalanceDifference(String balanceDifference) {
		this.balanceDifference = balanceDifference;
	}

	public Long getDrawCashId() {
		return drawCashId;
	}

	public void setDrawCashId(Long drawCashId) {
		this.drawCashId = drawCashId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getOperatorCheckTime() {
		return operatorCheckTime;
	}

	public void setOperatorCheckTime(Date operatorCheckTime) {
		this.operatorCheckTime = operatorCheckTime;
	}

	public Date getTreasurerCheckTime() {
		return treasurerCheckTime;
	}

	public void setTreasurerCheckTime(Date treasurerCheckTime) {
		this.treasurerCheckTime = treasurerCheckTime;
	}

	public String getCreateTime1() {
		return createTime1;
	}

	public void setCreateTime1(String createTime1) {
		this.createTime1 = createTime1;
	}

	public String getOperatorCheckTime1() {
		return operatorCheckTime1;
	}

	public void setOperatorCheckTime1(String operatorCheckTime1) {
		this.operatorCheckTime1 = operatorCheckTime1;
	}

	public String getTreasurerCheckTime1() {
		return treasurerCheckTime1;
	}

	public void setTreasurerCheckTime1(String treasurerCheckTime1) {
		this.treasurerCheckTime1 = treasurerCheckTime1;
	}
}
