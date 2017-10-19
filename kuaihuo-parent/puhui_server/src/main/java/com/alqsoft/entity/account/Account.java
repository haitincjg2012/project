package com.alqsoft.entity.account;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @Title: Account.java
 * @Description: 前台用户实体类
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月16日 下午3:30:31
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */

@Entity
@Table(name = "alq_account", indexes = {
		@Index(columnList = "accountName", name = "index_account_name"),
		@Index(columnList = "mobile", name = "index_mobile"),
		@Index(columnList = "gender", name = "index_gender"),
		@Index(columnList = "email", name = "index_email") })
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Account extends IdEntity{
	
	@NotBlank(message = "用户名不能为空")
	@Length(min = 3, max = 20, message = "用户名必须在3到20之间，请重新输入")
	private String accountName = "";// 用户名
	@NotBlank(message = "用户密码不能为空")
	@Length(min = 6, max = 100, message = "用户密码必须在6到50之间，请重新输入")
	private String accountPassword = "";// 用户密码
	@NotBlank(message = "真实名称不能为空")
	@Length(max = 50, message = "真实名称不能超过50个字符")
	private String accountRealName = "";// 真实名称
	private String mobile = "";// 电话号码
	@NotBlank(message = "手机号码不能为空")
	@Length(max = 11, message = "手机号码必须小于12个字符，请重新输入")
	private String tel = "";// 手机号码
	@Email(message = "电子邮件的格式不对请重新输入")
	private String email = "";// 电子邮件
	@NotBlank(message = "联系地址不能为空")
	@Length(max = 200, message = "联系地址必须小于200个字符，请重新输入")
	private String address = "";// 地址
	private Integer isLocked = 0;// 是否被锁定，0代表没有被锁定，1代表没锁定
	private Long times = 0L;// 登录的次数
	private Integer gender = 0;// 0未知，1为男，2为女
	
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountPassword() {
		return accountPassword;
	}
	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}
	public String getAccountRealName() {
		return accountRealName;
	}
	public void setAccountRealName(String accountRealName) {
		this.accountRealName = accountRealName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(Integer isLocked) {
		this.isLocked = isLocked;
	}
	public Long getTimes() {
		return times;
	}
	public void setTimes(Long times) {
		this.times = times;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}

}
