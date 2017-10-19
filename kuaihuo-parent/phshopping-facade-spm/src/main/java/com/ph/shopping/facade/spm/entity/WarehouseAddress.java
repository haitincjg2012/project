package com.ph.shopping.facade.spm.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.ph.shopping.common.core.base.BaseEntity;

/**
 * 
 * @ClassName: WarehouseAddress
 * @Description: 仓库地址实体
 * @author 王强
 * @date 2017年5月31日 下午3:21:36
 */
@Table(name = "ph_user_address")
public class WarehouseAddress extends BaseEntity {

	private static final long serialVersionUID = -2214432660507107277L;

	/**
	 * 代理商、商户、供应商Id
	 */
	@Column(name = "userId")
	private Long userId;

	/**
	 * 联系人
	 */
	@Column(name = "contacts")
	@NotBlank(message = "[联系人]不可为空")
	@Length(max = 20, message = "[联系人]最大长度为20个字符")
	private String contacts;

	/**
	 * 手机号
	 */
	@Column(name = "telPhone")
	@NotBlank(message = "[手机号]不可为空")
	@Length(max = 30, message = "[手机号]最大长度为30个字符")
	private String telPhone;

	/**
	 * 座机号
	 */
	@Column(name = "phoneNo")
	private String phoneNo;

	/**
	 * 详细地址
	 */
	@Column(name = "detailAddress")
	private String detailAddress;

	/**
	 * 详细地址
	 */
	@Column(name = "address")
	@NotBlank(message = "[完整地址]不可为空")
	@Length(max = 50, message = "[完整地址]最大长度为50个字符")
	private String address;

	/**
	 * 省Id
	 */
	@Column(name = "provinceId")
	private Long provinceId;

	/**
	 * 市Id
	 */
	@Column(name = "cityId")
	private Long cityId;

	/**
	 * 区Id
	 */
	@Column(name = "countyId")
	private Long countyId;

	/**
	 * 是否默认地址 1 是；2 不是
	 */
	@Column(name = "isDefault")
	private Byte isDefault;

	/**
	 * 是否提货点 1 是；2 不是
	 */
	@Column(name = "isDeliveryPoint")
	private Byte isDeliveryPoint;

	/**
	 * 是否可用 1 可用；2 不可用
	 */
	@Column(name = "isable")
	private Byte isable;

	/**
	 * 区域Id（区域表换成ph_position表）
	 */
	@Column(name = "positionId")
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

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
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

	public Byte getIsable() {
		return isable;
	}

	public void setIsable(Byte isable) {
		this.isable = isable;
	}

	public Long getCountyId() {
		return countyId;
	}

	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

}
