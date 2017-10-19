package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
import java.util.Date;

/**
* @ClassName: UnlineSupplyChainProfitVO
* @Description: 供应链订单分润VO
* @author Mr.Dong
* @date 2017年5月16日 下午5:01:30
 */
public class PurchaseOrderProfitVO implements Serializable {
	private static final long serialVersionUID = -1352085645208697516L;
	private Long id;// 表流水
	//订单号
	private String orderNo;
	private String cityAgentName;
	private String countyAgentName;
	private String townAgentName;
	private String cityPromoterName;
	private String countyPromoterName;
	private String townPromoterName;
	
	private String sellerName;
	private String buyerName;
	
	//审核人Id
	private Long  auditorId;
	//审核时间
	private String  auditDate;
	//审核状态       审核状态(0待审核，1通过，2不通过)
	private Byte auditStatus;
	
	//审核原因
	private String reason;
	
	//市级代理Id
	private Long  cityAgentId;
	
	//县级代理Id
	private Long  countryAgentId;
	
	//社区级代理Id
	private Long  townAgentId;
	
	//创建时间 (分润时间)
	private String createTime;
	
	//订单时间
	private String orderTime;	
	
	//零售价  (页面上的订单金额)
	private Long retailPrice;
	//结算成本
	private Long settlementPrice;
	//门店进货价格
	private Long purchasePrice;
	//物流费用
	private Long logisticsFee;
	//供应链利润
	private Long chainProfit;
	//普惠供应链收入
	private Long phIncome;
	//易商通供应链收入
	private Long ystIncome;
	//市级代理分润
	private Long cityAgentProfit;
	//县级代理分润
	private Long countyAgentProfit;
	//社区代理分润
	private Long townAgentProfit;
	//供应链支出合计
	private Long chainTotal;
	//供应链剩余
	private Long chainRemain;
	//易商通总余
	private Long ystRemain;
	//普惠总余
	private Long phRemain; 
	

	
	
	//零售价
	private Double retailPrice1;
	//结算成本
	private Double settlementPrice1;
	//门店进货价格
	private Double purchasePrice1;
	//物流费用
	private Double logisticsFee1;
	//供应链利润
	private Double chainProfit1;
	//普惠供应链收入
	private Double phIncome1;
	//易商通供应链收入
	private Double ystIncome1;
	//市级代理分润
	private Double cityAgentProfit1;
	//县级代理分润
	private Double countyAgentProfit1;
	//社区代理分润
	private Double townAgentProfit1;
	//供应链支出合计
	private Double chainTotal1;
	//供应链剩余
	private Double chainRemain1;
	//易商通总余
	private Double ystRemain1;
	//普惠总余
	private Double phRemain1;
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
		
		public String getTownPromoterName() {
			return townPromoterName;
		}
		public void setTownPromoterName(String townPromoterName) {
			this.townPromoterName = townPromoterName;
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
		public Long getAuditorId() {
			return auditorId;
		}
		public void setAuditorId(Long auditorId) {
			this.auditorId = auditorId;
		}
		public String getAuditDate() {
			return auditDate;
		}
		public void setAuditDate(String auditDate) {
			this.auditDate = auditDate;
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
		public Long getCityAgentId() {
			return cityAgentId;
		}
		public void setCityAgentId(Long cityAgentId) {
			this.cityAgentId = cityAgentId;
		}
		public Long getCountryAgentId() {
			return countryAgentId;
		}
		public void setCountryAgentId(Long countryAgentId) {
			this.countryAgentId = countryAgentId;
		}
		public Long getTownAgentId() {
			return townAgentId;
		}
		public void setTownAgentId(Long townAgentId) {
			this.townAgentId = townAgentId;
		}
		
		public Long getRetailPrice() {
			return retailPrice;
		}
		public void setRetailPrice(Long retailPrice) {
			this.retailPrice = retailPrice;
		}
		public Long getSettlementPrice() {
			return settlementPrice;
		}
		public void setSettlementPrice(Long settlementPrice) {
			this.settlementPrice = settlementPrice;
		}
		public Long getPurchasePrice() {
			return purchasePrice;
		}
		public void setPurchasePrice(Long purchasePrice) {
			this.purchasePrice = purchasePrice;
		}
		public Long getLogisticsFee() {
			return logisticsFee;
		}
		public void setLogisticsFee(Long logisticsFee) {
			this.logisticsFee = logisticsFee;
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
		public Double getRetailPrice1() {
			return retailPrice1;
		}
		public void setRetailPrice1(Double retailPrice1) {
			this.retailPrice1 = retailPrice1;
		}
		public Double getSettlementPrice1() {
			return settlementPrice1;
		}
		public void setSettlementPrice1(Double settlementPrice1) {
			this.settlementPrice1 = settlementPrice1;
		}
		public Double getPurchasePrice1() {
			return purchasePrice1;
		}
		public void setPurchasePrice1(Double purchasePrice1) {
			this.purchasePrice1 = purchasePrice1;
		}
		public Double getLogisticsFee1() {
			return logisticsFee1;
		}
		public void setLogisticsFee1(Double logisticsFee1) {
			this.logisticsFee1 = logisticsFee1;
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
		public String getCountyAgentName() {
			return countyAgentName;
		}
		public void setCountyAgentName(String countyAgentName) {
			this.countyAgentName = countyAgentName;
		}
		public String getCountyPromoterName() {
			return countyPromoterName;
		}
		public void setCountyPromoterName(String countyPromoterName) {
			this.countyPromoterName = countyPromoterName;
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
		
}