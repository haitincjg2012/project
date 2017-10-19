package com.ph.shopping.facade.profit.dto;

import java.io.Serializable;

/**
 * 
 * @ClassName: AuditDTO
 * @Description: 审核DTO
 * @author 王强
 * @date 2017年6月13日 下午2:34:28
 */
public class AuditDTO implements Serializable {

	private static final long serialVersionUID = 8853541232141772520L;

	private Long drawCashId;// 提现Id

	private Integer auditState; // 审核状态

	private Integer type;// 类型1通过操作，2不通过操作

	private long auditRgint;// 审核权
	
	private long userId;//用户id
	
	private byte userType;//用户角色
	
	private String orderNo;//订单号

	public Long getDrawCashId() {
		return drawCashId;
	}

	public void setDrawCashId(Long drawCashId) {
		this.drawCashId = drawCashId;
	}

	public Integer getAuditState() {
		return auditState;
	}

	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public long getAuditRgint() {
		return auditRgint;
	}

	public void setAuditRgint(long auditRgint) {
		this.auditRgint = auditRgint;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public byte getUserType() {
		return userType;
	}

	public void setUserType(byte userType) {
		this.userType = userType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
