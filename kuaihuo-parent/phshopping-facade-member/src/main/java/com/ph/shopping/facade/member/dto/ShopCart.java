
package com.ph.shopping.facade.member.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Date:     2017年2月27日 下午6:52:00 <br/>
 * @author   chen
 * @version  购物车
 * @since    JDK 1.8
 * @see 	 
 */
public class ShopCart implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1321597620742554113L;
	private Long id;
	private Long dishId;//商品
	private Long memberId;//会员
	private Long buyNum;//购买数量
	private Long merchantId;//商户
	private Long type;//类型  0、菜品  1、包间
	private Date createdTime;//
	private Date updateTime;//
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDishId() {
		return dishId;
	}
	public void setDishId(Long dishId) {
		this.dishId = dishId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(Long buyNum) {
		this.buyNum = buyNum;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}

