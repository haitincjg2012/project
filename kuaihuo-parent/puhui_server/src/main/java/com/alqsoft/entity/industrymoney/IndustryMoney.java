
package com.alqsoft.entity.industrymoney;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.industryassociation.IndustryAssociation;
import com.alqsoft.entity.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author cjd
 * @tiem 2017年3月9日上午11:33:13
 */
@Entity
@Table(name = "alq_industry_money", indexes = {})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class IndustryMoney extends IdEntity{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IndustryAssociation industryAssociation; 
	
	private Long fee;//手续费
	
	private Integer type;//0分润    1提现  
	
	private Long money;//金额
	
	private String orderNo;//订单号
	
	private String orderSubNo;//支付时候  分润时候 需要维护子订单号 
	
	private Long afterMoney;//变动后金额
	
	private Long beforeMoney;//变动前金额
	
	private Integer status;//状态 0提现申请中  1提现成功   2提现失败
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

  
	public Long getFee() {
		return fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

	public Long getAfterMoney() {
		return afterMoney;
	}

	public void setAfterMoney(Long afterMoney) {
		this.afterMoney = afterMoney;
	}

	public Long getBeforeMoney() {
		return beforeMoney;
	}

	public void setBeforeMoney(Long beforeMoney) {
		this.beforeMoney = beforeMoney;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderSubNo() {
		return orderSubNo;
	}

	public void setOrderSubNo(String orderSubNo) {
		this.orderSubNo = orderSubNo;
	}
	
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "industry_id")
	public IndustryAssociation getIndustryAssociation() {
		return industryAssociation;
	}

	public void setIndustryAssociation(IndustryAssociation industryAssociation) {
		this.industryAssociation = industryAssociation;
	}
	
}

