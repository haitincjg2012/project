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

import com.alqsoft.entity.msgpush.PushGroup;
import com.alqsoft.service.msgpush.PushGroupService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * @Title: pushGroupAccount.java
 * @Description: 群和用户中间表实体 （推送）
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月16日 下午3:52:43
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@Entity
@Table(name = "alq_pushgroup_account", indexes = {
		@Index(columnList = "accountId", name = "index_account_id"),
		@Index(columnList = "accountName", name = "index_account_name"),
		@Index(columnList = "pushGroupId", name = "index_pushgroup_id") })
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class PushGroupAccount extends IdEntity{

	private Long accountId;
	
	@Length(min = 2, max = 100, message = "用户名必须在2到100之间，请重新输入")
	private String accountName;
	
	private Long pushGroupId;
	@XStreamOmitField
	private PushGroup pushGroup;
	
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
	
	public Long getPushGroupId() {
		return pushGroupId;
	}
	public void setPushGroupId(Long pushGroupId) {
		this.pushGroupId = pushGroupId;
	}
	
	@Transient
	public PushGroup getPushGroup() {
		return (PushGroup) EntityGetUtils.get(PushGroupService.class, pushGroupId, pushGroup);
	}
	public void setPushGroup(PushGroup pushGroup) {
		this.pushGroup = pushGroup;
	}
	
	
}
