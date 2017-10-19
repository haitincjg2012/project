package com.ph.shopping.facade.spm.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ph.shopping.common.core.base.BaseEntity;

@Table(name = "ph_user_drawcash_record")
public class UserDrawcash extends BaseEntity {
	private static final long serialVersionUID = -6702926895147593172L;

	/** 商户id */
	@Column(name = "userId")
	private Long userId;

	/** 提现金额 */
	private BigDecimal score;

	/** 提现手续费 */
	@Column(name = "handingCharge")
	private BigDecimal handingCharge;

	/** 提现状态:0:提现中，1:提现成功，2:提现失败 */
	private Byte status;

	/** 提现编号 */
	@Column(name = "orderNo")
	private String orderNo;

	/**
	 * 审核状态:0待审核 1运营通过 2运营撤销 3财务通过 4财务撤销
	 * 
	 * 2.0版 -1提现失败，0待审核，1，提现中，2提现成功
	 */
	@Column(name = "auditState")
	private Byte auditState;

	/** 提现ip地址 */
	@Column(name = "drawcashIp")
	private String drawcashIp;

	/** 收款人姓名 */
	private String receiver;

	/** 银行卡号 */
	@Column(name = "bankNo")
	private String bankNo;

	/** 所属银行 */
	@Column(name = "bankName")
	private String bankName;

	/** 提现类型 0 市级代理提现 1县级代理提现 2社区代理提现 3商户提现 4供应商提现 */
	@Column(name = "userType")
	private Byte userType;

	/** 运营审核时间 */
	@Column(name = "operatorCheckTime")
	private Date operatorCheckTime;

	/** 财务审核时间 */
	@Column(name = "treasurerCheckTime")
	private Date treasurerCheckTime;

	/** 提现人账号 */
	@Column(name = "telPhone")
	private String telPhone;

	/** 银行交易状态 */
	@Column(name = "tradeState")
	private String tradeState;

	/** 运营审核人 */
	@Column(name = "operatorCheckId")
	private Long operatorCheckId;

	/** 财务审核人 */
	@Column(name = "treasurerCheckId")
	private Long treasurerCheckId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public BigDecimal getHandingCharge() {
		return handingCharge;
	}

	public void setHandingCharge(BigDecimal handingCharge) {
		this.handingCharge = handingCharge;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	public Byte getAuditState() {
		return auditState;
	}

	public void setAuditState(Byte auditState) {
		this.auditState = auditState;
	}

	public String getDrawcashIp() {
		return drawcashIp;
	}

	public void setDrawcashIp(String drawcashIp) {
		this.drawcashIp = drawcashIp == null ? null : drawcashIp.trim();
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver == null ? null : receiver.trim();
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo == null ? null : bankNo.trim();
	}

	public Byte getUserType() {
		return userType;
	}

	public void setUserType(Byte userType) {
		this.userType = userType;
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

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone == null ? null : telPhone.trim();
	}

	public String getTradeState() {
		return tradeState;
	}

	public void setTradeState(String tradeState) {
		this.tradeState = tradeState == null ? null : tradeState.trim();
	}

	public Long getOperatorCheckId() {
		return operatorCheckId;
	}

	public void setOperatorCheckId(Long operatorCheckId) {
		this.operatorCheckId = operatorCheckId;
	}

	public Long getTreasurerCheckId() {
		return treasurerCheckId;
	}

	public void setTreasurerCheckId(Long treasurerCheckId) {
		this.treasurerCheckId = treasurerCheckId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
}