package com.ph.shopping.facade.spm.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * @项目：phshopping-facade-spm
 * @描述：供应商信息表
 * @作者 何文浪
 * @时间：2017-5-15
 * @version: 2.1
 */
public class SupplierVO extends PositionExtendVO {
    /**
     *
     */
	private static final long serialVersionUID = -6436776622695632152L;

	/** 主键 */
    private Long id;

    /** 供应商名称 */
    private String supplierName;

    /** 1:全国，2:本地 */
    private Byte supplierType;

    /** 联系人名称 */
    private String personName;

    /** 联系人电话 */
    private String telPhone;

    /** 供应商审核状态：0待审核，1审核通过，2被驳回， */
    private Byte status;
    
    /** 审核等级：1.市级代理    2. 县级代理, 4.ADMIN */
   private Byte statusLevel;

    /** 详细地址 */
    private String address;

    /** 身份证 */
    private String personIdCardNo;

    /** 营业执照编号 */
    private String businessNo;

    /** ph_position表中的主键id 这里存的是区id */
    private Long positionId;

    /**  */
    private Long promoterId;

    /** 是否删除 0:否 1：是 */
    private Byte isDelete;

    /** 是否冻结 0:否 1:是 2.暂冻*/
    private Byte isFrozen;

    /** 创建人id */
    private Long createrId;

    /** 修改人 */
    private Long updaterId;

    /** 对应登录表主键id */
    private Long userId;

    /** 创建时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    /** 修改时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date updateTime;
    /**供应商图片-2017-5-15@author 何文浪添加*/
    private List<SupplierImageVO> supplierImageVOList;
    /**联系区域-2017-5-25@author 何文浪添加*/
    private String addressName;
    /**扩展登录账号-2017-5-26@author 何文浪添加*/
    private String userTelphone;
    /**状态 2017-6-26导出列表添加*/
    private String statusName;
    /**冻结状态 2017-6-26导出列表添加*/
    private String isFrozenName;
    /**类型 2017-6-26导出列表添加*/
    private String supplierTypeName;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
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

	public List<SupplierImageVO> getSupplierImageVOList() {
		return supplierImageVOList;
	}

	public void setSupplierImageVOList(List<SupplierImageVO> supplierImageVOList) {
		this.supplierImageVOList = supplierImageVOList;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getUserTelphone() {
		return userTelphone;
	}

	public void setUserTelphone(String userTelphone) {
		this.userTelphone = userTelphone;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getIsFrozenName() {
		return isFrozenName;
	}

	public void setIsFrozenName(String isFrozenName) {
		this.isFrozenName = isFrozenName;
	}

	public String getSupplierTypeName() {
		return supplierTypeName;
	}

	public void setSupplierTypeName(String supplierTypeName) {
		this.supplierTypeName = supplierTypeName;
	}

	public Byte getStatusLevel() {
		return statusLevel;
	}

	public void setStatusLevel(Byte statusLevel) {
		this.statusLevel = statusLevel;
	}
    
	
}