package com.ph.shopping.facade.spm.vo;

import java.io.Serializable;

/**
 * 
 * @ClassName: WarehouseAddressVO
 * @Description: 仓库地址VO
 * @author 王强
 * @date 2017年5月31日 下午5:17:34
 */
public class WarehouseAddressVO implements Serializable {

	private static final long serialVersionUID = 8689836264248823094L;

	private Long id;// 仓库地址id
	private Long userId;// 用户id
	private String contacts;// 联系人
	private String telPhone;// 联系电话
	private String phoneNo;// 座机号
	private Byte isDefault;// 是否默认地址
	private Byte isDeliveryPoint;// 是否提货点
	private String address;// 完整地址
	private String detailAddress;// 详细地址

	private String provinceId;// 省id
	private String cityId;// 市id
	private String countyId;// 区id

	private String provinceName;// 省名称
	private String cityName;// 市名称
	private String countyName;// 区名称

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Byte getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Byte isDefault) {
		this.isDefault = isDefault;
	}

	public Byte getIsDeliveryPoint() {
		return isDeliveryPoint;
	}

	public void setIsDeliveryPoint(Byte isDeliveryPoint) {
		this.isDeliveryPoint = isDeliveryPoint;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCountyId() {
		return countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

}
