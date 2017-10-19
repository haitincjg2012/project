package com.ph.shopping.facade.permission.vo;


import java.io.Serializable;

/**
 * @项目：phshopping-facade-permission
 * @描述：按钮返回VO
 * @作者： Mr.Shu
 * @创建时间：2017年5月12日
 * @Copyright @2017 by Mr.Shu
 */
public class BtnVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2267431338902973275L;

    /**
     * id
     */
    private Long id;

    /**
     * 按钮名称
     */
    private String btnName;
    /**
     * 按钮地址
     */
    private String btnUrl;

    /**
     * 按钮code
     */
    private String btnCode;

    /**
     * 是否选择 1：选择 2：未选择(默认)
     */
    private Integer isChecked = 2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
    }

    public String getBtnCode() {
        return btnCode;
    }

    public void setBtnCode(String btnCode) {
        this.btnCode = btnCode;
    }
}
