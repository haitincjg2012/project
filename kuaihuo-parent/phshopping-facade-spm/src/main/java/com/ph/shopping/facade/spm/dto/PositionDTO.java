package com.ph.shopping.facade.spm.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * @项目：phshopping-facade-spm
 * @描述：区域表
 * @作者 何文浪
 * @时间：2017-5-22
 * @version: 2.1
 */
public class PositionDTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8541810972464903284L;

	/** 表流水 */
    private Long id;

    /** 创建者 */
    private String createUser;

    /** 创建ip */
    private String createdIp;

    /** 创建时间 */
    private Date createdTime;

    /** 更新时间 */
    private Date updateTime;

    /** 市id */
    private Long cityId;

    /** 市名称 */
    private String cityName;

    /** 区id */
    private Long countyId;

    /** 区名称 */
    private String countyName;

    /** 省id */
    private Long provinceId;

    /** 省名称 */
    private String provinceName;

    /** 镇id */
    private Long townId;

    /** 镇名称 */
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