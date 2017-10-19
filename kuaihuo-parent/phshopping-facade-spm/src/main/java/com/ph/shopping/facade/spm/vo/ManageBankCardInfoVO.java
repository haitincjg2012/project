package com.ph.shopping.facade.spm.vo;

import java.io.Serializable;
/**
 * 
* @ClassName: ManageBankCardInfoVO
* @Description: 银行卡VO
* @author 王强
* @date 2017年5月25日 下午4:59:32
 */
public class ManageBankCardInfoVO implements Serializable {

	private static final long serialVersionUID = 4817595039714277729L;
	
	private long id;//银行卡基础表id
	private String telPhone;//登录手机号
	private String bankName;//银行名称
	private String ownerName;//卡户人姓名
	private String idCardNo;//身份证
	private String cardNo;//银行卡号
	private String userId;//用户id
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
