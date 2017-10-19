package com.ph.shopping.facade.spm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @项目：phshopping-facade-spm
 * @描述：供应商图片信息表
 * @作者 何文浪
 * @时间：2017-5-15
 * @version: 2.1
 */
@Table(name="ph_supplier_image")
public class SupplierImage implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1368144419517338574L;

	/** 主键 */
	@Id
    private Long id;

    /** 图片路径 */
	@Column(name ="url" )
    private String url;

    /** 供应商Id */
	@Column(name ="supplierId" )
    private Long supplierId;

    /** 序列 1营业执照  2身份证第一张  3身份第二张 */
	@Column(name ="sort" )
    private Byte sort;

    /** 图片类型 1营业执照类型  2身份证类型 */
	@Column(name ="type" )
    private Byte type;

    /** 创建时间 */
	@Column(name ="createTime" )
    private Date createTime;

    /** 更新时间 */
	@Column(name ="updateTime" )
    private Date updateTime;

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

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Byte getSort() {
        return sort;
    }

    public void setSort(Byte sort) {
        this.sort = sort;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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