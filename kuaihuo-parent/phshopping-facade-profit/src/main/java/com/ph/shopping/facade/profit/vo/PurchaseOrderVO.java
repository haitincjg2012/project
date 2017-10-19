package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 
* @ClassName: PurchaseOrderVO
* @Description: 供应链订单表VO(用于取供应链订单过来分润)(目前只有商户向代理商的进货的订单才分润)
* @author Mr.Dong
* @date 2017年5月17日 下午5:01:30
 */
public class PurchaseOrderVO implements Serializable {
	private static final long serialVersionUID = 159809859438673582L;
	private Long  id;//主键
	private String orderNo;//订单号
	private Date orderTime;//订单时间
	private Long retailPrice;//零售价
	private Long freight;//物流费
	private Long settlementPrice;//结算价
	private Long purchaserId;//商户id(买方)
	private Long senderId;//代理商id(卖方)
	
	
	private String cityAgentName;//市级代理名字
	private String countyAgentName;//县级代理名字
	private String townAgentName;//社区代理名字	
	private Long cityAgentId;//市级代理id
	private Long countyAgentId;//县级代理id
	private Long townAgentId;//社区代理id
	
	
	private Long merchantPositionId;//商户的区域id
	
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
	public Long getPurchaserId() {
		return purchaserId;
	}
	public void setPurchaserId(Long purchaserId) {
		this.purchaserId = purchaserId;
	}
	public Long getSenderId() {
		return senderId;
	}
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
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
	public Long getMerchantPositionId() {
		return merchantPositionId;
	}
	public void setMerchantPositionId(Long merchantPositionId) {
		this.merchantPositionId = merchantPositionId;
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
