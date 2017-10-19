package com.ph.shopping.facade.member.vo;

import java.io.Serializable;
/**
 * 
 * @ClassName:  MemberPhoneUpdataVO   
 * @Description:会员手机号修改数据   
 * @author: 李杰
 * @date:   2017年4月25日 上午11:23:29     
 * @Copyright: 2017
 */
public class MemberPhoneUpdataVO implements Serializable{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = -2416463826486137807L;

	/**
	 * 原手机号
	 */
	private String origPhone;
	/**
	 * 新手机号
	 */
	private String newPhone;
	/**
	 * 支付密码
	 */
	private String payPwd;
	
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
	
	
}
