package com.ph.shopping.facade.spm.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ph.shopping.common.core.base.BaseEntity;

/**
 * 
 * @ClassName: UserBalance
 * @Description: 用户余额实体
 * @author 王强
 * @date 2017年5月26日 上午8:56:57
 */
@Table(name = "ph_user_balance")
public class UserBalance extends BaseEntity {
	private static final long serialVersionUID = 4253984333480285019L;
	private Long score;// 商户余额
	@Column(name = "userId")
	private Long userId;// 账户id(商户 供应商 代理商)
	@Column(name = "userType")
	private Integer userType;// 类型 人员类型

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}
}
