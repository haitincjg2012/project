package com.ph.shopping.facade.permission.entity;

import com.ph.shopping.common.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @项目：phshopping-facade-permission
 * @描述： 按钮实体表
 * @作者： Mr.Shu
 * @创建时间：2017-05-12
 * @Copyright @2017 by Mr.Shu
 */
@Table(name = "ph_permission_btn")
public class Btn extends BaseEntity {

    private static final long serialVersionUID = 3409139139798172232L;

    /**
     * 按钮名称
     */
    @Column(name = "btnName")
    private String btnName;

    /**
     * 按钮地址
     */
    @Column(name = "btnUrl")
    private String btnUrl;

    /**
     * 按钮code
     */
    @Column(name = "btnCode")
    private String btnCode;

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }

    public String getBtnUrl() {
        return btnUrl;
    }

    public void setBtnUrl(String btnUrl) {
        this.btnUrl = btnUrl;
    }

    public String getBtnCode() {
        return btnCode;
    }

    public void setBtnCode(String btnCode) {
        this.btnCode = btnCode;
    }
}
