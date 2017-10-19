package com.ph.shopping.facade.spm.dto;

import com.ph.shopping.common.util.page.PageBean;
/**
 * 
* @ClassName: UserBalanceTradeDTO
* @Description: 用户余额流水DTO
* @author 王强
* @date 2017年6月2日 上午11:48:12
 */
public class UserBalanceTradeDTO extends PageBean {

	private static final long serialVersionUID = -7078197390361993734L;
	
	private Long userId;//用户id
	
	private String startDate;//开始日期
	
	private String endDate;//结束日期
	
	private Integer transCode;//交易码

	private String orderNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getTransCode() {
		return transCode;
	}

	public void setTransCode(Integer transCode) {
		this.transCode = transCode;
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
