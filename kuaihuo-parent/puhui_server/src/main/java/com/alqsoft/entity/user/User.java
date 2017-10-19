package com.alqsoft.entity.user;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.alqsoft.entity.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 用户实体
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-6-5 下午8:13:27
 * 
 */
@Entity
@Table(name = "alq_user",indexes = {@Index(columnList = "userName",name="index_user_name"),@Index(columnList = "email",name="index_email")})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) 
public class User extends IdEntity {
	@NotBlank(message = "用户名不能为空")
	@Length(min = 3, max = 20, message = "用户名必须在3到20之间，请重新输入")
	private String userName = "";// 用户名
	@NotBlank(message = "用户密码不能为空")
	@Length(min = 6, max = 100, message = "用户密码必须在6到50之间，请重新输入")
	private String userPassword = "";// 用户密码
	@NotBlank(message = "真实名称不能为空")
	@Length(max = 50, message = "真实名称不能超过50个字符")
	private String realName = "";// 真实名称
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
	private Integer gender=0;//0未知，1为男，2为女
	private Role role;// 角色
	private Long pid=0l;//0代表以1级代理商，依次往下降

	public Long getTimes() {
		return times;
	}

	public void setTimes(Long times) {
		this.times = times;
	}

	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Column(length = 1)
	public Integer getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Integer isLocked) {
		this.isLocked = isLocked;
	}

	@Column(length = 200)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(length = 100)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length = 50)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(length = 50)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(length = 50)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(length = 200)
	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Column(length = 50)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}
	
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}
}
