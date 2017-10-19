package com.ph.shopping.facade.spm.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ph.shopping.common.core.base.BaseEntity;
/**
 * @项目：phshopping-facade-spm
 * @描述：供应商信息表
 * @作者 何文浪
 * @时间：2017-5-15
 * @version: 2.1
 */
@Table(name = "ph_supplier")
public class Supplier extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6654082771177903561L;

	/** 供应商名称 */
	@Column(name ="supplierName" )
    private String supplierName;

    /** 1:全国，2:本地 */
	@Column(name ="supplierType" )
    private Byte supplierType;

    /** 联系人名称 */
	@Column(name ="personName" )
    private String personName;

    /** 联系人电话 */
	@Column(name ="telPhone" )
    private String telPhone;

    /** 供应商审核状态：0待审核，1审核通过，2被驳回， */
	@Column(name ="status" )
    private Byte status;
	
	 /** 审核等级：1.市级代理    2. 县级代理, 4.ADMIN */
	@Column(name ="statusLevel" )
    private Byte statusLevel;

    /** 详细地址 */
	@Column(name ="address" )
    private String address;

    /** 身份证 */
	@Column(name ="personIdCardNo" )
    private String personIdCardNo;

    /** 营业执照编号 */
	@Column(name ="businessNo" )
    private String businessNo;

    /** 管辖省Id */
	@Column(name ="provinceId" )
    private Long provinceId;

    /** 管辖市id */
	@Column(name ="cityId" )
    private Long cityId;

    /** 管辖区Id */
	@Column(name ="countyId" )
    private Long countyId;

    /** 管辖社区Id */
	@Column(name ="townId" )
    private Long townId;

    /** ph_position表中的主键id 这里存的是区id */
	@Column(name ="positionId" )
    private Long positionId;

    /** 推广师id */
	@Column(name ="promoterId" )
    private Long promoterId;

    /** 是否删除 0:否 1：是 */
	@Column(name ="isDelete" )
    private Byte isDelete;

    /** 是否冻结 0:否 1:是 2.暂冻*/
	@Column(name ="isFrozen" )
    private Byte isFrozen;

	/** 对应登录表主键id */
	@Column(name ="userId" )
    private Long userId;
    
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Byte getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(Byte supplierType) {
        this.supplierType = supplierType;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }


    public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPersonIdCardNo() {
        return personIdCardNo;
    }

    public void setPersonIdCardNo(String personIdCardNo) {
        this.personIdCardNo = personIdCardNo;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public Long getTownId() {
        return townId;
    }

    public void setTownId(Long townId) {
        this.townId = townId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Long getPromoterId() {
        return promoterId;
    }

    public void setPromoterId(Long promoterId) {
        this.promoterId = promoterId;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public Byte getIsFrozen() {
        return isFrozen;
    }

    public void setIsFrozen(Byte isFrozen) {
        this.isFrozen = isFrozen;
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Byte getStatusLevel() {
		return statusLevel;
	}

	public void setStatusLevel(Byte statusLevel) {
		this.statusLevel = statusLevel;
	}
    
}