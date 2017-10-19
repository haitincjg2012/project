package com.ph.shopping.facade.score.vo;

import java.io.Serializable;
/**
 * 
* @ClassName: MemberCostScore
* @Description: 查询会员订单返回数据vo
* @author 王强
* @date 2017年4月24日 下午4:08:32
 */
public class MemberCostScoreVO implements Serializable {
	private static final long serialVersionUID = -3399049694639820860L;
	private long payMoney; //订单金额
	private long memberId; //会员ID
	private String orderNo; //订单号
	public long getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(long payMoney) {
		this.payMoney = payMoney;
	}
	public long getMemberId() {
		return memberId;
	}
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	} 

}
