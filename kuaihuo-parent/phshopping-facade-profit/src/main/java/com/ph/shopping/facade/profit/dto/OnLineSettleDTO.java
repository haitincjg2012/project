package com.ph.shopping.facade.profit.dto;

import com.ph.shopping.common.util.page.PageBean;

/**
 * 
 * @ClassName: OnLineSettleDTO
 * @Description: 线上结算DTO
 * @author 王强
 * @date 2017年6月6日 下午3:34:07
 */
public class OnLineSettleDTO extends PageBean {

	private static final long serialVersionUID = 665039703787125012L;

	private String startDate;// 开始日期
	private String endDate;// 结束日期
	private String orderNo;// 订单号
	private String telPhone;// 会员账号
	private Integer status;// 结算状态0未结算，1已结算，2查询全部,3定时器执行需要条件
	private Integer type;//1导出，2查询(默认)

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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
