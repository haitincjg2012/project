package com.ph.shopping.facade.profit.dto;

import java.io.Serializable;

/**
 * 
* @ClassName: OnlineOrderProfitDTO
* @Description: 线上订单分润DTO
* @author Mr.Dong
* @date 2017年5月9日 下午4:01:36
 */
public class OnlineOrderProfitDTO implements Serializable  {

	private static final long serialVersionUID = -5027174534815956474L;
	
	private String orderNo;//订单号
	
	private String startOrderTime;//订单开始时间
	
	private String stopOrderTime;//订单结束时间
	
	private String startProfitTime;//订单开始分润时间
	
	private String stopProfitTime;//订单结束分润时间

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
