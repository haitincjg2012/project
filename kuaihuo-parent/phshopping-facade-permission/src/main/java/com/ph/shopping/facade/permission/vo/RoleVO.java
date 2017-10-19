package com.ph.shopping.facade.permission.vo;

import java.io.Serializable;

public class RoleVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5645594700043186105L;

	private Long id;
	  /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编号
     */
    private Byte roleCode;

	/**
	 * 角色描述
	 */
	private String description;

    /**
     * 角色状态
     */
    private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Byte getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(Byte roleCode) {
		this.roleCode = roleCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
