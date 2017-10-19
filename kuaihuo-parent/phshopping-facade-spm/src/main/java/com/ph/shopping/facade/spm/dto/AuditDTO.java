package com.ph.shopping.facade.spm.dto;

import java.io.Serializable;

public class AuditDTO implements Serializable {
	private static final long serialVersionUID = -3797361834114462221L;
	private Long id;//提现记录id
	private Integer auditState;//审核状态
	private Long updaterId;//更新人id
	private Long operatorCheckId;//运营审核人id
	private Long treasurerCheckId;//财务审核人id
	private Byte roleCode;
	public Byte getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(Byte roleCode) {
		this.roleCode = roleCode;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getAuditState() {
		return auditState;
	}
	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}
	public Long getUpdaterId() {
		return updaterId;
	}
	public void setUpdaterId(Long updaterId) {
		this.updaterId = updaterId;
	}
	public Long getOperatorCheckId() {
		return operatorCheckId;
	}
	public void setOperatorCheckId(Long operatorCheckId) {
		this.operatorCheckId = operatorCheckId;
	}
	public Long getTreasurerCheckId() {
		return treasurerCheckId;
	}
	public void setTreasurerCheckId(Long treasurerCheckId) {
		this.treasurerCheckId = treasurerCheckId;
	}
}
