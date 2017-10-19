package com.ph.shopping.facade.profit.dto;

import java.io.Serializable;

/**
* @ClassName: UnLineOrderProfitDTO
* @Description: 线下订单DTO
* @author Mr.Dong
* @date 2017年5月22日 下午6:06:59
 */
public class UnLineOrderProfitDTO implements Serializable {
	
	private static final long serialVersionUID = -5607199338981163656L;

	private Long id;//线下订单分润表主键id
	
	private String orderNo;//订单号
	
	private String startOrderTime;//订单开始时间
	
	private String stopOrderTime;//订单结束时间
	
	private String startProfitTime;//订单开始分润时间
	
	private String stopProfitTime;//订单结束分润时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getStartOrderTime() {
		return startOrderTime;
	}

	public void setStartOrderTime(String startOrderTime) {
		this.startOrderTime = startOrderTime;
	}

	public String getStopOrderTime() {
		return stopOrderTime;
	}

	public void setStopOrderTime(String stopOrderTime) {
		this.stopOrderTime = stopOrderTime;
	}

	public String getStartProfitTime() {
		return startProfitTime;
	}

	public void setStartProfitTime(String startProfitTime) {
		this.startProfitTime = startProfitTime;
	}

	public String getStopProfitTime() {
		return stopProfitTime;
	}

	public void setStopProfitTime(String stopProfitTime) {
		this.stopProfitTime = stopProfitTime;
	}

	
}
