package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 
* @ClassName: MerchantVO
* @Description: 商户 VO
* @author Mr.Dong
* @date 2017年4月25日 下午5:01:30
 */
public class MerchantVO implements Serializable {
	private static final long serialVersionUID = 4751538212542020310L;
	private long orderMoney;
	private String orderNo;
	private String sellerName;
	private String buyerName;
	private String merchantRName;
	private String buyerTel;
	private long provinceId;
	private long cityId;
	private long countyId;
	private long townId;
	private double businessProfitRatio;
	
	private long promoterId;
	private long merchantId;
	private long memberId;
	
	private Date createTime;
	
	public long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}
	public long getPromoterId() {
		return promoterId;
	}
	public void setPromoterId(long promoterId) {
		this.promoterId = promoterId;
	}
	public double getBusinessProfitRatio() {
		return businessProfitRatio;
	}
	public void setBusinessProfitRatio(double businessProfitRatio) {
		this.businessProfitRatio = businessProfitRatio;
	}
	public long getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(long orderMoney) {
		this.orderMoney = orderMoney;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getMerchantRName() {
		return merchantRName;
	}
	public void setMerchantRName(String merchantRName) {
		this.merchantRName = merchantRName;
	}
	public String getBuyerTel() {
		return buyerTel;
	}
	public void setBuyerTel(String buyerTel) {
		this.buyerTel = buyerTel;
	}
	public long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}
	public long getCityId() {
		return cityId;
	}
	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	public long getCountyId() {
		return countyId;
	}
	public void setCountyId(long countyId) {
		this.countyId = countyId;
	}
	public long getTownId() {
		return townId;
	}
	public void setTownId(long townId) {
		this.townId = townId;
	}
	public long getMemberId() {
		return memberId;
	}
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
