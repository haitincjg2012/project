package com.ph.shopping.facade.spm.vo;

import java.io.Serializable;
/**
 * 
* @ClassName: OnlineTakePointVO
* @Description: 提货点VO
* @author 王强
* @date 2017年6月21日 下午9:16:10
 */
public class OnlineTakePointVO implements Serializable {

	private static final long serialVersionUID = 3795566619255557393L;
	
	private String merchantName;//商户店名称
	private String address;//完整地址
	private Long id;//商户id
	private String contacts;//提过点联系人
	private String telPhone;//提货点手机电话
	private String phoneNo;//提货点座机电话

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
}
