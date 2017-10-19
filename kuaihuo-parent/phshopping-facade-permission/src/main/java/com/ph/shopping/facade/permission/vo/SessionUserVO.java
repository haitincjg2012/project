package com.ph.shopping.facade.permission.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @项目：com.ph.shopping.facade.permission.vo
 * @描述：
 * @作者： Mr.Shu
 * @创建时间：2017/5/19 17:20
 * @Copyright @2017 by Mr.Shu
 */
public class SessionUserVO implements Serializable {
    private static final long serialVersionUID = 6829940553030159312L;

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户手机号(登陆账号)
     */
    private String telphone;


    /**
     * 登陆账户对应的角色
     */
    List<SessionRoleVO> sessionRoleVo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public List<SessionRoleVO> getSessionRoleVo() {
        return sessionRoleVo;
    }

    public void setSessionRoleVo(List<SessionRoleVO> sessionRoleVo) {
        this.sessionRoleVo = sessionRoleVo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
