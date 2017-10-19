package com.ph.shopping.facade.member.dto;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import com.ph.shopping.common.core.base.BaseValidate;

/**
 * 
* @ClassName: PayPasswordAddDTO
* @Description: 修改支付密码   
* @author liuy
* @date 2017年6月8日 上午9:06:21
 */
public class PayPasswordUpdateDTO extends BaseValidate{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = 489794197747332811L;

	/**
	 * 密码使用者ID
	 */
	@NotNull(message="[密码使用者ID]不能为空")
	private Long userId;
	/**
	 * 使用者类型 1、会员 2、平台
	 */
	@NotNull(message="[使用者类型]不能为空")
	private Byte customerType;
	/**
	 * 创建人
	 */
	private Long createrId;
	/**
	 * 修改人
	 */
	@NotNull(message="[修改人]不能为空")
	private Long updaterId;
	/**
	 * 原密码
	 */
	private String originalPassword;
	/**
	 * 新密码
	 */
	@NotBlank(message="[新密码]不能为空")
	private String newPassword;
	/**
	 * 短信验证码
	 */
	private String smsCode;
	/**
	 * 手机号
	 */
	private String telPhone;
	
	public String getOriginalPassword() {
		return originalPassword;
	}
	public void setOriginalPassword(String originalPassword) {
		this.originalPassword = originalPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public Byte getCustomerType() {
		return customerType;
	}
	public void setCustomerType(Byte customerType) {
		this.customerType = customerType;
	}
	public Long getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}
	public Long getUpdaterId() {
		return updaterId;
	}
	public void setUpdaterId(Long updaterId) {
		this.updaterId = updaterId;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	
}
