package com.alqsoft.model;

import java.io.Serializable;

public class HunterLabelModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long merberId;//merber关联id
	
	private Long industryAssociationId;//行业协会id
	
	private Long logoAttachmentId;//头像id
	
	private String nickname;//昵称
	
	private Long provinceId;// 省 直辖市 自治区 特别行政区编号
	
	private Long cityId;// 市区编号
	
	private Long countyId;// 县编号
	
	private String countyName; // 县名
	
	private Long townId;// 乡镇 街道办事处编号
	
	private String detail;//详细地址
	
	private String industryTypeIdList;//行业二级分类id的集合
	
	private String service;//批发商服务
	
	private String longitude;//经度
	
	private String latitude;//维度
	
	private String station;//经纬度定位地址
	
	private String  major;//专业信息
	
	private Integer positionLevel;//当前用户级别  0省代理  1市代理  2县代理 3区代理

	
	

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public Integer getPositionLevel() {
		return positionLevel;
	}

	public void setPositionLevel(Integer positionLevel) {
		this.positionLevel = positionLevel;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getIndustryTypeIdList() {
		return industryTypeIdList;
	}

	public void setIndustryTypeIdList(String industryTypeIdList) {
		this.industryTypeIdList = industryTypeIdList;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
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

	public Long getMerberId() {
		return merberId;
	}

	public void setMerberId(Long merberId) {
		this.merberId = merberId;
	}

	public Long getIndustryAssociationId() {
		return industryAssociationId;
	}

	public void setIndustryAssociationId(Long industryAssociationId) {
		this.industryAssociationId = industryAssociationId;
	}

	public Long getLogoAttachmentId() {
		return logoAttachmentId;
	}

	public void setLogoAttachmentId(Long logoAttachmentId) {
		this.logoAttachmentId = logoAttachmentId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
