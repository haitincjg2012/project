package com.ph.shopping.facade.member.service.user.response;

import java.io.Serializable;

public class Content implements Serializable{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = -5833236530017046808L;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 身份证号
	 */
	private String card;
	/**
	 * 
	 */
	private String user;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	
	
}
