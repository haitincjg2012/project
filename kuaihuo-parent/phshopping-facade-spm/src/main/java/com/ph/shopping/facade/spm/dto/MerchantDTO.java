package com.ph.shopping.facade.spm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ph.shopping.common.core.base.BaseValidate;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
public class MerchantDTO extends BaseValidate implements Serializable {
    private static final long serialVersionUID = -1256225580343841098L;

    /**
     * 表流水
     */
    private Long id;

    @NotBlank(message = "门店名称不能为空")
    /** 店铺的名称 */
    private String merchantName;

    @NotBlank(message = "商户账号不能为空")
    /** 商户电话 */
    private String telPhone;

    @NotBlank(message = " 联系人不能为空")
    /** 联系人名称 */
    private String personName;

    @NotBlank(message = " 详细地址不能为空")
    /** 门店详细地址 */
    private String address;

    /**
     * 联系人地址
     */
    private String contactAddress;

    private Long level;

    /**
     * 商户类别名称
     */
    private String merchantTypeName;

    /**
     * 所属代理Id  如果由平台添加默认为 0
     */
    private Long agentId;

    /**
     * 营业执照编号
     */
    private String businessNo;

    @NotBlank(message = "企业名称不能为空")
    /** 企业名称 */
    private String companyName;

    /**
     * 身份证号码
     */
    private String idCardNo;


    /**
     * 密码
     */
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @DecimalMin(value = "0.15", message = "最小值为0.15")
    @DecimalMax(value = "0.39", message = "最大值为0.39")
    @NotNull(message = "管理费用比例不能为空")
    /** 商户分润比率 */
    private BigDecimal businessProfitRatio;

    /**
     * 联系人省
     */
    private Long provinceId;

    /**
     * 联系人市
     */
    private Long cityId;

    /**
     * 区县id
     */
    private Long countyId;

    @NotNull(message = "社区不能为空")
    /** 联系人镇的id */
    private Long townId;

    @NotNull(message = "门店地址不能为空")
    /** ph_position表中的主键id 门店地址 */
    private Long positionId;

    /**
     * 推广师Id
     */
    private Long promoterId;

    /**
     * 0：审核中 1：审核通过 2：未通过 （原来对应值：审核未通过0  审核未通过2  审核通过正常1 冻结  3）
     */
    private Byte status;

    /**
     * 审核等级：1.市级代理    2. 县级代理, 4.ADMIN
     */
    @NotNull(message = "审核等级不能为空")
    private Byte statusLevel;

    /**
     * 是否冻结 0:否 1:是
     */
    private Byte isFrozen;

    /**
     * 是否删除 0:否 1：是
     */
    private Byte isDelete;

    /**
     * 对应登录表主键id
     */
    private Long userId;

    /**
     * 创建人id
     */
    private Long createrId;

    /**
     * 修改人
     */
    private Long updaterId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date updateTime;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    private String description;//商户介绍


    private Integer isRecommend;//是否置顶   0、未置顶  1、已置顶

    private Integer isRecommendType;//置顶级别  0、代理置顶  1、运营置顶

    /**
     * 商户类别一级ID
     */
    private Long merchantFirstTypeId;

    /**
     * 商户类别二级ID
     */
    private Long merchantSecondTypeId;

    /**
     * 商户类别ID
     */
    private Long merchantTypeId;

    /**
     * 排序类型
     */
    private String sort;

    //扩展商户图片表 -- 2017-5-12何文浪
    //  @NotEmpty(message = "商户图片不能为空")
    private List<MerchantImageDTO> merchantImageDTOList;
    //扩展商户类别挂靠表
    //  @NotEmpty(message = "商户类别不能为空")
    private List<MerchantMerchantTypeDTO> merchantMerchantTypeDTOList;
    //扩展代理商id -- 2017-5-26何文浪
    private List<Long> agentIds;
    //扩展商户id -- 2017-5-26何文浪
    private List<Long> ids;
    //扩展商户id 这是需要更改statusLevel -- 2017-5-26何文浪
    private List<Long> isNotLevlIds;
    //扩展距离查询 -- 2017-6-7何文浪
    private Integer distanceNum;
    /**
     * 扩展ph_position表中的主键id 门店地址
     */
    private Long positionExtendsId;
    /* 商户展示的商品种类 0 菜品 1 餐位 */
    private int type;
    /**
     * 行业分类
     */
    private List<String> merchantType;
    /**
     * 身份证图片
     */
    private List<MerchantImageDTO> idcard;
    /**
     * 营业执照照片
     */
    private List<MerchantImageDTO> yingye;
    /**
     * 门店照片
     */
    private List<MerchantImageDTO> mendian;
    /**
     * 其他照片
     */
    private List<MerchantImageDTO> other;
    /**
     * 人均消费
     */
    private Long costMoney;

    /*营业开始时间*/
    private String openBeginTime;
    /*营业结束时间*/
    private String openEndTime;
    /*非营业开始时间*/
    private String closeBeginTime;
    /*非营业关闭时间*/
    private String closeEndTime;
    /*置顶顺序*/
    private Long isRecommendSsss;


    public List<String> getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(List<String> merchantType) {
        this.merchantType = merchantType;
    }

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

    public String getCloseBeginTime() {
        return closeBeginTime;
    }

    public void setCloseBeginTime(String closeBeginTime) {
        this.closeBeginTime = closeBeginTime;
    }

    public String getCloseEndTime() {
        return closeEndTime;
    }

    public void setCloseEndTime(String closeEndTime) {
        this.closeEndTime = closeEndTime;
    }

    public List<MerchantImageDTO> getIdcard() {
        return idcard;
    }

    public void setIdcard(List<MerchantImageDTO> idcard) {
        this.idcard = idcard;
    }

    public List<MerchantImageDTO> getYingye() {
        return yingye;
    }

    public void setYingye(List<MerchantImageDTO> yingye) {
        this.yingye = yingye;
    }

    public List<MerchantImageDTO> getMendian() {
        return mendian;
    }

    public void setMendian(List<MerchantImageDTO> mendian) {
        this.mendian = mendian;
    }

    public List<MerchantImageDTO> getOther() {
        return other;
    }

    public void setOther(List<MerchantImageDTO> other) {
        this.other = other;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getMerchantTypeName() {
        return merchantTypeName;
    }

    public void setMerchantTypeName(String merchantTypeName) {
        this.merchantTypeName = merchantTypeName;
    }

    public Long getId() {
        return id;
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

    public Long getMerchantFirstTypeId() {
        return merchantFirstTypeId;
    }

    public void setMerchantFirstTypeId(Long merchantFirstTypeId) {
        this.merchantFirstTypeId = merchantFirstTypeId;
    }

    public Long getMerchantSecondTypeId() {
        return merchantSecondTypeId;
    }

    public void setMerchantSecondTypeId(Long merchantSecondTypeId) {
        this.merchantSecondTypeId = merchantSecondTypeId;
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

    public Long getMerchantTypeId() {
        return merchantTypeId;
    }

    public void setMerchantTypeId(Long merchantTypeId) {
        this.merchantTypeId = merchantTypeId;
    }

    public List<MerchantImageDTO> getMerchantImageDTOList() {
        return merchantImageDTOList;
    }

    public void setMerchantImageDTOList(List<MerchantImageDTO> merchantImageDTOList) {
        this.merchantImageDTOList = merchantImageDTOList;
    }

    public List<Long> getAgentIds() {
        return agentIds;
    }

    public void setAgentIds(List<Long> agentIds) {
        this.agentIds = agentIds;
    }

    public Long getPositionExtendsId() {
        return positionExtendsId;
    }

    public void setPositionExtendsId(Long positionExtendsId) {
        this.positionExtendsId = positionExtendsId;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Integer getDistanceNum() {
        if (distanceNum == null)
            distanceNum = 1000;
        return distanceNum;
    }

    public void setDistanceNum(Integer distanceNum) {
        this.distanceNum = distanceNum;
    }

    public Byte getStatusLevel() {
        return statusLevel;
    }

    public void setStatusLevel(Byte statusLevel) {
        this.statusLevel = statusLevel;
    }

    public List<Long> getIsNotLevlIds() {
        return isNotLevlIds;
    }

    public void setIsNotLevlIds(List<Long> isNotLevlIds) {
        this.isNotLevlIds = isNotLevlIds;
    }

    public List<MerchantMerchantTypeDTO> getMerchantMerchantTypeDTOList() {
        return merchantMerchantTypeDTOList;
    }

    public void setMerchantMerchantTypeDTOList(List<MerchantMerchantTypeDTO> merchantMerchantTypeDTOList) {
        this.merchantMerchantTypeDTOList = merchantMerchantTypeDTOList;
    }

    public Long getIsRecommendSsss() {
        return isRecommendSsss;
    }

    public void setIsRecommendSsss(Long isRecommendSsss) {
        this.isRecommendSsss = isRecommendSsss;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }
}