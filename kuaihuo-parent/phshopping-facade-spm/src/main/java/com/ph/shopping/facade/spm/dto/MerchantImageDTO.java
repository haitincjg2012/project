package com.ph.shopping.facade.spm.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * @项目：phshopping-facade-spm
 * @描述：商户图片表
 * @作者 何文浪
 * @时间：2017-5-12
 * @version: 2.1
 */
public class MerchantImageDTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3173695878013712709L;

	/** 表流水 */
    private Long id;

    /** 图片地址 */
    private String url;

    /** 序列 */
    private Integer sort;

    /** 图片类型 1 营业执照图片 2 身份证图片 3 门店照片 */
    private Integer type;

    /** 商户id 关联商户表主键id */
    private Long merchantId;

    /**  */
    private Long createrId;

    /** 创建时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
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

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
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