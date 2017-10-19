package com.ph.shopping.facade.member.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ph.shopping.common.core.base.BaseEntity;

/**
 * 
 * @项目：phshopping-facade-member
 *
 * @描述：会员提现记录实体
 *
 * @作者：Mr.Chang
 *
 * @创建时间：2017年4月1日
 *
 * @Copyright @2017 by Mr.Chang
 *
 * mdy 2017-06-14 zhengpeng
 */
@Table(name = "ph_member_drawcash_record")
public class MemberDrawcashRecord extends BaseEntity{

	private static final long serialVersionUID = -557164592089388981L;

	@Column(name="memberId")
	private Long memberId;//会员Id

	@Column(name="orderNo")
	private  String orderNo;//订单号

	@Column(name="score")
	private BigDecimal score;//提现金额

	@Column(name="handingCharge")
	private  Long handingCharge;//提现手续费

	@Column(name="status")
	private Byte status;//提现状态

	@Column(name="memberName")
	private String memberName;//提现人名称

	@Column(name="batchNo")
	private String batchNo;//提现批次号

	@Column(name="bankCardNo")
	private String bankCardNo;//银行卡号

	@Column(name="requestIp")
	private  String requestIp;//提现Ip地址

	@Column(name="tradeState")
	private String tradeState;//交易状态 ：“0000”：交易成功；“00A4”：交易处理中（中间状态）

	@Column(name="operatorCheckTime")
	private Date operatorCheckTime;//运营审核时间

	@Column(name="treasurerCheckTime")
	private Date treasurerCheckTime;//财务审核时间

	@Column(name="auditState")
	private Byte auditState; //审核状态

	@Column(name="transCode")
	private Integer transCode;


	public Integer getTransCode() {
		return transCode;
	}

	public void setTransCode(Integer transCode) {
		this.transCode = transCode;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public Long getHandingCharge() {
		return handingCharge;
	}

	public void setHandingCharge(Long handingCharge) {
		this.handingCharge = handingCharge;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getTradeState() {
		return tradeState;
	}

	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
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

	public Byte getAuditState() {
		return auditState;
	}

	public void setAuditState(Byte auditState) {
		this.auditState = auditState;
	}

}
