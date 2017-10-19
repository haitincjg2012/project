package com.ph.shopping.facade.spm.dto;

import java.io.Serializable;

/**
 * 
 * @ClassName: WarehouseAddressDTO
 * @Description: 仓库地址DTO
 * @author 王强
 * @date 2017年5月31日 下午3:47:38
 */
public class WarehouseAddressDTO implements Serializable {

	private static final long serialVersionUID = 6944150123100869237L;
	/**
	 * 仓库地址id
	 */
	private Long wareHouseId;

	/**
	 * 代理商、商户、供应商Id
	 */
	private Long userId;

	/**
	 * 联系人
	 */
	private String contacts;

	/**
	 * 手机号
	 */
	private String telPhone;

	/**
	 * 座机号
	 */
	private String phoneNo;

	/**
	 * 完整地址
	 */
	private String address;

	/**
	 * 详细地址
	 */
	private String detailAddress;

	/**
	 * 省Id
	 */
	private Long provinceId;

	/**
	 * 市Id
	 */
	private Long cityId;

	/**
	 * 区Id
	 */
	private Long countyId;

	/**
	 * 是否可用 1 可用；2 不可用
	 */
	private Byte isable;

	/**
	 * 是否默认地址 1 是；2 不是
	 */
	private Byte isDefault;

	/**
	 * 是否提货点 1 是；2 不是
	 */
	private Byte isDeliveryPoint;

	/**
	 * 省名称
	 */
	private String provinceName;

	/**
	 * 市名称
	 */
	private String cityName;

	/**
	 * 区名称
	 */
	private String countyName;

	/**
	 * 区域Id（区域表换成ph_position表）
	 */
	private Long positionId;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Byte getIsable() {
		return isable;
	}

	public void setIsable(Byte isable) {
		this.isable = isable;
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

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public Long getCountyId() {
		return countyId;
	}

	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}

	public Long getWareHouseId() {
		return wareHouseId;
	}

	public void setWareHouseId(Long wareHouseId) {
		this.wareHouseId = wareHouseId;
	}

}
