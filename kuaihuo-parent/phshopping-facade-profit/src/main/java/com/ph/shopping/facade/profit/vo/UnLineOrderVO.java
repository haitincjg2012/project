package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
* @ClassName: UnLineOrderVO
* @Description: 线下订单VO(用于取线下订单过来分润)
* @author Mr.Dong
* @date 2017年5月23日 上午9:57:42
 */
public class UnLineOrderVO  implements Serializable{
	
	private static final long serialVersionUID = -4256505413581276111L;
	
	private Long id;//线下订单id
	private Long memberId;//用户id
	private Long memberPromoterId;//用户推荐人id
	private String memberPromoterName;//用户推荐人name
	private String  orderNo;//订单号
	private Date orderTime;//订单时间
	private Long payMoney;//订单金额
	private Long merchantId;//商户id
	private String merchantName;//商户名字
	private Double businessProfitRatio;//商户分润比率
	private Long merchantPositionId;//商户的区域id
	
	private String cityAgentName;//市级代理名字
	private String countyAgentName;//县级代理名字
	private String townAgentName;//社区代理名字	
	private Long cityAgentId;//市级代理id
	private Long countyAgentId;//县级代理id
	private Long townAgentId;//社区代理id
	
	private String cityPromoterName;//市级代理推荐人名字
	private String countyPromoterName;//县级代理推荐人名字
	private String townPromoterName;//社区代理推荐人名字	
	private String merchantPromoterName;//商户推荐人名字
	private Long cityPromoterId;//市级代理推荐人id
	private Long countyPromoterId;//县级代理推荐人id
	private Long townPromoterId;//社区代理推荐人id
	private Long merchantPromoterId;//商户推荐人id
	private String storeManagerName;//店面经理名字
	private Long storeManagerId;//店面经理id
	private Byte isMarketing;//推荐人是否是推广师 1 是推广师； 2会员
	private Byte isProfit;//是否分润 0=未分润；1=已分润；2=会员分润

	public String getStoreManagerName() {
		return storeManagerName;
	}

	public void setStoreManagerName(String storeManagerName) {
		this.storeManagerName = storeManagerName;
	}

	public Long getStoreManagerId() {
		return storeManagerId;
	}

	public void setStoreManagerId(Long storeManagerId) {
		this.storeManagerId = storeManagerId;
	}

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
	public Long getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Long payMoney) {
		this.payMoney = payMoney;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public Long getMerchantPositionId() {
		return merchantPositionId;
	}
	public void setMerchantPositionId(Long merchantPositionId) {
		this.merchantPositionId = merchantPositionId;
	}
	public String getCityAgentName() {
		return cityAgentName;
	}
	public void setCityAgentName(String cityAgentName) {
		this.cityAgentName = cityAgentName;
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
	public Double getBusinessProfitRatio() {
		return businessProfitRatio;
	}
	public void setBusinessProfitRatio(Double businessProfitRatio) {
		this.businessProfitRatio = businessProfitRatio;
	}
	public String getCountyAgentName() {
		return countyAgentName;
	}
	public void setCountyAgentName(String countyAgentName) {
		this.countyAgentName = countyAgentName;
	}
	public Long getCountyAgentId() {
		return countyAgentId;
	}
	public void setCountyAgentId(Long countyAgentId) {
		this.countyAgentId = countyAgentId;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Byte getIsMarketing() {
		return isMarketing;
	}
	public void setIsMarketing(Byte isMarketing) {
		this.isMarketing = isMarketing;
	}
	public Byte getIsProfit() {
		return isProfit;
	}
	public void setIsProfit(Byte isProfit) {
		this.isProfit = isProfit;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getMemberPromoterId() {
		return memberPromoterId;
	}
	public void setMemberPromoterId(Long memberPromoterId) {
		this.memberPromoterId = memberPromoterId;
	}
	public String getMemberPromoterName() {
		return memberPromoterName;
	}
	public void setMemberPromoterName(String memberPromoterName) {
		this.memberPromoterName = memberPromoterName;
	}
	
}
