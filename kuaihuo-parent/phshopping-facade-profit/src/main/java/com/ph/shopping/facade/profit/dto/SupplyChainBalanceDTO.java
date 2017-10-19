package com.ph.shopping.facade.profit.dto;

import com.ph.shopping.common.util.page.PageBean;

/**
 * 
* @ClassName: SupplyChainBalanceDTO
* @Description: 供链余额DTO
* @author 王强
* @date 2017年6月9日 下午12:16:40
 */
public class SupplyChainBalanceDTO extends PageBean {

	private static final long serialVersionUID = 4913404342682959337L;
	
	private String telPhone;//供链账号
	private Integer enterpriseType;//供链类型
	private Integer status;//状态
	private String enterpriseName;// 供链名称
	private String createTime;//创建时间


	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public Integer getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(Integer enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
