package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
/**
 * 
* @ClassName: PositionCityVo
* @Description: 市VO
* @author 王强
* @date 2017年4月25日 下午4:59:22
 */
public class PositionCityVO implements Serializable {
	
	private static final long serialVersionUID = -7838159727921037206L;
	private long id;//主键id
	private long cityId;//市id
	private String cityName;//市名称
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	
}
