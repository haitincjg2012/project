package com.ph.shopping.facade.spm.vo;

import java.io.Serializable;

public class WareHouseVO implements Serializable {

	private static final long serialVersionUID = 1516985516435850572L;

	private String contacts;//联系人
	private String telPhone;//联系电话
	private String address;//完整地址
	private String supplierName;//供应商的名称

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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
}
