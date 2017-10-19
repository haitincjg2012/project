package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 线下订单结算VO(前台展示用)
* @ClassName: UnLineSettleVO
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月7日 上午11:42:52
 */
public class UnLineSettleVO implements Serializable {

	private static final long serialVersionUID = -4096519515931001732L;
	
	private Long  id;//线下订单主键
	
	private String orderNo;//订单号
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date orderTime;//订单支付时间
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date settleTime;//结算时间
	
	private Long orderMoney;//订单金额
	
	private Long  settleMoney;//结算金额
	
	private String memberTel;//会员账号
	
	private String memberName;//会员名称
	
	private String merchantTel;//商户账号
	
	private String merchantCompanyName;//商户企业名字
	
	private String merchantName;//商户店铺名字
	
	private byte payType;//支付类型
	
	private byte isSettle;//是否结算
	
	private String orderMoney1;//订单金额
	
	private String settleMoney1;//结算金额

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getSettleTime() {
		return settleTime;
	}

	public void setSettleTime(Date settleTime) {
		this.settleTime = settleTime;
	}

	public Long getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Long orderMoney) {
		this.orderMoney = orderMoney;
	}

	public Long getSettleMoney() {
		return settleMoney;
	}

	public void setSettleMoney(Long settleMoney) {
		this.settleMoney = settleMoney;
	}

	public String getMemberTel() {
		return memberTel;
	}

	public void setMemberTel(String memberTel) {
		this.memberTel = memberTel;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMerchantTel() {
		return merchantTel;
	}

	public void setMerchantTel(String merchantTel) {
		this.merchantTel = merchantTel;
	}

	public String getMerchantCompanyName() {
		return merchantCompanyName;
	}

	public void setMerchantCompanyName(String merchantCompanyName) {
		this.merchantCompanyName = merchantCompanyName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public byte getPayType() {
		return payType;
	}

	public void setPayType(byte payType) {
		this.payType = payType;
	}

	public byte getIsSettle() {
		return isSettle;
	}

	public void setIsSettle(byte isSettle) {
		this.isSettle = isSettle;
	}

	public String getOrderMoney1() {
		return orderMoney1;
	}

	public void setOrderMoney1(String orderMoney1) {
		this.orderMoney1 = orderMoney1;
	}

	public String getSettleMoney1() {
		return settleMoney1;
	}

	public void setSettleMoney1(String settleMoney1) {
		this.settleMoney1 = settleMoney1;
	}
}
