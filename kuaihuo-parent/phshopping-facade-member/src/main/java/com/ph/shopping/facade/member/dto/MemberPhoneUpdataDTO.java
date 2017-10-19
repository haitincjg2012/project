package com.ph.shopping.facade.member.dto;

import org.hibernate.validator.constraints.NotBlank;

import com.ph.shopping.common.core.base.BaseValidate;
/**
 * 
 * @ClassName:  MemberPhoneUpdataDTO   
 * @Description:会员手机号修改数据   
 * @author: liuy
 * @date:   2017年05月16日 上午11:23:29     
 * @Copyright: 2017
 */
public class MemberPhoneUpdataDTO extends BaseValidate{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = -2416463826486137807L;

	/**
	 * 原手机号
	 */
	@NotBlank(message="[原手机号]不能为空")
	private String origPhone;
	/**
	 * 新手机号
	 */
	@NotBlank(message="[新手机号]不能为空")
	private String newPhone;
	/**
	 * 支付密码
	 */
	@NotBlank(message="[支付密码]不能为空")
	private String payPwd;
	/**
	 * 修改人
	 */
	private Long updaterId;
	
	public String getOrigPhone() {
		return origPhone;
	}
	public void setOrigPhone(String origPhone) {
		this.origPhone = origPhone;
	}
	public String getNewPhone() {
		return newPhone;
	}
	public void setNewPhone(String newPhone) {
		this.newPhone = newPhone;
	}
	public String getPayPwd() {
		return payPwd;
	}
	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}
	public Long getUpdaterId() {
		return updaterId;
	}
	public void setUpdaterId(Long updaterId) {
		this.updaterId = updaterId;
	}
	
	
}
