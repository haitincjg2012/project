package com.alqsoft.entity.wxpayorder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.alqsoft.entity.order.Order;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author sunhuijie
 *
 * @date 2017年3月10日
 *
 *	微信支付订单表
 * @used
 */
@Entity
@Table(name = "alq_wx_pay_order", indexes = {})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class WxPayOrder extends IdEntity{

	
	private String wxOrderNum;//订单支付订单号
	
	private Order orderInfo;//关联平台订单信息
	
	private String wxSerialNumber;//三方流水号（回调会返回改信息）
	
	private String description;//回调描述
	
	private int type;//0 订单支付 1 定金支付 2 协商价格（所有尾款）支付  3退全款（废弃，现在全部是定金支付）  4 退定金  5 退协商价格（退单个商品尾款）
	
	private int status;//0 处理中 1处理成功 2处理失败 3回调数据异常(已成功)
	
 	private Long money;//变动金额
 	
 	private Integer payType;//支付方式：0：微信，1：支付宝
 	
 	private String orderNum;//主订单号
 	
 	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getWxOrderNum() {
		return wxOrderNum;
	}

	public void setWxOrderNum(String wxOrderNum) {
		this.wxOrderNum = wxOrderNum;
	}
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	public Order getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(Order orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getWxSerialNumber() {
		return wxSerialNumber;
	}

	public void setWxSerialNumber(String wxSerialNumber) {
		this.wxSerialNumber = wxSerialNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}


	
	
}
