package com.ph.shopping.facade.profit.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;
/**
 * 
 * phshopping-facade-profit
 *
 * @description：线上订单分润实体
 *
 * @author：Mr.Dong
 *
 * @createTime：2017年5月9日
 *
 * @Copyright @2017 by Mr.Dong
 */
@Table(name="ph_profit_online_order")
public class OnlineOrderProfit implements Serializable{
	private static final long serialVersionUID = 7306540527933513448L;

	private Long id;// 表流水

	private String orderNo;// 订单编号
	
	
	private Long  shippingProvinceId;//收货地址的省id
	
	private Long  shippingCityId;//收货地址的市id

	private Long  shippingCountyId;//收货地址的区id

	private Long  shippingTownId;//收货地址的镇id
	
	
	private Long productMoney;// 商品金额  (霞哥)
	
	private Long settlementPrice;// 结算价格
	
	private Long freight;//物流费用
	
	@Column(name="chargeFee")
	private Long chargeFee;// 管理费

	private Long phChargeFee;// 普惠管理费

	private Long ystChargeFee;// 易商通管理费
	
	
	
	private Long cityAgentProfit;// 市级代理分润

	private Long countyAgentProfit;// 县级代理分润

	private Long townAgentProfit;// 社区代理分润
	
	
	
	private Long cityPromoterProfit;// 推广师推广市级代理分润

	private Long countyPromoterProfit;// 推广师推广县级代理分润

	private Long townPromoterProfit;// 推广师推广社区代理分润
	
	private Long merchantPromoterProfit;// 商户推荐人分润
	
	private Long chargeProfitTotal;// 管理费支出合计

	private Long chargeProfitRemain;// 管理费剩余
	
	
	
	
	private Long merchantChainProfit;// 商户供应链分润(会员自提的才分)
	
	private Long chainProfit;//供应链利润

    private Long phIncome;//普惠供应链收入

    private Long ystIncome;//易商通供应链收入
    
    private Long cityAgentChainProfit;//市代供应链分润

    private Long countyAgentChainProfit;//县代供应链分润
    
    private Long townAgentChainProfit;//区代理供应链分润金额
          
    private Long chainTotal;//供应链支出合计

    private Long chainRemain;//供应链剩余
    
    
    
    private Long ystRemain;//易商通总余
    
    private Long phRemain;//普惠总余
    
	private Date  createTime;//创建时间(分润时间)
	
	private Integer auditStatus;//审核状态
	
	private Long auditorId;//审核人id
	
	private Date auditTime;//审核时间
	
	private String reason;//审核原因
	
	private Date orderTime;//订单时间
        
	//代理相关----------------------------------------------------------------------
	private String cityAgentName;//市代名称
	private String countyAgentName;//县代名称
	private String townAgentName;//社区代名称
	private String merchantName;//商户名称
	
	private Long cityAgentId;//市级代理id
	private Long countyAgentId;//县级代理id
	private Long townAgentId;//区级代理id
	private Long merchantId;//商户id
	
	//推荐人相关----------------------------------------------------------------------
	private String cityPromoterName;//市代推荐人名称
	private String countyPromoterName;//县代推荐人名称
	private String townPromoterName;//社区代推荐人名称 
	private String merchantPromoterName;//商户推荐人名称
	
	private Long cityPromoterId;//市代推荐人id
	private Long countyPromoterId;//县代推荐人id
	private Long townPromoterId;//社区代推荐人id
	private Long merchantPromoterId;//商户推荐人id
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
	public Long getProductMoney() {
		return productMoney;
	}
	public void setProductMoney(Long productMoney) {
		this.productMoney = productMoney;
	}
	public Long getSettlementPrice() {
		return settlementPrice;
	}
	public void setSettlementPrice(Long settlementPrice) {
		this.settlementPrice = settlementPrice;
	}
	public Long getFreight() {
		return freight;
	}
	public void setFreight(Long freight) {
		this.freight = freight;
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
	public Long getMerchantChainProfit() {
		return merchantChainProfit;
	}
	public void setMerchantChainProfit(Long merchantChainProfit) {
		this.merchantChainProfit = merchantChainProfit;
	}
	public Long getChainProfit() {
		return chainProfit;
	}
	public void setChainProfit(Long chainProfit) {
		this.chainProfit = chainProfit;
	}
	public Long getPhIncome() {
		return phIncome;
	}
	public void setPhIncome(Long phIncome) {
		this.phIncome = phIncome;
	}
	public Long getYstIncome() {
		return ystIncome;
	}
	public void setYstIncome(Long ystIncome) {
		this.ystIncome = ystIncome;
	}
	public Long getCityAgentChainProfit() {
		return cityAgentChainProfit;
	}
	public void setCityAgentChainProfit(Long cityAgentChainProfit) {
		this.cityAgentChainProfit = cityAgentChainProfit;
	}
	public Long getCountyAgentChainProfit() {
		return countyAgentChainProfit;
	}
	public void setCountyAgentChainProfit(Long countyAgentChainProfit) {
		this.countyAgentChainProfit = countyAgentChainProfit;
	}
	public Long getTownAgentChainProfit() {
		return townAgentChainProfit;
	}
	public void setTownAgentChainProfit(Long townAgentChainProfit) {
		this.townAgentChainProfit = townAgentChainProfit;
	}
	public Long getChainTotal() {
		return chainTotal;
	}
	public void setChainTotal(Long chainTotal) {
		this.chainTotal = chainTotal;
	}
	public Long getChainRemain() {
		return chainRemain;
	}
	public void setChainRemain(Long chainRemain) {
		this.chainRemain = chainRemain;
	}
	public Long getYstRemain() {
		return ystRemain;
	}
	public void setYstRemain(Long ystRemain) {
		this.ystRemain = ystRemain;
	}
	public Long getPhRemain() {
		return phRemain;
	}
	public void setPhRemain(Long phRemain) {
		this.phRemain = phRemain;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
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
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public String getCityPromoterName() {
		return cityPromoterName;
	}
	public void setCityPromoterName(String cityPromoterName) {
		this.cityPromoterName = cityPromoterName;
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
	
	public Long getMerchantPromoterId() {
		return merchantPromoterId;
	}
	public void setMerchantPromoterId(Long merchantPromoterId) {
		this.merchantPromoterId = merchantPromoterId;
	}
	public Long getCountyPromoterProfit() {
		return countyPromoterProfit;
	}
	public void setCountyPromoterProfit(Long countyPromoterProfit) {
		this.countyPromoterProfit = countyPromoterProfit;
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
	public String getCountyPromoterName() {
		return countyPromoterName;
	}
	public void setCountyPromoterName(String countyPromoterName) {
		this.countyPromoterName = countyPromoterName;
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
