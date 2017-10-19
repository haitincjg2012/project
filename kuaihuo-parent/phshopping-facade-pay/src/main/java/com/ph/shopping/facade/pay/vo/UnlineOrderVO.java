package com.ph.shopping.facade.pay.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 项目名称：phshopping-facade-pay    
 * 类名称：UnlineOrderVO 支付订单交易实体
 * 类描述：    
 * 创建人：王雪洋    
 * 创建时间：2017年8月27日 上午11:59:57
 * 备注：订单下包含的商品暂不展示 （供以后需求变动） 
 */
public class UnlineOrderVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	 /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 订单金额
     */
    private Long orderMoney;
    /**
     * 交易订单包含的商品
     */
    private List<OrderUnlineRemarks> plist;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Long getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(Long orderMoney) {
		this.orderMoney = orderMoney;
	}
	public List<OrderUnlineRemarks> getPlist() {
		return plist;
	}
	public void setPlist(List<OrderUnlineRemarks> plist) {
		this.plist = plist;
	}
}
