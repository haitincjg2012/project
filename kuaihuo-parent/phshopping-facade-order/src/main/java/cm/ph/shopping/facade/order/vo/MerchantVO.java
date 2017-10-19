package cm.ph.shopping.facade.order.vo;

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
public class MerchantVO {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1256225580343841098L;

	/** 表流水 */
    private Long id;

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
//     */
//    private Long merchantSecondIds;
//    //扩展商户图片表 -- 2017-5-12何文浪
//    private List<MerchantImageVO> merchantImageVOList;
//    //扩展商户类别挂靠表 -- 2017-5-12何文浪
//    private List<MerchantMerchantTypeVO> merchantMerchantTypeVOList;
//    /*扩展商户类别名-- 2017-5-16何文浪*/
//    private String merchantTypeName;
//    /*扩展商户门店图片-- 2017-5-16何文浪*/
//    private String merchantUrl;
//    /*扩展距离-- 2017-5-16何文浪*/
//    private String distance;
//    /*商户门店区域名称-- 2017-5-16何文浪*/
//    private String positionName;
//    /*附件距离-- 2017-5-16何文浪*/
//    private Integer distanceNum;
//    /*代理商父级Id-- 2017-5-24何文浪*/
//    private Long agentParentId;
//    /*代理商父级名称-- 2017-5-24何文浪*/
//    private String parentName;
//    /**状态 2017-6-26导出列表添加*/
//    private String statusName;
//    /**冻结状态 2017-6-26导出列表添加*/
//    private String isFrozenName;

    /**
     * 浏览量
     */
    private Integer views;

    public Integer getViews() {
        return views;
    }

    public Long getFirstMerchantTypeIds() {
        return merchantFirstTypeIds;
    }

    public void setMerchantFirstTypeIds(Long merchantFirstTypeIds) {
        this.merchantFirstTypeIds = merchantFirstTypeIds;
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


	public Byte getStatusLevel() {
		return statusLevel;
	}

	public void setStatusLevel(Byte statusLevel) {
		this.statusLevel = statusLevel;
	}

}