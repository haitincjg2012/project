package com.alqsoft.entity.log;

import java.util.Date;

import org.alqframework.orm.hibernate.IdEntity;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月3日 下午3:58:43
 * 用户登录的基本信息
 */
public class UserLog extends IdEntity{
	private static final long serialVersionUID = -7906246338281258100L;
	
	private Long userId;// 用户id
	
	private String loginName;// 登陆用户名
	
	private String roleName;// 角色名称
	
	private String loginIp;// 登陆的ip
	
	private String sessionId;// 会话id
	
	private String browser;// 浏览器
	
	private Integer state = 0;// 0代表在线状态，1代表退出状态
	
	private Date loginTime;// 登陆的时间
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Date logoutTime;// 退出的时间
	
	
	
}
