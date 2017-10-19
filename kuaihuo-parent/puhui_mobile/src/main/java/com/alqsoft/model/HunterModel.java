package com.alqsoft.model;

import java.io.Serializable;
import java.util.List;

import com.alqsoft.entity.hunterruleattachment.HunterRuleAttachment;
import com.alqsoft.entity.phhunterruleattachment.PhHunterRuleAttachment;

public class HunterModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long merberId;// merber关联id

	private Long industryAssociationId;// 行业协会id

	private Long logoAttachmentId;// 头像id

	private String nickname;// 昵称

	private Long provinceId;// 省 直辖市 自治区 特别行政区编号

	private Long cityId;// 市区编号

	private Long countyId;// 县编号

	private String countyName; // 县名

	private Long townId;// 乡镇 街道办事处编号

	private String detail;// 详细地址
	
	private String  major;//专业信息

	/*
	 * private Long industryTypeId;// 行业一级分类id
	 * 
	 * private Long industryTypeId2;// 行业二级分类id
	 */
	private String industryTypeIdList;// 行业二级分类id的集合

	private String service;// 批发商服务

	private String longitude;// 经度

	private String latitude;// 维度

	private Integer positionLevel; // 当前用户级别 0省代理 1市代理 2县代理 3区代理

	private String station;// 驻地区域 经纬度定位

	private String businessLicence;// 商户营业执照

	private String idCardAttachment;// 身份证照

	private String otherAttachment;// 其他资料

	private String phone;// 手机号

	private String passWord;// 密码

	
	
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIndustryTypeIdList() {
		return industryTypeIdList;
	}

	public void setIndustryTypeIdList(String industryTypeIdList) {
		this.industryTypeIdList = industryTypeIdList;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getBusinessLicence() {
		return businessLicence;
	}

	public void setBusinessLicence(String businessLicence) {
		this.businessLicence = businessLicence;
	}

	public String getIdCardAttachment() {
		return idCardAttachment;
	}

	public void setIdCardAttachment(String idCardAttachment) {
		this.idCardAttachment = idCardAttachment;
	}

	public String getOtherAttachment() {
		return otherAttachment;
	}

	public void setOtherAttachment(String otherAttachment) {
		this.otherAttachment = otherAttachment;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public Integer getPositionLevel() {
		return positionLevel;
	}

	public void setPositionLevel(Integer positionLevel) {
		this.positionLevel = positionLevel;
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
