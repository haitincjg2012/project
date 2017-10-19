package com.ph.shopping.facade.member.vo;

import java.io.Serializable;

/**
 * 
 * @ClassName:  BankCardBindInfoVO   
 * @Description:银行卡绑定数据 
 * @author: 李杰
 * @date:   2017年4月25日 上午11:14:46   
 * @updateTime liuy重构 2017年5月17日 上午11:45  
 * @Copyright: 2017
 */
public class MemberBankCardBindInfoVO implements Serializable {

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = 3722122416287957997L;
	
	/***************************以下 会员银行卡绑定关系表 字段***************************/	
	/**
	 * 绑定ID（会员银行卡绑定关系表主键）
	 */
	private Long bindCardId;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 银行卡信息ID
	 */
	private Long bankCardInfoId;

	/**
	 * 银行卡号
	 */
	private String bankCardNo;
	/**
	 * 银行所绑定的手机号
	 */
	private String bindPhone;
	/**
	 * 开户银行
	 */
	private String bankName;
	/**
	 * 开户人姓名
	 */
	private String ownerName;
	/**
	 * 身份证号	
	 */
	private String idCardNo;
	/**
	 * 银行卡基础信息Id
	 */
	private Long bankCodenameDataId;

	public Long getBindCardId() {
		return bindCardId;
	}

	public void setBindCardId(Long bindCardId) {
		this.bindCardId = bindCardId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getBankCardInfoId() {
		return bankCardInfoId;
	}

	public void setBankCardInfoId(Long bankCardInfoId) {
		this.bankCardInfoId = bankCardInfoId;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBindPhone() {
		return bindPhone;
	}

	public void setBindPhone(String bindPhone) {
		this.bindPhone = bindPhone;
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

	public Long getBankCodenameDataId() {
		return bankCodenameDataId;
	}

	public void setBankCodenameDataId(Long bankCodenameDataId) {
		this.bankCodenameDataId = bankCodenameDataId;
	}
}
