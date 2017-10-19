package com.ph.shopping.facade.member.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @项目：phshopping-facade-spm
 * @描述：商户图片表
 * @作者 何文浪
 * @时间：2017-5-12
 * @version: 2.1
 */
@Table(name = "ph_store_manager_image")
public class StoreManagerImage implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 19257803055479816L;

	/** 表流水 */
	@Id
    private Long id;

    /** 图片地址 */
	@Column(name ="url" )
    private String url;

    /** 序列 */
	@Column(name ="sort" )
    private Integer sort;

    /** 图片类型 1 合同照片 2 身份证图片 3 门店合影照片 */
	@Column(name ="type" )
    private Integer type;

    /** 店面经理记录表id */
	@Column(name ="storeManagerId" )
    private Long storeManagerId;

    /** 创建人 */
	@Column(name ="createrId" )
    private Long createrId;

    /** 创建时间 */
	@Column(name ="createTime" )
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getStoreManagerId() {
        return storeManagerId;
    }

    public void setStoreManagerId(Long storeManagerId) {
        this.storeManagerId = storeManagerId;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}