package com.ph.shopping.facade.pay.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

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
 */
@Table(name = "ph_member_drawcash_record")
public class MemberDrawcashRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3181029865564480270L;
	@Id
	@Column(name="id")
	private Long id;
	
	@Column(name="memberId")
    private Long memberId;//会员Id
	
	@Column(name="orderNum")
	private  String orderNum;//订单号

    @Column(name="amount")
    private  Long amount;//提现金额

    @Column(name="handingCharge")
    private  Long handingCharge;//提现手续费

    @Column(name="status")
    private Integer status;//提现状态
    
    @Column(name="batchNo")
    private String batchNo;//提现批次号
    
    @Column(name="accountName")
    private String accountName;//开户名
    
    @Column(name="bankCardNo")
    private String bankCardNo;//银行卡号
    
    @Column(name="requestIp")
    private  String requestIp;//提现Ip地址
    
    @Column(name="tradeState")
    private String tradeState;//交易状态 ：“0000”：交易成功；“00A4”：交易处理中（中间状态）

    @Column(name="creater")
    private  Long creater;//创建人
    
    @Column(name="operater")
    private  Long operater;//更新人
    
    @Column(name="createTime")
    private Date createTime;//创建时间
    
    @Column(name="updateTime")
    private Date updateTime;//更新时间

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getHandingCharge() {
		return handingCharge;
	}

	public void setHandingCharge(Long handingCharge) {
		this.handingCharge = handingCharge;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCreater() {
		return creater;
	}

	public void setCreater(Long creater) {
		this.creater = creater;
	}

	public Long getOperater() {
		return operater;
	}

	public void setOperater(Long operater) {
		this.operater = operater;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
}
