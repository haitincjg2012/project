package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @ClassName: OnLineSettleVO
 * @Description: 线上结算VO
 * @author 王强
 * @date 2017年6月6日 上午9:47:19
 */
public class OnLineSettleVO implements Serializable {
	private static final long serialVersionUID = -7153811050956508837L;
	private String orderNo;// 订单号
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;// 订单时间
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date doneTime;// 收货时间
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date settleTime;// 结算时间
	private Long orderMoney1;
	private String orderMoney;// 订单金额
	private Long settlementMoney1;
	private Double settlementMoney;// 总结算价
	private Long logisticsMoney1;
	private Double logisticsMoney;// 总物流费
	private String totalSettlePrice;// 结算金额(总结算价 + 总物流费)
	private Integer payType;// 支付方式
	private String memberTelPhone;// 会员账号
	private String memberName;// 会员名称
	private String supplierTelPhone;// 供应商账号
	private String supplierName;// 供应商企业名称
	private Integer status;// 0待结算，1已结算
	
	private Long userId;//用户id

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getLogisticsMoney1() {
		return logisticsMoney1;
	}

	public void setLogisticsMoney1(Long logisticsMoney1) {
		this.logisticsMoney1 = logisticsMoney1;
	}

	public Double getLogisticsMoney() {
		return logisticsMoney;
	}

	public void setLogisticsMoney(Double logisticsMoney) {
		this.logisticsMoney = logisticsMoney;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getMemberTelPhone() {
		return memberTelPhone;
	}

	public void setMemberTelPhone(String memberTelPhone) {
		this.memberTelPhone = memberTelPhone;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getSupplierTelPhone() {
		return supplierTelPhone;
	}

	public void setSupplierTelPhone(String supplierTelPhone) {
		this.supplierTelPhone = supplierTelPhone;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getSettlementMoney1() {
		return settlementMoney1;
	}

	public void setSettlementMoney1(Long settlementMoney1) {
		this.settlementMoney1 = settlementMoney1;
	}

	public Double getSettlementMoney() {
		return settlementMoney;
	}

	public void setSettlementMoney(Double settlementMoney) {
		this.settlementMoney = settlementMoney;
	}

	public Long getOrderMoney1() {
		return orderMoney1;
	}

	public void setOrderMoney1(Long orderMoney1) {
		this.orderMoney1 = orderMoney1;
	}

	public Date getSettleTime() {
		return settleTime;
	}

	public void setSettleTime(Date settleTime) {
		this.settleTime = settleTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(Date doneTime) {
		this.doneTime = doneTime;
	}

	public String getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(String orderMoney) {
		this.orderMoney = orderMoney;
	}

	public String getTotalSettlePrice() {
		return totalSettlePrice;
	}

	public void setTotalSettlePrice(String totalSettlePrice) {
		this.totalSettlePrice = totalSettlePrice;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
