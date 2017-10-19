package com.ph.shopping.facade.profit.dto;

import com.ph.shopping.common.util.page.PageBean;

/**
 * 
* @ClassName: MemberDrawCashDTO
* @Description: 会员提现DTO
* @author 王强
* @date 2017年6月14日 上午10:05:24
 */
public class MemberDrawCashDTO extends PageBean {

	private static final long serialVersionUID = -4370574658634752758L;
	
	private String telPhone;//会员账号
	
	private Integer memberType;//会员类型
	
	private Integer auditState;//审核状态
	
	private Integer status;//提现状态
	
	private String orderNo;//订单号
	
	private String startDate;//提交开始时间
	
	private String endDate;//提交结束时间

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
