package com.ph.shopping.facade.pay.vo;

import java.io.Serializable;

/**
 * <pre>项目名称：phshopping-facade-pay    
 * 类名称：OrderUnlineRemarks    
 * 类描述：线下订单包含的商品备注    
 * 创建人：王雪洋    
 * 创建时间：2017年8月27日 下午3:03:48         
 * @version </pre>
 */
public class OrderUnlineRemarks implements Serializable{
	private static final long serialVersionUID = 34028392425627344L;
	private Long oid;
	private String dishName;
	private Long dCount;
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public String getDishName() {
		return dishName;
	}
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
	public Long getdCount() {
		return dCount;
	}
	public void setdCount(Long dCount) {
		this.dCount = dCount;
	}
}
