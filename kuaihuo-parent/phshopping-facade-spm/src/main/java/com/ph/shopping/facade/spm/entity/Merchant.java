package com.ph.shopping.facade.spm.entity;

import com.ph.shopping.common.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @项目：phshopping-facade-spm
 * @描述：商户信息表
 * @作者 何文浪 @时间：2017-5-12
 * @version: 2.1
 */
@Table(name = "ph_merchant")
public class Merchant extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7615786485331647942L;

	/** 店铺的名称 */
	@Column(name = "merchantName")
	private String merchantName;

	/** 商户电话 */
	@Column(name = "telPhone")
	private String telPhone;

	public Long getUuidId() {
		return uuidId;
	}

	public void setUuidId(Long uuidId) {
		this.uuidId = uuidId;
	}

	/** 联系人名称 */
	@Column(name = "personName")
	private String personName;

	/** 门店详细地址 */
	@Column(name = "address")
	private String address;

	/** 联系人地址 */
	@Column(name = "contactAddress")
	private String contactAddress;

	/** 所属代理Id 如果由平台添加默认为 0 */
	@Column(name = "agentId")
	private Long agentId;

	/** 营业执照编号 */
	@Column(name = "businessNo")
	private String businessNo;

	/** 企业名称 */
	@Column(name = "companyName")
	private String companyName;

	/** 身份证号码 */
	@Column(name = "idCardNo")
	private String idCardNo;

	/** 商户分润比率 */
	@Column(name = "businessProfitRatio")
	private BigDecimal businessProfitRatio;

	/** 联系人省 */
	@Column(name = "provinceId")
	private Long provinceId;

	/** 联系人市 */
	@Column(name = "cityId")
	private Long cityId;

	/** 区县id */
	@Column(name = "countyId")
	private Long countyId;

	/** 联系人镇的id */
	@Column(name = "townId")
	private Long townId;

	/** ph_position表中的主键id 门店地址 */
	@Column(name = "positionId")
	private Long positionId;

	/** 推广师Id */
	@Column(name = "promoterId")
	private Long promoterId;

	/** 0：审核中 1：审核通过 2：未通过 （原来对应值：审核未通过0 审核未通过2 审核通过正常1 冻结 3） */
	@Column(name = "status")
	private Byte status;
	/** 审核等级：1.市级代理 2. 县级代理, 4.ADMIN */
	@Column(name = "statusLevel")
	private Byte statusLevel;
	/** 是否冻结 0:否 1:是 */
	@Column(name = "isFrozen")
	private Byte isFrozen;

	/** 激活码 */
	@Column(name = "cdKey")
	private String cdKey;

	/** 是否激活 0:未激活 1:激活 */
	@Column(name = "isCdKey")
	private Byte isCdKey;

	/** 是否删除 0:否 1：是 */
	@Column(name = "isDelete")
	private Byte isDelete;

	/** 对应登录表的id */
	@Column(name = "userId")
	private Long userId;

	/** 创建人id */
	@Column(name = "createrId")
	private Long createrId;

	/** 修改人 */
	@Column(name = "updaterId")
	private Long updaterId;

	/** 经度 */
	@Column(name = "longitude")
	private String longitude;

	/** 纬度 */
	@Column(name = "latitude")
	private String latitude;

	/** 商户类别ID */
	@Column(name = "merchantTypeId")
	private Long merchantTypeId;

	/**
	 * 浏览量
	 */
	@Column(name = "views")
	private Integer views;

	@Column(name = "uuidId")
	private Long uuidId;

	/**
	 * 商户介绍
	 */
	@Column(name = "description")
	private String description;

	/**
	 *代理置顶
	 */
	@Column(name = "isRecommend")
	private Integer isRecommend;

	public Integer getRecommendManage() {
		return recommendManage;
	}

	public void setRecommendManage(Integer recommendManage) {
		this.recommendManage = recommendManage;
	}

	/**
	 *运营中心置顶
	 */
	@Column(name = "recommendManage")
	private Integer recommendManage;

	/**
	 * 置顶级别  1、运营置顶   3、代理置顶
	 */
	@Column(name = "isRecommendType")
	private Integer isRecommendType;

	/**
	 * 人均消费
	 */
	@Column(name = "costMoney")
	private Long costMoney;

    /*营业开始时间*/
	@Column(name = "openBeginTime")
	private String openBeginTime;
    /*营业结束时间*/
	@Column(name = "openEndTime")
	private String openEndTime;
    /*非营业开始时间*/
	@Column(name = "closeBeginTime")
	private Date closeBeginTime;
    /*非营业关闭时间*/
	@Column(name = "closeEndTime")
	private Date closeEndTime;
	
	
	public String getOpenBeginTime() {
		return openBeginTime;
	}

	public void setOpenBeginTime(String openBeginTime) {
		this.openBeginTime = openBeginTime;
	}

	public String getOpenEndTime() {
		return openEndTime;
	}

	public void setOpenEndTime(String openEndTime) {
		this.openEndTime = openEndTime;
	}


	public Date getCloseBeginTime() {
		return closeBeginTime;
	}

	public void setCloseBeginTime(Date closeBeginTime) {
		this.closeBeginTime = closeBeginTime;
	}

	public Date getCloseEndTime() {
		return closeEndTime;
	}

	public void setCloseEndTime(Date closeEndTime) {
		this.closeEndTime = closeEndTime;
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

	public BigDecimal getBusinessProfitRatio() {
		return businessProfitRatio;
	}

	public void setBusinessProfitRatio(BigDecimal businessProfitRatio) {
		this.businessProfitRatio = businessProfitRatio;
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