package com.ph.shopping.facade.spm.dto;

import java.io.Serializable;

/**
 * 
 * @ClassName: UserChargeRecordDTO
 * @Description: 充值记录DTO
 * @author 王强
 * @date 2017年5月16日 下午5:58:17
 */
public class UserChargeDTO implements Serializable {
	private static final long serialVersionUID = -7419161215415990447L;
	private Long userId;// 用户id
	private Double score;// 充值金额
	private Byte userType;// 用户角色 2供应商3市级代理4县级代理5社区代理6商户

	public Long getUserId() {
		return userId;
	}

	public Byte getUserType() {
		return userType;
	}

	public void setUserType(Byte userType) {
		this.userType = userType;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

}
