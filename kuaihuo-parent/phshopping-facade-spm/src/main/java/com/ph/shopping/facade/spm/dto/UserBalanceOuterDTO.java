package com.ph.shopping.facade.spm.dto;

import java.io.Serializable;
/**
 * 
* @ClassName: UserBalanceOuterDTO
* @Description: 外部余额调用DTO
* @author 王强
* @date 2017年6月27日 上午11:43:16
 */
public class UserBalanceOuterDTO implements Serializable {

	private static final long serialVersionUID = -7414754883922815092L;
	private Long userId; //用户id
	private Long score; //交易金额
	private String orderNo; //订单号
	private Long handingCharge; //手续费，没有就传0L
	private Byte userType;//用户角色编码
	private String transCode;//交易码
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getScore() {
		return score;
	}
	public void setScore(Long score) {
		this.score = score;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Long getHandingCharge() {
		return handingCharge;
	}
	public void setHandingCharge(Long handingCharge) {
		this.handingCharge = handingCharge;
	}
	public Byte getUserType() {
		return userType;
	}
	public void setUserType(Byte userType) {
		this.userType = userType;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	
}

