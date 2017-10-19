package com.ph.shopping.facade.spm.dto;

/**
 * 
* @ClassName: ManageBankCardDTO
* @Description: 银行卡DTO
* @author 王强
* @date 2017年5月24日 下午4:54:22
 */
public class ManageBankCardDTO {
	private String ownerName;//开户人
	private String idCardNo;//身份证号
	private String cardNo;//银行卡号
	private String bankName;//银行名称
	private String verificationCode;//验证码
	
	private Long bankCodenameDataId;//银行卡基础表Id 
	
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
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public Long getBankCodenameDataId() {
		return bankCodenameDataId;
	}
	public void setBankCodenameDataId(Long bankCodenameDataId) {
		this.bankCodenameDataId = bankCodenameDataId;
	}
}
