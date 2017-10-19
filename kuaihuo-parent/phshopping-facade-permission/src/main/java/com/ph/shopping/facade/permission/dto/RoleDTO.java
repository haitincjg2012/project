package com.ph.shopping.facade.permission.dto;

import java.io.Serializable;

/**
 * @项目：phshopping-facade-permission
 * @描述：角色列表查询dto
 * @作者： Mr.zheng
 * @创建时间：2017-03-15
 * @Copyright @2017 by Mr.zheng
 */
public class RoleDTO implements Serializable{

    private static final long serialVersionUID = -6244664223048744656L;
    
    /**
     * 角色id
     */
    private Long id ;
    
    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色状态 1：启用 2：禁用
     */
    private Integer status;
    

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
