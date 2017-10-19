package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
/**
 * 
* @ClassName: UserBalanceRecordVO
* @Description: 用户余额流水VO
* @author Mr.Dong
* @date 2017年5月5日 下午5:01:30
 */
public class UserBalanceRecordVO implements Serializable {
	private static final long serialVersionUID = 7766498070596808710L;

	private Long userId;//用户id
	private Long money;//分润金额
	private String orderNo;//订单号
	private String transCode;//码
	private String userType;//用户类型    用户角色(2供应商，3市级代理商，4县级代理商，5社区代理商，6商户)
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getMoney() {
		return money;
	}
	public void setMoney(Long money) {
		this.money = money;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
}
