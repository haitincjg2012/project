package com.ph.shopping.facade.profit.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
* @ClassName: HunterOrderProfit
* @Description: TODO(批发商订单分润记录)
* @author Mr.Dong
* @date 2017年6月1日 下午3:21:53
 */
public class HunterOrderProfit implements Serializable {
	private static final long serialVersionUID = 6345337367988026205L;

    private Long id;

    private String orderNo;//订单号

    private String cityAgentName;//市代名字
    
    private String countyAgentName;//县代名字
    
    private String cityPromoterName;//市推名字
    
    private String countyPromoterName;//县推名字
    

      
    private String memberTel;//下单用户手机号

    private Long provinceId;

    private Long cityId;

    private Long countyId;
    
    private Long townId;

    private String hunterTel;//批发商电话

    private Byte isAccept;//是否接受 默认接受  1
    
    private Long auditorId;//审核人id
    
    private String auditTime;//审核时间
    
    private Byte auditStatus;//审核状态
    
    private String reason;//审核原因
    
    private String profitTime;//分润时间
    
    private String createTime;//创建时间
    
    private Long cityAgentId;//市级代理id
    
    private Long countyAgentId;//县级代理id
    
    private Long cityPromoterId;//市级代理推荐人id
    
    private Long countyPromoterId;//县级代理推荐人id
    
    
    private Long  townAgentId;//社区代理id
    
    private Long  townPromoterId;//社区代理推荐人id
    
    private Long  townAgentProfit;//社区代理分润 
    
    private Long townPromoterProfit ;//社区代理推广师分润
    
    private  Long  townAgentChainProfit;//社区代理供应链分润

    private String  townAgentName;//  社区代理名称
    
    private String  townPromoterName;//社区代理推荐人名称
    
    
    
    
    
    
    private Long cityAgentProfit;//市代分润
    
    private Long countyAgentProfit;//县代分润

    private Long cityPromoterProfit;//市级代理推分润
    
    private Long countyPromoterProfit;//县代理推分润
    
    private Long chargeProfitTotal;//管理费支出合计
    
    private Long chargeProfitRemain;//管理费剩余
    
    private Long chainProfit;//供应链利润

    private Long phChainProfit;//普惠供应链利润

    private Long ystChainProfit;//易商通供应链利润

    private Long cityAgentChainProfit;//市级代理供应链分润

    private Long countyAgentChainProfit;//县级代理供应链分润
    
    private Long chainProfitTotal;//供应链支出合计

    private Long chainProfitRemain;//供应链剩余
    
    private Long orderMoney;//订单金额

    private Long hunterProfit;//批发商分润
    
    private Long phProfit;//普惠分润
    
    private Long chargeProfit;//管理费

    private Long phChargeProfit;//普惠管理费

    private Long ystChargeProfit;//易商通管理费
    
    
    private Long compensationMoney;//批发商赔付金
    
    private Long guildProfit;//工会
    
    private Long yytProfit ;//易耀通
    
    private String  fromSys;  //用户来源   null 0 批发商平台   1 普惠平台
    
    
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

	public Long getHunterProfit() {
		return hunterProfit;
	}

	public void setHunterProfit(Long hunterProfit) {
		this.hunterProfit = hunterProfit;
	}

	public Long getPhProfit() {
		return phProfit;
	}

	public void setPhProfit(Long phProfit) {
		this.phProfit = phProfit;
	}

	public Long getChargeProfit() {
		return chargeProfit;
	}

	public void setChargeProfit(Long chargeProfit) {
		this.chargeProfit = chargeProfit;
	}

	

	public Long getPhChargeProfit() {
		return phChargeProfit;
	}

	public void setPhChargeProfit(Long phChargeProfit) {
		this.phChargeProfit = phChargeProfit;
	}

	public Long getYstChargeProfit() {
		return ystChargeProfit;
	}

	public void setYstChargeProfit(Long ystChargeProfit) {
		this.ystChargeProfit = ystChargeProfit;
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

	public Long getChainProfit() {
		return chainProfit;
	}

	public void setChainProfit(Long chainProfit) {
		this.chainProfit = chainProfit;
	}

	public Long getPhChainProfit() {
		return phChainProfit;
	}

	public void setPhChainProfit(Long phChainProfit) {
		this.phChainProfit = phChainProfit;
	}

	public Long getYstChainProfit() {
		return ystChainProfit;
	}

	public void setYstChainProfit(Long ystChainProfit) {
		this.ystChainProfit = ystChainProfit;
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

	public Long getChainProfitTotal() {
		return chainProfitTotal;
	}

	public void setChainProfitTotal(Long chainProfitTotal) {
		this.chainProfitTotal = chainProfitTotal;
	}

	public Long getChainProfitRemain() {
		return chainProfitRemain;
	}

	public void setChainProfitRemain(Long chainProfitRemain) {
		this.chainProfitRemain = chainProfitRemain;
	}

	public String getMemberTel() {
		return memberTel;
	}

	public void setMemberTel(String memberTel) {
		this.memberTel = memberTel;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getCountyId() {
		return countyId;
	}

	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}

	public Long getTownId() {
		return townId;
	}

	public void setTownId(Long townId) {
		this.townId = townId;
	}

	public String getHunterTel() {
		return hunterTel;
	}

	public void setHunterTel(String hunterTel) {
		this.hunterTel = hunterTel;
	}

	public Byte getIsAccept() {
		return isAccept;
	}

	public void setIsAccept(Byte isAccept) {
		this.isAccept = isAccept;
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

	public String getProfitTime() {
		return profitTime;
	}

	public void setProfitTime(String profitTime) {
		this.profitTime = profitTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public Long getTownAgentProfit() {
		return townAgentProfit;
	}

	public void setTownAgentProfit(Long townAgentProfit) {
		this.townAgentProfit = townAgentProfit;
	}

	public Long getTownPromoterProfit() {
		return townPromoterProfit;
	}

	public void setTownPromoterProfit(Long townPromoterProfit) {
		this.townPromoterProfit = townPromoterProfit;
	}

	public Long getTownAgentChainProfit() {
		return townAgentChainProfit;
	}

	public void setTownAgentChainProfit(Long townAgentChainProfit) {
		this.townAgentChainProfit = townAgentChainProfit;
	}

	public String getTownAgentName() {
		return townAgentName;
	}

	public void setTownAgentName(String townAgentName) {
		this.townAgentName = townAgentName;
	}

	public String getTownPromoterName() {
		return townPromoterName;
	}

	public void setTownPromoterName(String townPromoterName) {
		this.townPromoterName = townPromoterName;
	}

	public Long getCompensationMoney() {
		return compensationMoney;
	}

	public void setCompensationMoney(Long compensationMoney) {
		this.compensationMoney = compensationMoney;
	}

	public Long getGuildProfit() {
		return guildProfit;
	}

	public void setGuildProfit(Long guildProfit) {
		this.guildProfit = guildProfit;
	}

	public Long getYytProfit() {
		return yytProfit;
	}

	public void setYytProfit(Long yytProfit) {
		this.yytProfit = yytProfit;
	}

	public String getFromSys() {
		return fromSys;
	}

	public void setFromSys(String fromSys) {
		this.fromSys = fromSys;
	}

}