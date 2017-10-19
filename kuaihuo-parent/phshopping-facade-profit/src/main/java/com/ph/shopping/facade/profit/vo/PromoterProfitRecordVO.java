package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 推广师分润记录提供pc商城用
* @ClassName: PromoterProfitRecordVO
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月22日 下午9:12:10
 */
public class PromoterProfitRecordVO implements Serializable {

	private static final long serialVersionUID = -5325036220633255098L;

	private Long  id;//主键
	
	private String enterpriseName;//企业名称
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date deliveryDate;//下单日期
	
	private Byte enterpriseType;//企业类型  3市级代理，4县级代理，5社区代理 6商户，默认为1


	private String orderNo;//订单编号

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date profitDate;//分润日期

	
	
	
	private Double proProfit;//分润金额（单条记录）

	@JsonIgnore
	private Long proProfit1;
	

	
	private Double proProfitTotal;//分润总金额

	private Double orderMoney;//订单金额

	@JsonIgnore
	private Long orderMoney1;

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Byte getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(Byte enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getProfitDate() {
		return profitDate;
	}

	public void setProfitDate(Date profitDate) {
		this.profitDate = profitDate;
	}

	public Double getProProfit() {
		return proProfit;
	}

	public void setProProfit(Double proProfit) {
		this.proProfit = proProfit;
	}

	public Long getProProfit1() {
		return proProfit1;
	}

	public void setProProfit1(Long proProfit1) {
		this.proProfit1 = proProfit1;
	}



	public Double getProProfitTotal() {
		return proProfitTotal;
	}

	public void setProProfitTotal(Double proProfitTotal) {
		this.proProfitTotal = proProfitTotal;
	}

	public Double getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}

	public Long getOrderMoney1() {
		return orderMoney1;
	}

	public void setOrderMoney1(Long orderMoney1) {
		this.orderMoney1 = orderMoney1;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
