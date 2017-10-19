package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
import java.util.Date;

/**
* @ClassName: UnLineOrderProfitVO
* @Description: 线下订单分润VO
* @author Mr.Dong
* @date 2017年4月25日 下午5:01:30
 */
public class UnLineOrderProfitVO implements Serializable {
	
	private static final long serialVersionUID = 2203408422822001075L;

	private Long id;// 表流水

	private String orderNo;// 订单编号

	private String cityAgentName;//市级代理名字
	
	private String countyAgentName;//县级代理名字
	
	private String townAgentName;//社区代理名字
	
	private String cityPromoterName;//市级代理推荐人名字
	
	private String countyPromoterName;//县级代理推荐人名字
	
	private String townPromoterName;//社区代理推荐人名字
	
	private String merchantPromoterName;//商户推荐人名字
	
	private String sellerName;//
	
	private String buyerName;

	private String createTime;//创建时间(分润时间)
	
	private String buyerTel;//买家电话号码
	
	private Long  auditorId;//审核人
	
	private Date  auditTime;//审核时间
	
	private Byte auditStatus;//审核状态
	
	private String reason;//审核原因
	
	private Long sellerId;//卖方id
	
	private Long buyerId;//买方id
	
	private Long cityAgentId;//市级代理id
	
	private Long cityPromoterId;//市级代理推荐人id
	
	private Long countyAgentId;//县级代理id
	
	private Long countyPromoterId;//县级代理推荐人id
	
	private Long townAgentId;//社区代理id
	
	private Long townPromoterId;//社区代理推荐人id
	
	private Long merchantPromoterId;//商户推荐人id
	
	private String orderTime;//订单完成时间
	
	private Long orderMoney;// 订单金额

	private Long chargeFee;// 管理费

	private Long phChargeFee;// 普惠管理费

	private Long ystChargeFee;// 易商通管理费

	private Long khChargeFee;// 快火收入

	public Long getKhChargeFee() {
		return khChargeFee;
	}

	public void setKhChargeFee(Long khChargeFee) {
		this.khChargeFee = khChargeFee;
	}

	private Long cityAgentProfit;// 市级代理分润

	private Long countyAgentProfit;// 县级代理分润

	private Long townAgentProfit;// 社区分润

	private Long cityPromoterProfit;// 推广师推广市级代理分润

	private Long countyPromoterProfit;// 推广师推广县级代理分润

	private Long townPromoterProfit;// 推广师推广社区代理分润

	private Long merchantPromoterProfit;// 推广师推广商户分润

	private Long chargeProfitTotal;// 管理费支出合计

	private Long chargeProfitRemain;// 管理费剩余

	private Long memberPromoterProfit;// 会员推荐的分润

	public Long getMemberPromoterProfit() {
		return memberPromoterProfit;
	}

	public void setMemberPromoterProfit(Long memberPromoterProfit) {
		this.memberPromoterProfit = memberPromoterProfit;
	}

	public Double getMemberPromoterProfit1() {
		return memberPromoterProfit1;
	}

	public void setMemberPromoterProfit1(Double memberPromoterProfit1) {
		this.memberPromoterProfit1 = memberPromoterProfit1;
	}

	private Double orderMoney1;// 订单金额

	private Double chargeFee1;// 管理费

	private Double phChargeFee1;// 普惠管理费

	private Double ystChargeFee1;// 易商通管理费

	private Double khChargeFee1;// 快火管理费

	public Double getKhChargeFee1() {
		return khChargeFee1;
	}

	public void setKhChargeFee1(Double khChargeFee1) {
		this.khChargeFee1 = khChargeFee1;
	}

	private Double cityAgentProfit1;// 市级代理分润

	private Double countyAgentProfit1;// 县级代理分润

	private Double townAgentProfit1;// 社区分润

	private Double cityPromoterProfit1;// 推广师推广市级代理分润

	private Double countyPromoterProfit1;// 推广师推广县级代理分润

	private Double townPromoterProfit1;// 推广师推广社区代理分润

	private Double merchantPromoterProfit1;// 推广师推广商户分润

	private Double memberPromoterProfit1; //推荐会员的分润


	public Long getStoreManagerProfit() {
		return storeManagerProfit;
	}

	public void setStoreManagerProfit(Long storeManagerProfit) {
		this.storeManagerProfit = storeManagerProfit;
	}

	private Long   storeManagerProfit; //店面经理的分润

	private Double storeManagerProfit1; //店面经理的分润

	public Double getStoreManagerProfit1() {
		return storeManagerProfit1;
	}

	public void setStoreManagerProfit1(Double storeManagerProfit1) {
		this.storeManagerProfit1 = storeManagerProfit1;
	}

	private Double chargeProfitTotal1;// 管理费支出合计

	private Double chargeProfitRemain1;// 管理费剩余

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

	

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getBuyerTel() {
		return buyerTel;
	}

	public void setBuyerTel(String buyerTel) {
		this.buyerTel = buyerTel;
	}

	public Long getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Long auditorId) {
		this.auditorId = auditorId;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Byte getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Byte auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Long getCityAgentId() {
		return cityAgentId;
	}

	public void setCityAgentId(Long cityAgentId) {
		this.cityAgentId = cityAgentId;
	}

	public Long getCityPromoterId() {
		return cityPromoterId;
	}

	public void setCityPromoterId(Long cityPromoterId) {
		this.cityPromoterId = cityPromoterId;
	}

	public Long getCountyAgentId() {
		return countyAgentId;
	}

	public void setCountyAgentId(Long countyAgentId) {
		this.countyAgentId = countyAgentId;
	}

	public Long getCountyPromoterId() {
		return countyPromoterId;
	}

	public void setCountyPromoterId(Long countyPromoterId) {
		this.countyPromoterId = countyPromoterId;
	}

	public Long getTownAgentId() {
		return townAgentId;
	}

	public void setTownAgentId(Long townAgentId) {
		this.townAgentId = townAgentId;
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

	public Long getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Long orderMoney) {
		this.orderMoney = orderMoney;
	}

	public Long getChargeFee() {
		return chargeFee;
	}

	public void setChargeFee(Long chargeFee) {
		this.chargeFee = chargeFee;
	}

	public Long getPhChargeFee() {
		return phChargeFee;
	}

	public void setPhChargeFee(Long phChargeFee) {
		this.phChargeFee = phChargeFee;
	}

	public Long getYstChargeFee() {
		return ystChargeFee;
	}

	public void setYstChargeFee(Long ystChargeFee) {
		this.ystChargeFee = ystChargeFee;
	}

	public Long getCityAgentProfit() {
		return cityAgentProfit;
	}

	public void setCityAgentProfit(Long cityAgentProfit) {
		this.cityAgentProfit = cityAgentProfit;
	}

	public Long getCountyAgentProfit() {
		return countyAgentProfit;
	}

	public void setCountyAgentProfit(Long countyAgentProfit) {
		this.countyAgentProfit = countyAgentProfit;
	}

	public Long getTownAgentProfit() {
		return townAgentProfit;
	}

	public void setTownAgentProfit(Long townAgentProfit) {
		this.townAgentProfit = townAgentProfit;
	}

	public Long getCityPromoterProfit() {
		return cityPromoterProfit;
	}

	public void setCityPromoterProfit(Long cityPromoterProfit) {
		this.cityPromoterProfit = cityPromoterProfit;
	}

	public Long getCountyPromoterProfit() {
		return countyPromoterProfit;
	}

	public void setCountyPromoterProfit(Long countyPromoterProfit) {
		this.countyPromoterProfit = countyPromoterProfit;
	}

	public Long getTownPromoterProfit() {
		return townPromoterProfit;
	}

	public void setTownPromoterProfit(Long townPromoterProfit) {
		this.townPromoterProfit = townPromoterProfit;
	}

	public Long getMerchantPromoterProfit() {
		return merchantPromoterProfit;
	}

	public void setMerchantPromoterProfit(Long merchantPromoterProfit) {
		this.merchantPromoterProfit = merchantPromoterProfit;
	}

	public Long getChargeProfitTotal() {
		return chargeProfitTotal;
	}

	public void setChargeProfitTotal(Long chargeProfitTotal) {
		this.chargeProfitTotal = chargeProfitTotal;
	}

	public Long getChargeProfitRemain() {
		return chargeProfitRemain;
	}

	public void setChargeProfitRemain(Long chargeProfitRemain) {
		this.chargeProfitRemain = chargeProfitRemain;
	}

	public Double getOrderMoney1() {
		return orderMoney1;
	}

	public void setOrderMoney1(Double orderMoney1) {
		this.orderMoney1 = orderMoney1;
	}

	public Double getChargeFee1() {
		return chargeFee1;
	}

	public void setChargeFee1(Double chargeFee1) {
		this.chargeFee1 = chargeFee1;
	}

	public Double getPhChargeFee1() {
		return phChargeFee1;
	}

	public void setPhChargeFee1(Double phChargeFee1) {
		this.phChargeFee1 = phChargeFee1;
	}

	public Double getYstChargeFee1() {
		return ystChargeFee1;
	}

	public void setYstChargeFee1(Double ystChargeFee1) {
		this.ystChargeFee1 = ystChargeFee1;
	}

	public Double getCityAgentProfit1() {
		return cityAgentProfit1;
	}

	public void setCityAgentProfit1(Double cityAgentProfit1) {
		this.cityAgentProfit1 = cityAgentProfit1;
	}

	public Double getCountyAgentProfit1() {
		return countyAgentProfit1;
	}

	public void setCountyAgentProfit1(Double countyAgentProfit1) {
		this.countyAgentProfit1 = countyAgentProfit1;
	}

	public Double getTownAgentProfit1() {
		return townAgentProfit1;
	}

	public void setTownAgentProfit1(Double townAgentProfit1) {
		this.townAgentProfit1 = townAgentProfit1;
	}

	public Double getCityPromoterProfit1() {
		return cityPromoterProfit1;
	}

	public void setCityPromoterProfit1(Double cityPromoterProfit1) {
		this.cityPromoterProfit1 = cityPromoterProfit1;
	}

	public Double getCountyPromoterProfit1() {
		return countyPromoterProfit1;
	}

	public void setCountyPromoterProfit1(Double countyPromoterProfit1) {
		this.countyPromoterProfit1 = countyPromoterProfit1;
	}

	public Double getTownPromoterProfit1() {
		return townPromoterProfit1;
	}

	public void setTownPromoterProfit1(Double townPromoterProfit1) {
		this.townPromoterProfit1 = townPromoterProfit1;
	}

	public Double getMerchantPromoterProfit1() {
		return merchantPromoterProfit1;
	}

	public void setMerchantPromoterProfit1(Double merchantPromoterProfit1) {
		this.merchantPromoterProfit1 = merchantPromoterProfit1;
	}

	public Double getChargeProfitTotal1() {
		return chargeProfitTotal1;
	}

	public void setChargeProfitTotal1(Double chargeProfitTotal1) {
		this.chargeProfitTotal1 = chargeProfitTotal1;
	}

	public Double getChargeProfitRemain1() {
		return chargeProfitRemain1;
	}

	public void setChargeProfitRemain1(Double chargeProfitRemain1) {
		this.chargeProfitRemain1 = chargeProfitRemain1;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	
	
}
