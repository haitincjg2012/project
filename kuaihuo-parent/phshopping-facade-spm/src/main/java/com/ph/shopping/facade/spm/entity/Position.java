package com.ph.shopping.facade.spm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @项目：phshopping-facade-spm
 * @描述：区域表
 * @作者 何文浪
 * @时间：2017-5-22
 * @version: 2.1
 */
@Table(name = "ph_position")
public class Position implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3802171431706259525L;

	/** 表流水 */
	@Id
    private Long id;

    /** 创建者 */
	@Column(name="createUser")
    private String createUser;

    /** 创建ip */
	@Column(name="createdIp")
    private String createdIp;

    /** 创建时间 */
	@Column(name="createdTime")
    private Date createdTime;

    /** 更新时间 */
	@Column(name="updateTime")
    private Date updateTime;
	
	 /** 省id */
	@Column(name="provinceId")
    private Long provinceId;

    /** 省名称 */
	@Column(name="provinceName")
    private String provinceName;
	
    /** 市id */
	@Column(name="cityId")
    private Long cityId;

    /** 市名称 */
	@Column(name="cityName")
    private String cityName;

    /** 区id */
	@Column(name="countyId")
    private Long countyId;

    /** 区名称 */
	@Column(name="countyName")
    private String countyName;

    /** 镇id */
	@Column(name="townId")
    private Long townId;

    /** 镇名称 */
	@Column(name="townName")
    private String townName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreatedIp() {
        return createdIp;
    }

    public void setCreatedIp(String createdIp) {
        this.createdIp = createdIp;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Long getTownId() {
        return townId;
    }

    public void setTownId(Long townId) {
        this.townId = townId;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }
}