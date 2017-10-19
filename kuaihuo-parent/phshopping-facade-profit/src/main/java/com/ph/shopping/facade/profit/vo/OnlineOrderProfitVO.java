package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
* @ClassName: OnlineOrderProfitVO
* @Description: 线上订单分润VO
* @author Mr.Dong
* @date 2017年5月9日 下午4:01:36
 */
public class OnlineOrderProfitVO implements Serializable  {

	private static final long serialVersionUID = -209373260097839012L;
	private Long id;// 表流水

	private String orderNo;// 订单编号
	
	private Long  shippingProvinceId;//收货地址的省id
	
	private Long  shippingCityId;//收货地址的市id

	private Long  shippingCountyId;//收货地址的区id

	private Long  shippingTownId;//收货地址的镇id
	
	 
	private String   createTime;//创建时间(分润时间)
	
	private Integer auditStatus;//审核状态
	
	private Long auditorId;//审核人id
	
	private String  auditTime;//审核时间
	
	private String reason;//审核原因
	
	private String  orderTime;//订单时间
        
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
	
	private Long cityAgentPromoterId;//市代推荐人id
	private Long countyAgentPromoterId;//县代推荐人id
	private Long townAgentPromoterId;//社区代推荐人id
	private Long merchantPromoterId;//商户推荐人id
    
    
	
	
	
	
	
	private Long productMoney;// 商品金额  (霞哥)
	
	private Long settlementPrice;// 结算价格
	
	private Long freight;//物流费用
	
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
    
    
    
    
    
    
    
    
    
    
    private Double productMoney1;// 商品金额  (霞哥)
	
	private Double settlementPrice1;// 结算价格
	
	private Double freight1;//物流费用
	
	private Double chargeFee1;// 管理费

	private Double phChargeFee1;// 普惠管理费

	private Double ystChargeFee1;// 易商通管理费
	
	private Double cityAgentProfit1;// 市级代理分润

	private Double countyAgentProfit1;// 县级代理分润

	private Double townAgentProfit1;// 社区代理分润
	
	private Double cityPromoterProfit1;// 推广师推广市级代理分润

	private Double countyPromoterProfit1;// 推广师推广县级代理分润

	private Double townPromoterProfit1;// 推广师推广社区代理分润
	
	private Double merchantPromoterProfit1;// 商户推荐人分润
	
	private Double chargeProfitTotal1;// 管理费支出合计

	private Double chargeProfitRemain1;// 管理费剩余
	
	private Double merchantChainProfit1;// 商户供应链分润(会员自提的才分)
	
	private Double chainProfit1;//供应链利润

    private Double phIncome1;//普惠供应链收入

    private Double ystIncome1;//易商通供应链收入
    
    private Double cityAgentChainProfit1;//市代供应链分润

    private Double countyAgentChainProfit1;//县代供应链分润
    
    private Double townAgentChainProfit1;//区代理供应链分润金额
          
    private Double chainTotal1;//供应链支出合计

    private Double chainRemain1;//供应链剩余
    
    private Double ystRemain1;//易商通总余
    
    private Double phRemain1;//普惠总余 

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

	

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
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

	public Long getCityAgentPromoterId() {
		return cityAgentPromoterId;
	}

	public void setCityAgentPromoterId(Long cityAgentPromoterId) {
		this.cityAgentPromoterId = cityAgentPromoterId;
	}

	

	public Long getTownAgentPromoterId() {
		return townAgentPromoterId;
	}

	public void setTownAgentPromoterId(Long townAgentPromoterId) {
		this.townAgentPromoterId = townAgentPromoterId;
	}

	public Long getMerchantPromoterId() {
		return merchantPromoterId;
	}

	public void setMerchantPromoterId(Long merchantPromoterId) {
		this.merchantPromoterId = merchantPromoterId;
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

	public Double getProductMoney1() {
		return productMoney1;
	}

	public void setProductMoney1(Double productMoney1) {
		this.productMoney1 = productMoney1;
	}

	public Double getSettlementPrice1() {
		return settlementPrice1;
	}

	public void setSettlementPrice1(Double settlementPrice1) {
		this.settlementPrice1 = settlementPrice1;
	}

	public Double getFreight1() {
		return freight1;
	}

	public void setFreight1(Double freight1) {
		this.freight1 = freight1;
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

	public Double getMerchantChainProfit1() {
		return merchantChainProfit1;
	}

	public void setMerchantChainProfit1(Double merchantChainProfit1) {
		this.merchantChainProfit1 = merchantChainProfit1;
	}

	public Double getChainProfit1() {
		return chainProfit1;
	}

	public void setChainProfit1(Double chainProfit1) {
		this.chainProfit1 = chainProfit1;
	}

	public Double getPhIncome1() {
		return phIncome1;
	}

	public void setPhIncome1(Double phIncome1) {
		this.phIncome1 = phIncome1;
	}

	public Double getYstIncome1() {
		return ystIncome1;
	}

	public void setYstIncome1(Double ystIncome1) {
		this.ystIncome1 = ystIncome1;
	}

	public Double getCityAgentChainProfit1() {
		return cityAgentChainProfit1;
	}

	public void setCityAgentChainProfit1(Double cityAgentChainProfit1) {
		this.cityAgentChainProfit1 = cityAgentChainProfit1;
	}

	public Double getCountyAgentChainProfit1() {
		return countyAgentChainProfit1;
	}

	public void setCountyAgentChainProfit1(Double countyAgentChainProfit1) {
		this.countyAgentChainProfit1 = countyAgentChainProfit1;
	}

	public Double getTownAgentChainProfit1() {
		return townAgentChainProfit1;
	}

	public void setTownAgentChainProfit1(Double townAgentChainProfit1) {
		this.townAgentChainProfit1 = townAgentChainProfit1;
	}

	public Double getChainTotal1() {
		return chainTotal1;
	}

	public void setChainTotal1(Double chainTotal1) {
		this.chainTotal1 = chainTotal1;
	}

	public Double getChainRemain1() {
		return chainRemain1;
	}

	public void setChainRemain1(Double chainRemain1) {
		this.chainRemain1 = chainRemain1;
	}

	public Double getYstRemain1() {
		return ystRemain1;
	}

	public void setYstRemain1(Double ystRemain1) {
		this.ystRemain1 = ystRemain1;
	}

	public Double getPhRemain1() {
		return phRemain1;
	}

	public void setPhRemain1(Double phRemain1) {
		this.phRemain1 = phRemain1;
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

	public Long getCountyAgentPromoterId() {
		return countyAgentPromoterId;
	}

	public void setCountyAgentPromoterId(Long countyAgentPromoterId) {
		this.countyAgentPromoterId = countyAgentPromoterId;
	}

	public Long getCountyAgentProfit() {
		return countyAgentProfit;
	}

	public void setCountyAgentProfit(Long countyAgentProfit) {
		this.countyAgentProfit = countyAgentProfit;
	}

	public Long getCountyPromoterProfit() {
		return countyPromoterProfit;
	}

	public void setCountyPromoterProfit(Long countyPromoterProfit) {
		this.countyPromoterProfit = countyPromoterProfit;
	}

	public Double getCountyAgentProfit1() {
		return countyAgentProfit1;
	}

	public void setCountyAgentProfit1(Double countyAgentProfit1) {
		this.countyAgentProfit1 = countyAgentProfit1;
	}

	public Double getCountyPromoterProfit1() {
		return countyPromoterProfit1;
	}

	public void setCountyPromoterProfit1(Double countyPromoterProfit1) {
		this.countyPromoterProfit1 = countyPromoterProfit1;
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
