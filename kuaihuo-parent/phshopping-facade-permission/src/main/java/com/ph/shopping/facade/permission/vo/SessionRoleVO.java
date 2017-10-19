package com.ph.shopping.facade.permission.vo;

import com.ph.shopping.common.core.customenum.RoleEnum;

import java.io.Serializable;

/**
 * 登陆角色vo实体
 * @author zhengpeng
 *
 */
public class SessionRoleVO implements Serializable {

	private static final long serialVersionUID = 1271305496020041687L;
	
	/**
	 * 角色id
	 */
	private Long id;

	
	private String roleName;
	
	/**
	 * 角色code{@link RoleEnum}
	 */
	private Byte roleCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Byte getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(Byte roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
