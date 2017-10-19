package com.ph.shopping.facade.member.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
/**
 * 
 * @ClassName:  BankBindInfoDTO   
 * @Description:银行卡绑定数据
 * @author: 熊克文
 * @date: 2017/5/23
 * @Copyright: 2017
 */
public class BankCardBindInfoDTO extends BaseValidate implements Serializable {

	private static final long serialVersionUID = -4747734633757244024L;
	/**
	 * 绑定ID（会员银行卡绑定关系表主键）
	 */
	private Long bindCardId;
	/**
	 * 用户id
	 */
	@NotNull(message = "用户id不能为空")
	private Long userId;
	/**
	 * 银行卡号
	 */
	@NotNull(message = "银行卡号不能为空")
	private String bankCardNo;
	/**
	 * 银行所绑定的手机号
	 */
	@NotNull(message = "开户手机号不能为空")
	private String bindPhone;
	/**
	 * 开户银行卡基础信息id
	 */
	private Long bankCodenameDataId;
	/**
	 * 开户银行卡名称
	 */
	private String bankName;
	/**
	 * 开户人姓名
	 */
	@NotNull(message = "开户人姓名不能为空")
	private String ownerName;
	/**
	 * 身份证号
	 */
	@NotNull(message = "开户人身份证号不能为空")
	private String idCardNo;
	/**
	 * 当前登录ip
	 */
	@NotNull(message = "当前登录ip不能为空")
	private String createIp;
	/**
	 * 当前登陆人id
	 */
	@NotNull(message = "当前登陆人id不能为空")
	private Long loginUserId;


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Long getBankCodenameDataId() {
		return bankCodenameDataId;
	}

	public void setBankCodenameDataId(Long bankCodenameDataId) {
		this.bankCodenameDataId = bankCodenameDataId;
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

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}

	public Long getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(Long loginUserId) {
		this.loginUserId = loginUserId;
	}

	public Long getBindCardId() {
		return bindCardId;
	}

	public void setBindCardId(Long bindCardId) {
		this.bindCardId = bindCardId;
	}
	
}
