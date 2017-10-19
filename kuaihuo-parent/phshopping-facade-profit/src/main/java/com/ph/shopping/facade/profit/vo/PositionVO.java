package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
/**
 * 
* @ClassName: PositionVo
* @Description: 区域VO
* @author 王强
* @date 2017年4月25日 下午5:02:12
 */
public class PositionVO implements Serializable {
	
	private static final long serialVersionUID = -7838159727921037206L;
	private long id;//主键id
	private long provinceId;//省id
	private String provinceName;//省名称
	private long cityId;//市id
	private String cityName;//市名称
	private long countyId;//区县id
	private String countyName;//区县名称
	private long townId;//乡镇id
	private String townName;//乡镇名称

	public long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public long getCountyId() {
		return countyId;
	}

	public void setCountyId(long countyId) {
		this.countyId = countyId;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public long getTownId() {
		return townId;
	}

	public void setTownId(long townId) {
		this.townId = townId;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
