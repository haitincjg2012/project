package com.alqsoft.entity.msgpush;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

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

public class PushGroup extends IdEntity{
	
	@NotBlank(message = "群名称不能为空")
	@Length(min = 2, max = 20, message = "群名称必须在2到20之间，请重新输入")
	private String pushGroupName;//群名称
	private Long accountNum;//前台用户数
	private String description;//群描述
	private Long userId;//用户id
	
	
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
