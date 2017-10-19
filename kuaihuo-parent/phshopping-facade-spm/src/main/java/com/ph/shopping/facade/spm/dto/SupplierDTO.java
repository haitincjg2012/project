package com.ph.shopping.facade.spm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ph.shopping.common.core.base.BaseValidate;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @项目：phshopping-facade-spm
 * @描述：供应商信息表
 * @作者 何文浪
 * @时间：2017-5-15
 * @version: 2.1
 */
public class SupplierDTO extends BaseValidate implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7722556884816467799L;

	/** 主键 */
    private Long id;

    @NotBlank(message = "企业名称不能为空")
    /** 供应商名称 */
    private String supplierName;

    /** 1:全国，2:本地 */
    private Byte supplierType;

    @NotBlank(message = "联系人不能为空")
    /** 联系人名称 */
    private String personName;

    @NotBlank(message = "登录帐号不能为空")
    /** 联系人电话 */
    private String telPhone;

    /** 供应商审核状态：0待审核，1审核通过，2被驳回， */
    private Byte status;
    
    /** 审核等级：1.市级代理    2. 县级代理, 4.ADMIN */
    private Byte statusLevel;

    @NotBlank(message = "详细地址不能为空")
    /** 详细地址 */
    private String address;

    /** 身份证 */
    private String personIdCardNo;

    /** 营业执照编号 */
    private String businessNo;

    @NotNull(message = "省不能为空")
    /** 管辖省Id */
    private Long provinceId;

    @NotNull(message = "市不能为空")
    /** 管辖市id */
    private Long cityId;

    @NotNull(message = "区不能为空")
    /** 管辖区Id */
    private Long countyId;

    /** 管辖社区Id */
    private Long townId;

    /** ph_position表中的主键id 这里存的是区id */
    private Long positionId;

    /**  */
    private Long promoterId;

    /** 是否删除 0:否 1：是 */
    private Byte isDelete;

    /** 是否冻结 0:否 1:是2.暂冻 */
    private Byte isFrozen;

    /** 创建人id */
    private Long createrId;

    /** 修改人 */
    private Long updaterId;

    /** 创建时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    /** 修改时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date updateTime;

    /** 对应登录表主键id */
    private Long userId;
    /** 扩展登录账号 */
    private String userTelphone;
    //扩展供应商图片表 6-2 熊克文
    private List<SupplierImageDTO> supplierImageDTOList;
    //区域扩展id
    private List<Long> positionIds  = new ArrayList<Long>(); 
    //扩展创建人id
    private Long extendsCreaterId;
    //扩展区域id(供后台列表使用)2017-5-27@何文浪
    private Long positionExtendsId;

    public List<SupplierImageDTO> getSupplierImageDTOList() {
        return supplierImageDTOList;
    }

    public void setSupplierImageDTOList(List<SupplierImageDTO> supplierImageDTOList) {
        this.supplierImageDTOList = supplierImageDTOList;
    }

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

	public List<Long> getPositionIds() {
		return positionIds;
	}

	public void setPositionIds(List<Long> positionIds) {
		this.positionIds = positionIds;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserTelphone() {
		return userTelphone;
	}

	public void setUserTelphone(String userTelphone) {
		this.userTelphone = userTelphone;
	}

	public Long getPositionExtendsId() {
		return positionExtendsId;
	}

	public void setPositionExtendsId(Long positionExtendsId) {
		this.positionExtendsId = positionExtendsId;
	}

	public Long getExtendsCreaterId() {
		return extendsCreaterId;
	}

	public void setExtendsCreaterId(Long extendsCreaterId) {
		this.extendsCreaterId = extendsCreaterId;
	}

	public Byte getStatusLevel() {
		return statusLevel;
	}

	public void setStatusLevel(Byte statusLevel) {
		this.statusLevel = statusLevel;
	}
    
	
}