package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 
* @ClassName: OnLineOrderVO
* @Description: 线上订单表VO(用于取线上订单过来分润)
* @author Mr.Dong
* @date 2017年5月26日 下午5:01:30
 */
public class OnLineOrderVO implements Serializable {
	private static final long serialVersionUID = -8754152564833280894L;
	private Long  id;//主键
	private String orderNo;//订单号
	private Date orderTime;//订单时间(订单完成时间交易完成)
	private Long retailPrice;//零售价(商品金额)
	private Long freight;//物流费
	private Long settlementPrice;//结算价(结算成本)
	private Long memberId;//会员id
	
	private Byte shippingType; //收货方式：0=自提方式；1=送货到家。
	
	
	private String cityAgentName;//市级代理名字
	private String countyAgentName;//县级代理名字
	private String townAgentName;//社区代理名字	
	private String  merchantName;//商户名字
	private Long cityAgentId;//市级代理id
	private Long countyAgentId;//县级代理id
	private Long townAgentId;//社区代理id
	private Long merchantId;//自提时商户的id
	
	
	private String cityPromoterName;//市级代理推荐人名字
	private String countyPromoterName;//县级代理推荐人名字
	private String townPromoterName;//社区代理推荐人名字	
	private String merchantPromoterName;//商户推荐人名字
	
	private Long cityPromoterId;//市级代理推荐人id
	private Long countyPromoterId;//县级代理推荐人id
	private Long townPromoterId;//社区代理推荐人id
	private Long merchantPromoterId;//商户推荐人id
	
	
	
	private Long  shippingProvinceId;//收货地址的省id
	
	private Long  shippingCityId;//收货地址的市id

	private Long  shippingCountyId;//收货地址的区id

	private Long  shippingTownId;//收货地址的镇id
	
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



	public Long getRetailPrice() {
		return retailPrice;
	}



	public void setRetailPrice(Long retailPrice) {
		this.retailPrice = retailPrice;
	}



	public Long getFreight() {
		return freight;
	}



	public void setFreight(Long freight) {
		this.freight = freight;
	}



	public Long getSettlementPrice() {
		return settlementPrice;
	}



	public void setSettlementPrice(Long settlementPrice) {
		this.settlementPrice = settlementPrice;
	}



	public Long getMemberId() {
		return memberId;
	}



	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}



	public String getCityAgentName() {
		return cityAgentName;
	}



	public void setCityAgentName(String cityAgentName) {
		this.cityAgentName = cityAgentName;
	}



	public String getCountyAgentName() {
		return countyAgentName;
	}



	public void setCountyAgentName(String countyAgentName) {
		this.countyAgentName = countyAgentName;
	}



	public String getTownAgentName() {
		return townAgentName;
	}



	public void setTownAgentName(String townAgentName) {
		this.townAgentName = townAgentName;
	}



	public Long getCityAgentId() {
		return cityAgentId;
	}



	public void setCityAgentId(Long cityAgentId) {
		this.cityAgentId = cityAgentId;
	}



	public Long getCountyAgentId() {
		return countyAgentId;
	}



	public void setCountyAgentId(Long countyAgentId) {
		this.countyAgentId = countyAgentId;
	}



	public Long getTownAgentId() {
		return townAgentId;
	}



	public void setTownAgentId(Long townAgentId) {
		this.townAgentId = townAgentId;
	}



	public String getCityPromoterName() {
		return cityPromoterName;
	}



	public void setCityPromoterName(String cityPromoterName) {
		this.cityPromoterName = cityPromoterName;
	}



	public String getCountyPromoterName() {
		return countyPromoterName;
	}



	public void setCountyPromoterName(String countyPromoterName) {
		this.countyPromoterName = countyPromoterName;
	}



	public String getTownPromoterName() {
		return townPromoterName;
	}



	public void setTownPromoterName(String townPromoterName) {
		this.townPromoterName = townPromoterName;
	}



	public String getMerchantPromoterName() {
		return merchantPromoterName;
	}



	public void setMerchantPromoterName(String merchantPromoterName) {
		this.merchantPromoterName = merchantPromoterName;
	}



	public Long getCityPromoterId() {
		return cityPromoterId;
	}



	public void setCityPromoterId(Long cityPromoterId) {
		this.cityPromoterId = cityPromoterId;
	}



	public Long getCountyPromoterId() {
		return countyPromoterId;
	}



	public void setCountyPromoterId(Long countyPromoterId) {
		this.countyPromoterId = countyPromoterId;
	}



	public Long getTownPromoterId() {
		return townPromoterId;
	}



	public void setTownPromoterId(Long townPromoterId) {
		this.townPromoterId = townPromoterId;
	}



	public Long getMerchantPromoterId() {
		return merchantPromoterId;
	}



	public void setMerchantPromoterId(Long merchantPromoterId) {
		this.merchantPromoterId = merchantPromoterId;
	}



	public Byte getShippingType() {
		return shippingType;
	}



	public void setShippingType(Byte shippingType) {
		this.shippingType = shippingType;
	}



	public Long getMerchantId() {
		return merchantId;
	}



	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}



	public String getMerchantName() {
		return merchantName;
	}



	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}



	public Long getShippingProvinceId() {
		return shippingProvinceId;
	}



	public void setShippingProvinceId(Long shippingProvinceId) {
		this.shippingProvinceId = shippingProvinceId;
	}



	public Long getShippingCityId() {
		return shippingCityId;
	}



	public void setShippingCityId(Long shippingCityId) {
		this.shippingCityId = shippingCityId;
	}



	public Long getShippingCountyId() {
		return shippingCountyId;
	}



	public void setShippingCountyId(Long shippingCountyId) {
		this.shippingCountyId = shippingCountyId;
	}



	public Long getShippingTownId() {
		return shippingTownId;
	}



	public void setShippingTownId(Long shippingTownId) {
		this.shippingTownId = shippingTownId;
	}

}
