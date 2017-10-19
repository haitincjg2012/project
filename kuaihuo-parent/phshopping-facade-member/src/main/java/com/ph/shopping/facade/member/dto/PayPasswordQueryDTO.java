package com.ph.shopping.facade.member.dto;

import javax.validation.constraints.NotNull;

import com.ph.shopping.common.core.base.BaseValidate;
/**
 * 
 * @ClassName:  PayPasswordDto   
 * @Description:支付密码   
 * @author: 李杰
 * @date:   2017年4月25日 上午11:18:19     
 * @Copyright: 2017
 */
public class PayPasswordQueryDTO extends BaseValidate{

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
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Byte getCustomerType() {
		return customerType;
	}
	public void setCustomerType(Byte customerType) {
		this.customerType = customerType;
	}
	
}
