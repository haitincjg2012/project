
package com.alqsoft.entity.inoutdetail;

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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Date:     2017年2月27日 下午2:42:37 <br/>
 * @author   zhangcan
 * @version  收入明细
 * @since    JDK 1.8
 * @see 	 
 */
@Entity
@Table(name = "alq_in_out_detail", indexes = {})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class InOutDetail extends IdEntity{

	private String orderNO;//订单号
	
	private Long orderMoney;//订单收入
	
	private Long serviceMoney;//服务金额
	
	private Long actualMoney;//实际收入
	
	private Hunter hunter;//批发商
	
	private IndustryAssociation industryAssociation;//行业协会

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public Long getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Long orderMoney) {
		this.orderMoney = orderMoney;
	}

	public Long getServiceMoney() {
		return serviceMoney;
	}

	public void setServiceMoney(Long serviceMoney) {
		this.serviceMoney = serviceMoney;
	}

	public Long getActualMoney() {
		return actualMoney;
	}

	public void setActualMoney(Long actualMoney) {
		this.actualMoney = actualMoney;
	}
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "hunter_id")
	public Hunter getHunter() {
		return hunter;
	}

	public void setHunter(Hunter hunter) {
		this.hunter = hunter;
	}
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "industry_association_id")
	public IndustryAssociation getIndustryAssociation() {
		return industryAssociation;
	}

	public void setIndustryAssociation(IndustryAssociation industryAssociation) {
		this.industryAssociation = industryAssociation;
	}
	
}

