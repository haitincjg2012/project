package com.ph.shopping.facade.profit.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 供应链分润总表实体
* @ClassName: UserProfitTotal
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年7月4日 下午1:54:33
 */
public class SupplyProfitTotal implements Serializable{

	private static final long serialVersionUID = -3591869753545162201L;

	private Long id;//主键
	
	private Long   userId;//用户id
	
	private Byte userType;//人员类型
	
	private String userName;//供应链企业名称
	
	private Long profited;//已经分润
	
	private Date createTime;//创建时间
	
	private String  remark;//备注

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getProfited() {
		return profited;
	}

	public void setProfited(Long profited) {
		this.profited = profited;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
}
