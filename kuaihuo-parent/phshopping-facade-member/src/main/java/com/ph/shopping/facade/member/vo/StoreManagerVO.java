package com.ph.shopping.facade.member.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by wudi on 2017/9/26.
 */
public class StoreManagerVO implements Serializable{
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAgentLevelId() {
        return agentLevelId;
    }

    public void setAgentLevelId(Long agentLevelId) {
        this.agentLevelId = agentLevelId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
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

    public Integer getPartnersNumber() {
        return partnersNumber;
    }

    public void setPartnersNumber(Integer partnersNumber) {
        this.partnersNumber = partnersNumber;
    }

    public Long getContactProvinceId() {
        return contactProvinceId;
    }

    public void setContactProvinceId(Long contactProvinceId) {
        this.contactProvinceId = contactProvinceId;
    }

    public Long getContactCityId() {
        return contactCityId;
    }

    public void setContactCityId(Long contactCityId) {
        this.contactCityId = contactCityId;
    }

    public Long getContactCountyId() {
        return contactCountyId;
    }

    public void setContactCountyId(Long contactCountyId) {
        this.contactCountyId = contactCountyId;
    }

    public String getPromoterTelPhone() {
        return promoterTelPhone;
    }

    public void setPromoterTelPhone(String promoterTelPhone) {
        this.promoterTelPhone = promoterTelPhone;
    }

    public String getPromoterMemberName() {
        return promoterMemberName;
    }

    public void setPromoterMemberName(String promoterMemberName) {
        this.promoterMemberName = promoterMemberName;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getPersonIdCardNo() {
        return personIdCardNo;
    }

    public void setPersonIdCardNo(String personIdCardNo) {
        this.personIdCardNo = personIdCardNo;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Integer getContactPositionId() {
        return contactPositionId;
    }

    public void setContactPositionId(Integer contactPositionId) {
        this.contactPositionId = contactPositionId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Byte getStatusLevel() {
        return statusLevel;
    }

    public void setStatusLevel(Byte statusLevel) {
        this.statusLevel = statusLevel;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getPromoterId() {
        return promoterId;
    }

    public void setPromoterId(Long promoterId) {
        this.promoterId = promoterId;
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

    public List<StoreMangerImageVO> getStoreImageVOList() {
        return storeImageVOList;
    }

    public void setStoreImageVOList(List<StoreMangerImageVO> storeImageVOList) {
        this.storeImageVOList = storeImageVOList;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
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

    public Byte getIsFormSystem() {
        return isFormSystem;
    }

    public void setIsFormSystem(Byte isFormSystem) {
        this.isFormSystem = isFormSystem;
    }

    /**
     *

     */
    private static final long serialVersionUID = 5110459345315317271L;

    /** 主键 */
    private Long id;

    /** 代理级别id */
    private Long agentLevelId;

    /** 代理名称 */
    private String agentName;

    /** 联系人 */
    private String personName;

    /** 联系电话 */
    private String telPhone;

    /** 合伙人数 */
    private Integer partnersNumber;

    /**
     * 联系人省id
     */
    private Long contactProvinceId;

    /**
     * 联系人市id
     */
    private Long contactCityId;

    /**
     * 联系人区id
     */
    private Long contactCountyId;

    /**
     * 推广师电话
     */
    private String promoterTelPhone;

    /**
     * 推广师刑姓名
     */
    private String promoterMemberName;

    /**
     * 营业执照编号
     */
    private String businessNo;

    /** 负责人身份证号码 */
    private String personIdCardNo;

    /** 管辖区域Id */
    private Long positionId;

    /** 联系地址区域id */
    private Integer contactPositionId;

    /** 联系区域详细地址 */
    private String address;

    /** 审核状态：0：待审核，1：审核通过，2：被驳回 */
    private Integer status;
    /** 审核等级：1.市级代理    2. 县级代理, 4.ADMIN */
    private Byte statusLevel;

    /** 父id */
    private Long parentId;

    /** 推广师Id */
    private Long promoterId;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

    /** 创建人Id */
    private Long createrId;

    /** 修改人Id */
    private Long updaterId;

    /** 是否删除 0:否 1：是 */
    private Byte isDelete;

    /** 是否冻结 0:否 1:是 2.暂冻*/
    private Byte isFrozen;

    /** 对应登录表主键id */
    private Long userId;

    /**代理商图片-2017-5-15@author 何文浪添加*/
    private List<StoreMangerImageVO> storeImageVOList;
    /**代理商地址-2017-5-25@author 何文浪添加*/
    private String addressName;
    /**代理商父级名称-2017-5-25@author 何文浪添加*/
    private String parentName;
    /**代理级别名称-2017-5-25@author 何文浪添加*/
    private String levelName;
    /**状态 2017-6-26导出列表添加*/
    private String statusName;
    /**冻结状态 2017-6-26导出列表添加*/
    private String isFrozenName;
    /**标示位  0 快火代理  1 批发代理*/
    private Byte isFormSystem;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    /**公司名称*/
    private String merchantName;

    public Byte getCertification() {
        return certification;
    }

    public void setCertification(Byte certification) {
        this.certification = certification;
    }

    /**正在审核为0，审核通过是1，审核未通过是2*/
    private Byte certification;
    /**代理经理名称*/
    private String memberName;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
