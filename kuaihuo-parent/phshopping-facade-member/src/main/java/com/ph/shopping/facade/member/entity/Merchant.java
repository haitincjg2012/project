package com.ph.shopping.facade.member.entity;

import com.ph.shopping.common.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
/**
 * @项目：phshopping-facade-member
 * @描述：商户信息dto
 * @作者 lzh
 * @时间：2017-5-12
 * @version: 2.1
 */
public class Merchant extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7615786485331647942L;

    /** 店铺的名称 */
    private String merchantName;

    /** 商户电话 */
    private String telPhone;


	/** 联系人名称 */
    @Column(name = "personName")
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


    /** 联系人省 */
    private Long provinceId;

    /** 联系人市 */
    private Long cityId;

    /** 区县id */
    private Long countyId;

    /** 联系人镇的id */
    private Long townId;

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

    /** 激活码 */
    private String cdKey;
    
    /** 是否激活  0:未激活  1:激活 */
    private Byte isCdKey;
    
    
    /** 是否删除 0:否 1：是 */
    private Byte isDelete;

    /** 对应登录表的id */
    private Long userId;

    /** 创建人id */
    private Long createrId;

    /** 修改人 */
    private Long updaterId;

    /** 经度 */
    private String longitude;

    /** 纬度 */
    private String latitude;

    /** 商户类别ID */
    private Long merchantTypeId;

    /**
     * 浏览量
     */
    private Integer views;
    /**
     *  商户介绍
     */
    private String description;
    
    /**
     *  是否置顶   0、未置顶  1、已置顶
     */
    private Integer isRecommend;
    
    /**
     *  置顶级别  0、代理置顶  1、运营置顶
     */
    private Integer isRecommendType;

    /**
     *  人均消费
     */
    private Long costMoney;
    /**
     * 门店图片
     * @return
     */
    private List<String> url;

    /**
     * 评价数量
     * @return
     */
    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
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

    public Long getMerchantTypeId() {
        return merchantTypeId;
    }

    public void setMerchantTypeId(Long merchantTypeId) {
        this.merchantTypeId = merchantTypeId;
    }

	public Byte getStatusLevel() {
		return statusLevel;
	}

	public void setStatusLevel(Byte statusLevel) {
		this.statusLevel = statusLevel;
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

	public String getCdKey() {
		return cdKey;
	}

	public void setCdKey(String cdKey) {
		this.cdKey = cdKey;
	}

	public Byte getIsCdKey() {
		return isCdKey;
	}

	public void setIsCdKey(Byte isCdKey) {
		this.isCdKey = isCdKey;
	}
    
}