package com.alqsoft.entity.account;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.alqframework.orm.hibernate.EntityGetUtils;
import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import com.alqsoft.entity.sms.ChatGroup;
import com.alqsoft.service.sms.ChatGroupService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * @Title: ChatGroupAccount.java
 * @Description: 群和用户中间表实体 (消息发送)
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月16日 下午3:52:43
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@Entity
@Table(name = "alq_chatgroup_account", indexes = {
		@Index(columnList = "accountId", name = "index_account_id"),
		@Index(columnList = "accountName", name = "index_account_name"),
		@Index(columnList = "chatGroupId", name = "index_chatgroup_id") })
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class ChatGroupAccount extends IdEntity{

	private Long accountId;
	@Length(max = 100)
	private String accountName;
	
	private Long chatGroupId;
	@XStreamOmitField
	private ChatGroup chatGroup;
	
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Long getChatGroupId() {
		return chatGroupId;
	}
	public void setChatGroupId(Long chatGroupId) {
		this.chatGroupId = chatGroupId;
	}
	@Transient
	public ChatGroup getChatGroup() {
		return (ChatGroup) EntityGetUtils.get(ChatGroupService.class, chatGroupId, chatGroup);
	}
	public void setChatGroup(ChatGroup chatGroup) {
		this.chatGroup = chatGroup;
	}
}
