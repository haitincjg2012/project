package com.ph.shopping.facade.profit.request;

import java.io.Serializable;
/**
 * 
* @ClassName: PositionDTO
* @Description: 区域DTO
* @author 王强
* @date 2017年4月25日 下午6:07:11
 */
public class PositionDTO implements Serializable {
	private static final long serialVersionUID = 261069537378261166L;
	private Long provinceId;//省id
	private Long cityId;//市id
	private Long countyId;//区县id

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
}
