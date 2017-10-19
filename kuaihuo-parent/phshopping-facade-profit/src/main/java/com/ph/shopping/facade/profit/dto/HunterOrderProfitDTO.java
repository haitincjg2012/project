package com.ph.shopping.facade.profit.dto;

import java.io.Serializable;

/**
 * 
* @ClassName: HunterOrderProfitDTO
* @Description: TODO(批发商订单分润DTO)
* @author Mr.Dong
* @date 2017年6月1日 下午5:35:27
 */
public class HunterOrderProfitDTO implements Serializable  {

	private static final long serialVersionUID = 1274021087380247913L;

	private String orderNo;//订单号
	
	private String startProfitTime;//订单开始分润时间
	
	private String stopProfitTime;//订单结束分润时间

	private String startOrderTime;//创建订单开始的时间

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

	private String stopOrderTime;//创建订单结束时间

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
