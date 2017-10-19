package com.ph.shopping.facade.profit.dto;

import java.io.Serializable;
/**
 * 
* @ClassName: PositionDto
* @Description: 区域DTO
* @author 王强
* @date 2017年4月25日 下午4:01:36
 */
public class PositionDTO implements Serializable {
	private static final long serialVersionUID = -3379860590083119495L;
	private long provinceId;//省ID
	private long cityId;//城市ID
	private long countyId;//区县ID
	private long townId;//乡镇ID
	public long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}
	public long getCityId() {
		return cityId;
	}
	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	public long getCountyId() {
		return countyId;
	}
	public void setCountyId(long countyId) {
		this.countyId = countyId;
	}
	public long getTownId() {
		return townId;
	}
	public void setTownId(long townId) {
		this.townId = townId;
	}
	
}
