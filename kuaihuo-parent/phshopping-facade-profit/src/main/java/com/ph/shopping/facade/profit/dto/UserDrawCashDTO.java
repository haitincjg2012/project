package com.ph.shopping.facade.profit.dto;

import com.ph.shopping.common.util.page.PageBean;

/**
 * 
 * @ClassName: UserDrawCashDTO
 * @Description: 供链提现DTO
 * @author 王强
 * @date 2017年6月12日 上午10:44:40
 */
public class UserDrawCashDTO extends PageBean {

	private static final long serialVersionUID = -462127095186088417L;

	private String startDate;// 提交时间
	private String endDate;// 提交时间
	private String orderNo;// 订单号
	private String enterpriseName;// 供链名称
	private String telPhone;// 供链账号
	private Integer auditState;// 审核状态
	private Integer status;// 提现状态

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public Integer getAuditState() {
		return auditState;
	}

	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
