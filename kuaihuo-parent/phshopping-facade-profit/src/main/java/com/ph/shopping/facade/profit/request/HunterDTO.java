package com.ph.shopping.facade.profit.request;

import java.io.Serializable;
/**
 * 
* @ClassName: HunterDTO
* @Description: 批发商DTO
* @author 王强
* @date 2017年4月25日 下午6:06:26
 */
public class HunterDTO implements Serializable {

	private static final long serialVersionUID = -744549921319792739L;

	private String memberTel;//会员电话
	
	private Long orderMoney;//订单金额
	
	private Long phProfit;//普惠分润
	
	private Long provinceId;//省id
	
	private Long cityId;//市id
	
	private Long countyId;//区县id
	
	private Long townId;//乡镇id
	
	private String orderNo;//订单编号
	
	private String hunterTel;//批发商电话
	
	private String profitTime;//分润时间
	
	private String  fromSys;  //用户来源   null 0 批发商平台   1 普惠平台


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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getHunterTel() {
		return hunterTel;
	}

	public void setHunterTel(String hunterTel) {
		this.hunterTel = hunterTel;
	}

	public String getProfitTime() {
		return profitTime;
	}

	public void setProfitTime(String profitTime) {
		this.profitTime = profitTime;
	}

	public Long getTownId() {
		return townId;
	}

	public void setTownId(Long townId) {
		this.townId = townId;
	}

	public Long getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Long orderMoney) {
		this.orderMoney = orderMoney;
	}

	public Long getPhProfit() {
		return phProfit;
	}

	public void setPhProfit(Long phProfit) {
		this.phProfit = phProfit;
	}

	public String getFromSys() {
		return fromSys;
	}

	public void setFromSys(String fromSys) {
		this.fromSys = fromSys;
	}

}
