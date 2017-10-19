package com.ph.shopping.facade.spm.dto;

import java.io.Serializable;
/**
 * 
* @ClassName: TakePointDTO
* @Description: 提货点DTO
* @author 王强
* @date 2017年6月21日 下午9:20:59
 */
public class TakePointDTO implements Serializable {

	private static final long serialVersionUID = 3873085896885461189L;
	
	private Long provinceId;//省
	
	private Long cityId;//市
	
	private Long countyId;//区
	
	private Long townId;//社区

	private Long userId;//商户id（登录信息表中的id）

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
