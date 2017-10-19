package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;

/**
 * 
* @ClassName: BackDrawCashVO
* @Description: 提现退款数据
* @author 王强
* @date 2017年6月24日 下午8:55:22
 */
public class BackUserDrawCashVO implements Serializable {

	private static final long serialVersionUID = -2469965653746324035L;

	private Long userId; //用户id
	private Byte userType;//用户角色
	private Long score;//金额
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Byte getUserType() {
		return userType;
	}
	public void setUserType(Byte userType) {
		this.userType = userType;
	}
	public Long getScore() {
		return score;
	}
	public void setScore(Long score) {
		this.score = score;
	}
	
}
