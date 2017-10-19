
package com.alqsoft.entity.inoutdetail;

import org.alqframework.orm.hibernate.IdEntity;

import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.industryassociation.IndustryAssociation;

/**
 * Date:     2017年2月27日 下午2:42:37 <br/>
 * @author   zhangcan
 * @version  收入明细
 * @since    JDK 1.8
 * @see 	 
 */

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
	
	public Hunter getHunter() {
		return hunter;
	}

	public void setHunter(Hunter hunter) {
		this.hunter = hunter;
	}
	
	public IndustryAssociation getIndustryAssociation() {
		return industryAssociation;
	}

	public void setIndustryAssociation(IndustryAssociation industryAssociation) {
		this.industryAssociation = industryAssociation;
	}
	
}

