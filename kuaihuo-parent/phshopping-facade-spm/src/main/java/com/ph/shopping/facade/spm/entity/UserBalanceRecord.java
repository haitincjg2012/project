package com.ph.shopping.facade.spm.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ph.shopping.common.core.base.BaseEntity;

/**
 * 
 * @ClassName: UserBalanceRecord
 * @Description: 余额记录实体
 * @author 王强
 * @date 2017年5月26日 上午8:59:25
 */
@Table(name = "ph_user_balance_trade")
public class UserBalanceRecord extends BaseEntity {

	private static final long serialVersionUID = 5406243254407240226L;
	@Column(name = "userId")
	private Long userId;// 用户id
	@Column(name = "money")
	private Long score;// 金额
	@Column(name = "userType")
	private Byte userType;// 用户角色(2供应商，3市级代理商，4县级代理商，5社区代理商，6商户)
	@Column(name = "transCode")
	private Integer transCode;// 交易码
	@Column(name = "orderNo")
	private String orderNo;// 订单号
	@Column(name = "handingCharge")
	private Long handingCharge;// 手续费

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

	public Integer getTransCode() {
		return transCode;
	}

	public void setTransCode(Integer transCode) {
		this.transCode = transCode;
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

}
