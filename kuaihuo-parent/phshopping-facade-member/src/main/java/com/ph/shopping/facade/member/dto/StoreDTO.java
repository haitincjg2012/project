package com.ph.shopping.facade.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ph.shopping.common.core.base.BaseValidate;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by wudi on 2017/9/26.
 */
public class StoreDTO extends BaseValidate implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 5110459345315317271L;

    /** 主键 */
    private Long id;

    /** 代理级别id */
    @NotNull(message = "代理级别不能为空")
    private Long agentLevelId;

    /** 代理名称 */
    @NotBlank(message = "代理名称不能为空")
    private String agentName;

    /** 联系人 */
    @NotBlank(message = "联系人不能为空")
    private String personName;

    /** 联系电话 */
    @NotBlank(message = "联系电话不能为空")
    private String telPhone;

    /** 合伙人数 */
    private Integer partnersNumber;

    /** 管辖省id */
    @NotNull(message = "省不能为空")
    private Long provinceId;

    /** 管辖市id */
    @NotNull(message = "市不能为空")
    private Long cityId;

    /** 管辖区id */
    private Long countyId;

    /** 管辖所属社区id */
    private Long townId;

    /** 营业执照编号 */
    private String businessNo;

    /** 负责人身份证号码 */
    private String personIdCardNo;

    /** 管辖区域Id */
    @NotNull(message = "管辖区域不能为空")
    private Long positionId;
    /** 审核等级：0.快火代理    1. 批发代理, 4.ADMIN */
    private Byte isFormSystem;
    /**
     * 联系人地址区域id
     */
    @NotNull(message = "联系地址人不能为空")
    private Long contactPositionId;

    /** 联系区域详细地址 */
    private String address;

    /** 审核状态：0：待审核，1：审核通过，2：被驳回 */
    private Byte status;

    /** 审核等级：1.市级代理    2. 县级代理, 4.ADMIN */
    @NotNull(message = "审核等级不能为空")
    private Byte statusLevel;

    /** 父id */
    private Long parentId;

    /** 推广师Id */
    private Long promoterId;

    /** 创建时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    /** 修改时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
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
    /** 上级代理2017-5-27@何文浪 */
    private String parentName;
    /** 扩展区域id2017-5-27@何文浪 */
    private Long positionExtendsId;

    /** 扩展父级id2017-6-1@何文浪 */
    private List<Long> parentIds;
    /** 扩展父级id2017-6-1@何文浪 */
    private List<Long> parentExtendsIds;
    /** 扩展是否冻结2017-6-1@何文浪 */
    private List<Long> isFrozens;
    /** 扩展id20176-15@何文浪 */
    private List<Long> ids;
    //扩展代理图片表 -- 2017-6-5 熊克文

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

    public Byte getIsFormSystem() {
        return isFormSystem;
    }

    public void setIsFormSystem(Byte isFormSystem) {
        this.isFormSystem = isFormSystem;
    }

    public Long getContactPositionId() {
        return contactPositionId;
    }

    public void setContactPositionId(Long contactPositionId) {
        this.contactPositionId = contactPositionId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Long getPositionExtendsId() {
        return positionExtendsId;
    }

    public void setPositionExtendsId(Long positionExtendsId) {
        this.positionExtendsId = positionExtendsId;
    }

    public List<Long> getParentIds() {
        return parentIds;
    }

    public void setParentIds(List<Long> parentIds) {
        this.parentIds = parentIds;
    }

    public List<Long> getParentExtendsIds() {
        return parentExtendsIds;
    }

    public void setParentExtendsIds(List<Long> parentExtendsIds) {
        this.parentExtendsIds = parentExtendsIds;
    }

    public List<Long> getIsFrozens() {
        return isFrozens;
    }

    public void setIsFrozens(List<Long> isFrozens) {
        this.isFrozens = isFrozens;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public List<StoreImageDTO> getAgentImageDTOList() {
        return agentImageDTOList;
    }

    public void setAgentImageDTOList(List<StoreImageDTO> agentImageDTOList) {
        this.agentImageDTOList = agentImageDTOList;
    }

    private List<StoreImageDTO> agentImageDTOList;



}
