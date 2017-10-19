package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
* @ClassName: HunterOrderProfitVO
* @Description: TODO(批发商订单分润记录) VO
* @author Mr.Dong
* @date 2017年6月1日 下午3:21:53
 */
public class HunterOrderProfitVO implements Serializable {

	private static final long serialVersionUID = 6066183713497091151L;

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
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date auditTime;//审核时间
    
    private Byte auditStatus;//审核状态
    
    private String reason;//审核原因
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date profitTime;//分润时间(北京传过来只入库)
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;//创建时间
    
    private Long cityAgentId;//市级代理id
    
    private Long countyAgentId;//县级代理id
    
    private Long cityPromoterId;//市级代理推荐人id
    
    private Long countyPromoterId;//县级代理推荐人id
    
    
    private Long  townAgentId;//社区代理id
    
    private Long  townPromoterId;//社区代理推荐人id
    
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
    
    private Long  townAgentProfit;//社区代理分润 
    
    private Long townPromoterProfit ;//社区代理推广师分润
    
    private  Long  townAgentChainProfit;//社区代理供应链分润
    
    
    
    
    private Double cityAgentProfit1;//市代分润
    
    private Double countyAgentProfit1;//县代分润

    private Double cityPromoterProfit1;//市级代理推分润
    
    private Double countyPromoterProfit1;//县代理推分润
    
    private Double chargeProfitTotal1;//管理费支出合计
    
    private Double chargeProfitRemain1;//管理费剩余
    
    private Double chainProfit1;//供应链利润

    private Double phChainProfit1;//普惠供应链利润

    private Double ystChainProfit1;//易商通供应链利润

    private Double cityAgentChainProfit1;//市级代理供应链分润

    private Double countyAgentChainProfit1;//县级代理供应链分润
    
    private Double chainProfitTotal1;//供应链支出合计

    private Double chainProfitRemain1;//供应链剩余
    
    private Double orderMoney1;//订单金额

    private Double hunterProfit1;//批发商分润
    
    private Double phProfit1;//普惠分润
    
    private Double chargeProfit1;//管理费

    private Double phChargeProfit1;//普惠管理费

    private Double ystChargeProfit1;//易商通管理费
    
    
    
    
    private Double  townAgentProfit1;//社区代理分润 
    
    private Double townPromoterProfit1 ;//社区代理推广师分润
    
    private  Double  townAgentChainProfit1;//社区代理供应链分润

    
    
    
    
    //7-12新添加
    private Long compensationMoney;//批发商赔付金
    
    private Long guildProfit;//工会
    
    private Long yytProfit ;//易耀通
    
    private String  fromSys;  //用户来源   null 0 批发商平台   1 普惠平台
    
    
    private Double compensationMoney1;//批发商赔付金
    
    private Double guildProfit1;//工会
    
    private Double yytProfit1 ;//易耀通
    
    
    
    
    
    
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

	public Double getCompensationMoney1() {
		return compensationMoney1;
	}

	public void setCompensationMoney1(Double compensationMoney1) {
		this.compensationMoney1 = compensationMoney1;
	}

	public Double getGuildProfit1() {
		return guildProfit1;
	}

	public void setGuildProfit1(Double guildProfit1) {
		this.guildProfit1 = guildProfit1;
	}

	public Double getYytProfit1() {
		return yytProfit1;
	}

	public void setYytProfit1(Double yytProfit1) {
		this.yytProfit1 = yytProfit1;
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

	public Double getChainProfit1() {
		return chainProfit1;
	}

	public void setChainProfit1(Double chainProfit1) {
		this.chainProfit1 = chainProfit1;
	}

	public Double getPhChainProfit1() {
		return phChainProfit1;
	}

	public void setPhChainProfit1(Double phChainProfit1) {
		this.phChainProfit1 = phChainProfit1;
	}

	public Double getYstChainProfit1() {
		return ystChainProfit1;
	}

	public void setYstChainProfit1(Double ystChainProfit1) {
		this.ystChainProfit1 = ystChainProfit1;
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

	public Double getChainProfitTotal1() {
		return chainProfitTotal1;
	}

	public void setChainProfitTotal1(Double chainProfitTotal1) {
		this.chainProfitTotal1 = chainProfitTotal1;
	}

	public Double getChainProfitRemain1() {
		return chainProfitRemain1;
	}

	public void setChainProfitRemain1(Double chainProfitRemain1) {
		this.chainProfitRemain1 = chainProfitRemain1;
	}

	public Double getOrderMoney1() {
		return orderMoney1;
	}

	public void setOrderMoney1(Double orderMoney1) {
		this.orderMoney1 = orderMoney1;
	}

	public Double getHunterProfit1() {
		return hunterProfit1;
	}

	public void setHunterProfit1(Double hunterProfit1) {
		this.hunterProfit1 = hunterProfit1;
	}

	public Double getPhProfit1() {
		return phProfit1;
	}

	public void setPhProfit1(Double phProfit1) {
		this.phProfit1 = phProfit1;
	}

	public Double getChargeProfit1() {
		return chargeProfit1;
	}

	public void setChargeProfit1(Double chargeProfit1) {
		this.chargeProfit1 = chargeProfit1;
	}

	
	public Double getPhChargeProfit1() {
		return phChargeProfit1;
	}

	public void setPhChargeProfit1(Double phChargeProfit1) {
		this.phChargeProfit1 = phChargeProfit1;
	}

	public Double getYstChargeProfit1() {
		return ystChargeProfit1;
	}

	public void setYstChargeProfit1(Double ystChargeProfit1) {
		this.ystChargeProfit1 = ystChargeProfit1;
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

	public Double getTownAgentProfit1() {
		return townAgentProfit1;
	}

	public void setTownAgentProfit1(Double townAgentProfit1) {
		this.townAgentProfit1 = townAgentProfit1;
	}

	public Double getTownPromoterProfit1() {
		return townPromoterProfit1;
	}

	public void setTownPromoterProfit1(Double townPromoterProfit1) {
		this.townPromoterProfit1 = townPromoterProfit1;
	}

	public Double getTownAgentChainProfit1() {
		return townAgentChainProfit1;
	}

	public void setTownAgentChainProfit1(Double townAgentChainProfit1) {
		this.townAgentChainProfit1 = townAgentChainProfit1;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Date getProfitTime() {
		return profitTime;
	}

	public void setProfitTime(Date profitTime) {
		this.profitTime = profitTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}