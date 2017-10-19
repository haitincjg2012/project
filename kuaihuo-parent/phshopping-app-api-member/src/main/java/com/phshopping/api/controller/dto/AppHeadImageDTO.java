package com.phshopping.api.controller.dto;

import java.io.Serializable;
/**
 * 
 * @ClassName:  HeadImageDTO   
 * @Description:头像传输Dto  
 * @author: 李杰
 * @date:   2017年5月24日 下午2:07:04     
 * @Copyright: 2017
 */
public class AppHeadImageDTO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -8603592297605571891L;
	/**
	 * 头像地址
	 */
	private String headImgUrl;
	/**
	 * 用户token 标记
	 */
	private String token;
	
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
