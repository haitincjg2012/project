package com.ph.shopping.facade.spm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @项目：phshopping-facade-spm
 * @描述：供应商、商户、代理商审核流水信息表
 * @作者 何文浪
 * @时间：2017-5-22
 * @version: 2.1
 */
@Table(name = "ph_spmwater")
public class Spmwater implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3409290666390969532L;

	/** 流水id */
	@Id
    private Long id;
	
	/** 关联id */
	@Column(name="associationId")
	private Long associationId;

    /** 操作人id */
	@Column(name="operatId")
    private Long operatId;

    /** 操作时间 */
	@Column(name="operatTime")
    private Date operatTime;

    /** 流水记录类型1.代理商2.供应商3.商户 */
	@Column(name="type")
    private Byte type;

    /** 代理商类型0.管理员1.市级2.县级3.社区 */
	@Column(name="agentType")
    private Byte agentType;

    /** 操作详情 */
	@Column(name="operatDetail")
    private String operatDetail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssociationId() {
		return associationId;
	}

	public void setAssociationId(Long associationId) {
		this.associationId = associationId;
	}

	public Long getOperatId() {
        return operatId;
    }

    public void setOperatId(Long operatId) {
        this.operatId = operatId;
    }

    public Date getOperatTime() {
        return operatTime;
    }

    public void setOperatTime(Date operatTime) {
        this.operatTime = operatTime;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getAgentType() {
        return agentType;
    }

    public void setAgentType(Byte agentType) {
        this.agentType = agentType;
    }

    public String getOperatDetail() {
        return operatDetail;
    }

    public void setOperatDetail(String operatDetail) {
        this.operatDetail = operatDetail;
    }
}