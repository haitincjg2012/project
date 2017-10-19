package com.ph.shopping.facade.profit.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * phshopping-facade-profit
 *
 * @description：订单分润总表实体
 *
 * @author：Mr.Dong
 *
 * @createTime：2017年5月16日
 *
 * @Copyright @2017 by Mr.Dong
 */
public class OrderProfitTotal implements Serializable {

	private static final long serialVersionUID = -4072490399461940643L;
	
	private Long id;//主键
	
	private Long  userId;//用户id
	
	private String  telPhone;//用户账号
	
	private Byte type;//人员类型
	
	private String userName;//用户名字
	
	private Long profited;//已经分润
	
	private Long profiting;//待分润
	
	private Long profitTotal;//分润总额
	
	private Date createTime;//创建时间
	
	private Date updateTime;//修改时间

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

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
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

	public Long getProfiting() {
		return profiting;
	}

	public void setProfiting(Long profiting) {
		this.profiting = profiting;
	}

	public Long getProfitTotal() {
		return profitTotal;
	}

	public void setProfitTotal(Long profitTotal) {
		this.profitTotal = profitTotal;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
	
	
	

}
