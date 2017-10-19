package com.ph.shopping.facade.profit.entity;

import java.io.Serializable;
import java.util.Date;
/**
* @ClassName: UnlineOrderProfit
* @Description: 线下订单分润实体
* @author Mr.Dong
* @date 2017年5月22日 下午5:01:30
 */
public class UnlineOrderProfit implements Serializable {

	private static final long serialVersionUID = -520283518630546513L;

	private Long id;// 表流水

	private String orderNo;// 订单编号

	private Long orderMoney;// 订单金额

	private Long chargeFee;// 管理费

	private Long phChargeFee;// 普惠基金

	private Long ystChargeFee;// 易商通管理费


	private Long khChargeFee;// 快火管理费
	
	private String cityAgentName;//市级代理名字
	
	private String countyAgentName;//县级代理名字
	
	private String townAgentName;//社区代理名字
	
	private String cityPromoterName;//市级代理推荐人名字
	
	private String countyPromoterName;//县级代理推荐人名字
	private String townPromoterName;//社区代理推荐人名字
	
	private String merchantPromoterName;//商户推荐人名字
	
	private String memberPromoterName;//用户推荐人名字
	
	private String sellerName;
	
	private String buyerName;
	
	private Date orderTime;//订单完成时间

	private Long cityAgentProfit;// 市级代理分润

	private Long countyAgentProfit;// 县级代理分润

	private Long townAgentProfit;// 社区分润

	private Long cityPromoterProfit;// 市级代理推广分润

	private Long countyPromoterProfit;// 县级代理推广分润

	private Long townPromoterProfit;// 社区代理推广分润

	private Long merchantPromoterProfit;// 推广商户分润

	private Long storeManagerProfit;// 店面经理分润

	private Long storeManagerId;// 店面经理Id

	private String storeManagerName;//店面经理名字

	public Long getStoreManagerProfit() {
		return storeManagerProfit;
	}

	public void setStoreManagerProfit(Long storeManagerProfit) {
		this.storeManagerProfit = storeManagerProfit;
	}

	public Long getStoreManagerId() {
		return storeManagerId;
	}

	public void setStoreManagerId(Long storeManagerId) {
		this.storeManagerId = storeManagerId;
	}

	private Long memberPromoterProfit;// 推广用户分润

	private Long chargeProfitTotal;// 管理费支出合计

	private Long chargeProfitRemain;// 管理费剩余

	private Date createTime;//创建时间
	
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
	
	private Long merchantPromoterId;//推广商户推荐人id
	
	private Long memberPromoterId;//推广商户推荐人id
	
	private String merchantName;//商户名字
	
	private Long merchantId;//商户ID

	private Byte isMarketing;//推荐人是否是推广师 1 是推广师； 2会员

	private Byte isProfit;//是否分润 0=未分润；1=已分润；2=会员分润

	public String getStoreManagerName() {
		return storeManagerName;
	}

	public void setStoreManagerName(String storeManagerName) {
		this.storeManagerName = storeManagerName;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
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

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
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
	
	public Long getKhChargeFee() {
		return khChargeFee;
	}
	
	public void setKhChargeFee(Long khChargeFee) {
		this.khChargeFee = khChargeFee;
	}

	public String getMemberPromoterName() {
		return memberPromoterName;
	}

	public void setMemberPromoterName(String memberPromoterName) {
		this.memberPromoterName = memberPromoterName;
	}

	public Long getMemberPromoterProfit() {
		return memberPromoterProfit;
	}

	public void setMemberPromoterProfit(Long memberPromoterProfit) {
		this.memberPromoterProfit = memberPromoterProfit;
	}

	public Long getMemberPromoterId() {
		return memberPromoterId;
	}

	public void setMemberPromoterId(Long memberPromoterId) {
		this.memberPromoterId = memberPromoterId;
	}
	
	
}