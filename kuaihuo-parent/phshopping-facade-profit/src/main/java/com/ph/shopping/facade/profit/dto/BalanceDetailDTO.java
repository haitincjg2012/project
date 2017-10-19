package com.ph.shopping.facade.profit.dto;

import com.ph.shopping.common.util.page.PageBean;

/**
 * 
 * @ClassName: BalanceDetailDTO
 * @Description: 余额明细DTO
 * @author 王强
 * @date 2017年6月8日 下午2:16:44
 */
public class BalanceDetailDTO extends PageBean {

	private static final long serialVersionUID = -5687068174520403825L;

	private Integer enterpriseType;// 企业类型（2供应商，3市级代理，4县级代理，5社区代理，6商户）
	private Integer transCode;// 交易码(来源)
	private String telPhone;// 供链账号
	private String orderNo;// 订单号
	private String startDate;// 开始日期
	private String endDate;// 结束日期
	private Long userId;//用户id

	public Integer getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(Integer enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public Integer getTransCode() {
		return transCode;
	}

	public void setTransCode(Integer transCode) {
		this.transCode = transCode;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
