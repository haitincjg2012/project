package com.ph.shopping.facade.profit.entity;

import java.io.Serializable;
import java.util.Date;

/**
* @ClassName: PurchaseOrderProfit
* @Description: 供应链订单分润实体
* @author Mr.Dong
* @date 2017年5月16日 下午5:01:30
 */
public class PurchaseOrderProfit implements Serializable {
	private static final long serialVersionUID = 4362323687102739636L;
	private Long id;// 表流水
	//订单号
	private String orderNo;
	
	//零售价
	private Long retailPrice;
	
	
	//结算成本
	private Long settlementPrice;
	
	
	//门店进货价格
	private Long purchasePrice;
	
	
	//物流费用
	private Long freight;
	
	
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
	private Long  countyAgentId;
	
	//社区级代理Id
	private Long  townAgentId;
	
	//创建时间 (分润时间)
	private Date createTime;
	
	//订单时间
	private Date orderTime;	

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

	

	public Long getFreight() {
		return freight;
	}

	public void setFreight(Long freight) {
		this.freight = freight;
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

	public Long getTownAgentId() {
		return townAgentId;
	}

	public void setTownAgentId(Long townAgentId) {
		this.townAgentId = townAgentId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
}