package com.ph.shopping.facade.spm.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ph.shopping.common.core.base.BaseEntity;

/**
 * 
 * @ClassName: ManageBankCardInfo
 * @Description: 后台绑定银行卡信息
 * @author 王强
 * @date 2017年5月23日 下午3:53:46
 */
@Table(name = "ph_user_bank_card_info") // ph_manager_bank_card_info
public class ManageBankCardInfo extends BaseEntity {

	private static final long serialVersionUID = -7945733454880484940L;

	/** 用户id */
	@Column(name = "userId")
	private Long userId;

	/** 银行卡号 */
	@Column(name = "cardNo")
	private String cardNo;

	/** 银行所绑定的手机号 */
	@Column(name = "telPhone")
	private String telPhone;

	/** 开户银行名字 */
	@Column(name = "bankName")
	private String bankName;

	/** 开户人姓名 */
	@Column(name = "ownerName")
	private String ownerName;

	/** 身份证号 */
	@Column(name = "idCardNo")
	private String idCardNo;

	/** 创建ip */
	@Column(name = "createIp")
	private String createIp;

	/** 是否删除 */
	@Column(name = "isDelete")
	private Byte isDelete;

	/** 银行编号 */
	@Column(name = "bankNo")
	private String bankNo;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo == null ? null : cardNo.trim();
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone == null ? null : telPhone.trim();
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName == null ? null : bankName.trim();
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName == null ? null : ownerName.trim();
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo == null ? null : idCardNo.trim();
	}

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp == null ? null : createIp.trim();
	}

	public Byte getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Byte isDelete) {
		this.isDelete = isDelete;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
}
