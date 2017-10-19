package com.ph.shopping.facade.spm.entity;

import com.ph.shopping.common.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @项目：phshopping-facade-permission
 * @描述：公共登录用户实体
 * @作者： Mr.Shu
 * @创建时间：2017-05-12
 * @Copyright @2017 by Mr.Shu
 */
@Table(name = "ph_permission_common_user")
public class PermissionCommonUser extends BaseEntity {
    private static final long serialVersionUID = -9056436515111782366L;


    /**
     * 用户名
     */
    @Column(name = "userName")
    private String userName;

    /**
     * 电话号码
     */
    @Column(name = "telphone")
    private String telphone;
    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 是否可用 1 可用；2 不可用
     */
    @Column(name = "isable")
    private Byte isable;

    /**
     * 登录时间
     */
    @Column(name = "loginTime")
    private Date loginTime;

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

    public Byte getIsable() {
        return isable;
    }

    public void setIsable(Byte isable) {
        this.isable = isable;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

}
