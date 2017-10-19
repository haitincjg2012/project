package com.ph.shopping.facade.spm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * @项目：phshopping-facade-spm
 * @描述：商户信息表
 * @作者 何文浪
 * @时间：2017-5-12
 * @version: 2.1
 */
public class MerchantVO extends PositionExtendVO {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1256225580343841098L;

	/** 表流水 */
    private Long id;
    
    /** 是否激活 0:未激活 1:激活 */
    private Integer isCdKey;
    
    /** 激活码 */
    private Integer  cdKey;

    /** 店铺的名称 */
    private String merchantName;

    /** 商户电话 */
    private String telPhone;

    /** 联系人名称 */
    private String personName;

    /** 门店详细地址 */
    private String address;

    /** 联系人地址 */
    private String contactAddress;

    /** 所属代理Id  如果由平台添加默认为 0  */
    private Long agentId;

    /** 营业执照编号 */
    private String businessNo;

    /** 企业名称 */
    private String companyName;

    /** 身份证号码 */
    private String idCardNo;

    /** 商户分润比率 */
    private BigDecimal businessProfitRatio;

    /** ph_position表中的主键id 门店地址 */
    private Long positionId;

    /** 推广师Id */
    private Long promoterId;

    /** 0：审核中 1：审核通过 2：未通过 （原来对应值：审核未通过0  审核未通过2  审核通过正常1 冻结  3） */
    private Byte status;
    
    /** 审核等级：1.市级代理    2. 县级代理, 4.ADMIN */
   private Byte statusLevel;

    /** 是否冻结 0:否 1:是 */
    private Byte isFrozen;

    /** 是否删除 0:否 1：是 */
    private Byte isDelete;

    /** 对应登录表主键id */
    private Long userId;

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

    /** 经度 */
    private String longitude;

    /** 纬度 */
    private String latitude;
    
    private String description;//商户介绍
    
    private Long costMoney;//人均消费 
    
    private Integer isRecommend;//是否置顶   0、未置顶  1、已置顶
    
    private Integer isRecommendType;//置顶级别  0、代理置顶  1、运营置顶

    /**
     * 推广师名称
     */
    private String promoterMemberName;

    /**
     * 推广师电话
     */
    private String promoterTelPhone;
    
    /**
     * 商户类别一级ID
     */
    private Long merchantFirstTypeIds;

    /**
     * 商户类别二级ID
     */
    private Long merchantSecondIds;
    //扩展商户图片表 -- 2017-5-12何文浪
    private List<MerchantImageVO> merchantImageVOList;
    //扩展商户类别挂靠表 -- 2017-5-12何文浪
    private List<MerchantMerchantTypeVO> merchantMerchantTypeVOList;
    /*扩展商户类别名-- 2017-5-16何文浪*/
    private String merchantTypeName;
    /*扩展商户门店图片-- 2017-5-16何文浪*/
    private String merchantUrl;
    /*扩展距离-- 2017-5-16何文浪*/
    private String distance;
    /*商户门店区域名称-- 2017-5-16何文浪*/
    private String positionName;
    /*附件距离-- 2017-5-16何文浪*/
    private Integer distanceNum;
    /*代理商父级Id-- 2017-5-24何文浪*/
    private Long agentParentId;
    /*代理商父级名称-- 2017-5-24何文浪*/
    private String parentName;
    /**状态 2017-6-26导出列表添加*/
    private String statusName;
    /**冻结状态 2017-6-26导出列表添加*/
    private String isFrozenName;
    /*置顶顺序*/
	private Long isRecommendSsss;

    /**
     * 浏览量
     */
    private Integer views;
    
    private List<DishVO> dishVOList;
    
    
    public List<DishVO> getDishVOList() {
		return dishVOList;
	}

	public void setDishVOList(List<DishVO> dishVOList) {
		this.dishVOList = dishVOList;
	}

	public Integer getViews() {
        return views;
    }

    public Long getFirstMerchantTypeIds() {
        return merchantFirstTypeIds;
    }

    public void setMerchantFirstTypeIds(Long merchantFirstTypeIds) {
        this.merchantFirstTypeIds = merchantFirstTypeIds;
    }

    public Long getMerchantSecondIds() {
        return merchantSecondIds;
    }

    public void setMerchantSecondIds(Long merchantSecondIds) {
        this.merchantSecondIds = merchantSecondIds;
    }

    public Long getId() {
        return id;
    }

    public String getPromoterMemberName() {
        return promoterMemberName;
    }

    public void setPromoterMemberName(String promoterMemberName) {
        this.promoterMemberName = promoterMemberName;
    }

    public String getPromoterTelPhone() {
        return promoterTelPhone;
    }

    public void setPromoterTelPhone(String promoterTelPhone) {
        this.promoterTelPhone = promoterTelPhone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }


    public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public BigDecimal getBusinessProfitRatio() {
        return businessProfitRatio;
    }

    public void setBusinessProfitRatio(BigDecimal businessProfitRatio) {
        this.businessProfitRatio = businessProfitRatio;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getIsFrozen() {
        return isFrozen;
    }

    public void setIsFrozen(Byte isFrozen) {
        this.isFrozen = isFrozen;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

	public List<MerchantImageVO> getMerchantImageVOList() {
		return merchantImageVOList;
	}

	public void setMerchantImageVOList(List<MerchantImageVO> merchantImageVOList) {
		this.merchantImageVOList = merchantImageVOList;
	}

	public String getMerchantTypeName() {
		return merchantTypeName;
	}

	public void setMerchantTypeName(String merchantTypeName) {
		this.merchantTypeName = merchantTypeName;
	}

	public String getMerchantUrl() {
		return merchantUrl;
	}

	public void setMerchantUrl(String merchantUrl) {
		this.merchantUrl = merchantUrl;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Integer getDistanceNum() {
		if(this.distanceNum == null || this.distanceNum <= 0)//判断是否小于0，设为默认值
			this.distanceNum = 5;
		return distanceNum;
	}

	public void setDistanceNum(Integer distanceNum) {
		this.distanceNum = distanceNum;
	}

	public Long getAgentParentId() {
		return agentParentId;
	}

	public void setAgentParentId(Long agentParentId) {
		this.agentParentId = agentParentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
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

	public Byte getStatusLevel() {
		return statusLevel;
	}

	public void setStatusLevel(Byte statusLevel) {
		this.statusLevel = statusLevel;
	}

    public List<MerchantMerchantTypeVO> getMerchantMerchantTypeVOList() {
        return merchantMerchantTypeVOList;
    }

    public void setMerchantMerchantTypeVOList(List<MerchantMerchantTypeVO> merchantMerchantTypeVOList) {
        this.merchantMerchantTypeVOList = merchantMerchantTypeVOList;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCostMoney() {
		return costMoney;
	}

	public void setCostMoney(Long costMoney) {
		this.costMoney = costMoney;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Integer getIsRecommendType() {
		return isRecommendType;
	}

	public void setIsRecommendType(Integer isRecommendType) {
		this.isRecommendType = isRecommendType;
	}

	public Integer getIsCdKey() {
		return isCdKey;
	}

	public void setIsCdKey(Integer isCdKey) {
		this.isCdKey = isCdKey;
	}



	public Long getIsRecommendSsss() {
		return isRecommendSsss;
	}

    public Integer getCdKey() {
        return cdKey;
    }

    public void setCdKey(Integer cdKey) {
        this.cdKey = cdKey;
    }

    public void setIsRecommendSsss(Long isRecommendSsss) {
		this.isRecommendSsss = isRecommendSsss;
	}
	
	
    
    
}