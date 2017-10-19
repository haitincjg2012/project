package com.ph.shopping.facade.system.entity;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 物流公司实体
 *
 * @author 郑朋
 * @create 2017/6/8
 **/
@Table(name = "ph_logistics")
public class Logistics implements Serializable{

    private static final long serialVersionUID = 5470746711507465470L;

    @Id
    private Long id;

    /**
     * 物流公司名称
     */
    @Column(name = "logisticsName")
    private String logisticsName;

    /**
     * 拼音
     */
    @Column(name = "logisticsSpell")
    private String logisticsSpell;

    /**
     * 物流公司图标
     */
    @Column(name = "image")
    private String image;

    /**
     * 电话
     */
    @Column(name = "telphone")
    private String telphone;

    /**
     * 地址
     */
    @Column(name = "dnfAddress")
    private String dnfAddress;


    /**
     * 删除标记 1未删除 2 已删除
     */
    @Column(name = "deleteFlag")
    private Byte deleteFlag;


    /**
     * 创建时间
     */
    @Column(name = "createTime")
    private Date createTime;


    /**
     * 修改时间
     */
    @Column(name = "updateTime")
    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getLogisticsSpell() {
        return logisticsSpell;
    }

    public void setLogisticsSpell(String logisticsSpell) {
        this.logisticsSpell = logisticsSpell;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getDnfAddress() {
        return dnfAddress;
    }

    public void setDnfAddress(String dnfAddress) {
        this.dnfAddress = dnfAddress;
    }

    public Byte getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Byte deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
