/**  
 * @Title:  OrderVO.java   
 * @Package com.phshopping.api.controller.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月19日 下午2:49:09   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.phshopping.api.controller.vo;

import java.io.Serializable;

/**   
 * @ClassName:  OrderVO   
 * @Description:订单返回相关数据   
 * @author: 李杰
 * @date:   2017年6月19日 下午2:49:09     
 * @Copyright: 2017
 */
public class OrderVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -6771732542977387719L;
	/**
	 * 订单ID
	 */
	private Long id;
	/**
	 * 订单编号
	 */
	private String orderNo;
	/**
	 * 下单时间
	 */
	private String createTime;
	/**
	 * 商户名称
	 */
	private String merchantName;
	/**
	 * 订单金额
	 */
	private String payMoney;
	/**
	 * 订单状态
	 */
	private Byte status;
	/**
	 * 商户手机号
	 */
	private String merchantTel;
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMerchantTel() {
		return merchantTel;
	}
	public void setMerchantTel(String merchantTel) {
		this.merchantTel = merchantTel;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
