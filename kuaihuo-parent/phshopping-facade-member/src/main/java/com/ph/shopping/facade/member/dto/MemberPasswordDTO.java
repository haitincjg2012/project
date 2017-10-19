package com.ph.shopping.facade.member.dto;

import org.hibernate.validator.constraints.NotBlank;

import com.ph.shopping.common.core.base.BaseValidate;
/**
 * 
 * @ClassName:  MemberPasswordDto   
 * @Description:会员密码传输数据  
 * @author: 李杰
 * @date:   2017年4月25日 上午11:17:36     
 * @Copyright: 2017
 */
public class MemberPasswordDTO extends BaseValidate{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = 7005304431416847717L;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 手机号
	 */
	@NotBlank(message="[会员手机号]不能为空")
	private String telPhone;
	/**
	 *新密码
	 */
	@NotBlank(message="[新密码]不能为空")
	private String newPassword;
	/**
	 * 短信验证码
	 */
	private String smsCode;

	/**
	 * 修改人
	 */
	private Long updaterId;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public Long getUpdaterId() {
		return updaterId;
	}
	public void setUpdaterId(Long updaterId) {
		this.updaterId = updaterId;
	}
	
}
