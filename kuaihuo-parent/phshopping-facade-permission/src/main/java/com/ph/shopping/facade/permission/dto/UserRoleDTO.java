package com.ph.shopping.facade.permission.dto;

import com.ph.shopping.common.core.customenum.RoleEnum;

import java.io.Serializable;


/**
 * @项目：phshopping-facade-permission
 * @描述： 用户角色dto
 * @作者： Mr.zheng
 * @创建时间：2017-03-14
 * @Copyright @2017 by Mr.zheng
 */
public class UserRoleDTO implements Serializable {

    private static final long serialVersionUID = 7377466214296737203L;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 手机号
     */
    private String telphone;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建人
     */
    private Long createrId;

    /**
     * 修改人
     */
    private Long updaterId;

    /**
     * 角色code {@link RoleEnum}
     */
    private Byte roleCode;

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Byte getRoleCode() {
        return roleCode;
	}

    public void setRoleCode(Byte roleCode) {
        this.roleCode = roleCode;
	}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }
}
