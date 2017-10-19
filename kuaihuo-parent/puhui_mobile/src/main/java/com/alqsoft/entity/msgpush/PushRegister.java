package com.alqsoft.entity.msgpush;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * @Title: Register.java
 * @Description: 消息推送注册缓冲区实体类
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月12日 上午11:20:04
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 *
 */
public class PushRegister {
	private String messageKey;//pushRegister
	private String key;//对应的用户id
	private Integer state;// 0 单推，1、群推

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	
	
}
