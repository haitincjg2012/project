package com.ph.shopping.facade.score.request;

import com.ph.shopping.common.core.customenum.TransCodeEnum;
/**
 * 
* @ClassName: ScoreRequest
* @Description: 积分模块请求实体
* @author 王强
* @date 2017年4月24日 下午4:10:22
 */
public class ScoreDTO {
	
	private long memberId;//会员ID
	
	private String orderNo = "";//订单号
	
	private double score;//积分

	private TransCodeEnum codeEnum;//交易码枚举
	
	private double handingCharge;//手续费
	
	public TransCodeEnum getCodeEnum() {
		return codeEnum;
	}
	public long getMemberId() {
		return memberId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setCodeEnum(TransCodeEnum codeEnum) {
		this.codeEnum = codeEnum;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public double getHandingCharge() {
		return handingCharge;
	}
	public void setHandingCharge(double handingCharge) {
		this.handingCharge = handingCharge;
	}
}
