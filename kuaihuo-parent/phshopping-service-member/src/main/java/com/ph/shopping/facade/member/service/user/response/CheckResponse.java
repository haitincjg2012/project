package com.ph.shopping.facade.member.service.user.response;

import java.io.Serializable;

public class CheckResponse implements Serializable{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = -6172809811162272303L;

	/**
	 * 状态码
	 */
	private String code;
	/**
	 * 状态描述
	 */
	private String msg;
	/**
	 * 返回结果
	 */
	private Content content;
	/**
	 * 
	 */
	private String user;
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}


}
