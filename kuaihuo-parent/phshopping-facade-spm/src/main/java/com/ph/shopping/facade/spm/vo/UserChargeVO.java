package com.ph.shopping.facade.spm.vo;

import java.io.Serializable;

/**
 * 
* @ClassName: UserChargeVO
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 王强
* @date 2017年6月15日 下午3:14:07
 */
public class UserChargeVO implements Serializable {

	private static final long serialVersionUID = 5739743857281572769L;
	
	private long score;//充值金额
	
	private long userId;//用户id
	
	private byte userType;// 用户角色

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public byte getUserType() {
		return userType;
	}

	public void setUserType(byte userType) {
		this.userType = userType;
	}

}
