package com.ph.shopping.facade.spm.entity;

import com.ph.shopping.common.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;
/**
 * @项目：phshopping-facade-spm
 * @描述：代理商信息表
 * @作者 何文浪
 * @时间：2017-5-15
 * @version: 2.1
 */
@Table(name = "ph_agent")
public class Agent extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2939870156696790828L;

    /** 代理级别id */
	@Column(name ="agentLevelId" )
    private Long agentLevelId;

    /** 代理名称 */
	@Column(name ="agentName" )
    private String agentName;

    /** 联系人 */
	@Column(name ="personName" )
    private String personName;

    /** 联系电话 */
	@Column(name ="telPhone" )
    private String telPhone;

    /** 合伙人数 */
	@Column(name ="partnersNumber" )
    private Integer partnersNumber;

    /** 管辖省id */
	@Column(name ="provinceId" )
    private Long provinceId;

    /** 管辖市id */
	@Column(name ="cityId" )
    private Long cityId;

    /** 管辖区id */
	@Column(name ="countyId" )
    private Long countyId;

    /** 管辖所属社区id */
	@Column(name ="townId" )
    private Long townId;

    /** 营业执照编号 */
	@Column(name ="businessNo" )
    private String businessNo;

    /** 负责人身份证号码 */
	@Column(name ="personIdCardNo" )
    private String personIdCardNo;

    /** 管辖区域Id */
	@Column(name ="positionId" )
    private Long positionId;

    /** 联系地址区域id */
	@Column(name ="contactPositionId" )
    private Long contactPositionId;

    /** 联系区域详细地址 */
    @Column(name ="address" )
    private String address;

    /** 审核状态：0：待审核，1：审核通过，2：被驳回 */
    @Column(name ="status" )
    private Byte status;
    
    /** 审核等级：1.市级代理    2. 县级代理, 4.ADMIN */
   	@Column(name ="statusLevel" )
   private Byte statusLevel;

    /** 父id */
    @Column(name ="parentId" )
    private Long parentId;

    /** 推广师Id */
    @Column(name ="promoterId" )
    private Long promoterId;
    
    /** 是否删除 0:否 1：是 */
    @Column(name ="isDelete" )
    private Byte isDelete;

    /** 是否冻结 0:否 1:是2.暂冻 */
    @Column(name ="isFrozen" )
    private Byte isFrozen;
    
    /** 对应登录表主键id */
    @Column(name ="userId" )
    private Long userId;

    /**标示位  0 快火代理  1 批发代理*/
    @Column(name = "isFormSystem")
    private Byte isFormSystem;

    public Byte getIsFormSystem() {
        return isFormSystem;
    }

    public void setIsFormSystem(Byte isFormSystem) {
        this.isFormSystem = isFormSystem;
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