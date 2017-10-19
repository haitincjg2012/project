package com.alqsoft.entity.position;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author sunhuijie
 *
 * @date 2016年12月20日
 *
 */
@Entity
@Table(name = "ph_position", indexes = {})
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Position extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long provinceId;// 省 直辖市 自治区 特别行政区编号
	private String provinceName;// 省 直辖市 自治区 特别行政区名字
	private Long cityId;// 市区编号
	private String cityName;// 市区名称
	private Long countyId;// 县编号
	private String countyName;// 县名称
	private Long townId;// 乡镇 街道办事处编号
	private String townName;// 乡镇 街道办事处名称

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getCountyId() {
		return countyId;
	}

	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public Long getTownId() {
		return townId;
	}

	public void setTownId(Long townId) {
		this.townId = townId;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

}