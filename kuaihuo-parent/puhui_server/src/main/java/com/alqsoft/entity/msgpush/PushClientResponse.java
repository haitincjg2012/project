package com.alqsoft.entity.msgpush;
/**
 * @Title: ClientResponse.java
 * @Description: 消息推送-客户端响应实体（推送信息接收成功时响应给服务端）
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月13日 下午8:29:08
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public class PushClientResponse {

	private String messageKey;// pushClientResponse 或 pushGroupClientResponse
	private String uniqueKey;//唯一标识 
	private String accountId;//用户id
	
	public String getMessageKey() {
		return messageKey;
	}
	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}
	public String getUniqueKey() {
		return uniqueKey;
	}
	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
