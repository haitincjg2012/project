package com.alqsoft.entity.msgpush;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @Title: ChatGroup.java
 * @Description: 群实体
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月16日 下午3:35:57
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@Entity
@Table(name = "alq_push_group", indexes = {
		@Index(columnList = "pushGroupName", name = "index_push_group_name"),
		@Index(columnList = "userId", name = "index_chat_user_id"),
		@Index(columnList = "accountNum", name = "index_account_num") })
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class PushGroup extends IdEntity{
	
	@NotBlank(message = "群名称不能为空")
	@Length(min = 2, max = 20, message = "群名称必须在2到20之间，请重新输入")
	private String pushGroupName;//群名称
	private Long accountNum;//前台用户数
	private String description;//群描述
	private Long userId;//用户id
	
	@Column(unique=true)
	public String getPushGroupName() {
		return pushGroupName;
	}
	public void setPushGroupName(String pushGroupName) {
		this.pushGroupName = pushGroupName;
	}
	public Long getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(Long accountNum) {
		this.accountNum = accountNum;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
