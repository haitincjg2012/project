/**  
 * @Title:  AppShareIndexDTO.java   
 * @Package com.phshopping.api.controller.dto   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月27日 上午9:20:50   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.phshopping.api.controller.dto;

import java.io.Serializable;

/**   
 * @ClassName:  AppShareIndexDTO   
 * @Description:分享传输数据   
 * @author: 李杰
 * @date:   2017年7月27日 上午9:20:50     
 * @Copyright: 2017
 */
public class AppShareIndexDTO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -4228890871433323056L;
	/**
	 * 手机号
	 */
	private String telPhone;
	/**
	 * 类型：0、会员 1、商户
	 */
	private Byte type;
	
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
}
