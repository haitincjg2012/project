
package com.alqsoft.entity.producttrace;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Date:     2017年2月27日 下午5:51:04 <br/>
 * @author   zhangcan
 * @version  订单跟踪
 * @since    JDK 1.8
 * @see 	 
 */
@Entity
@Table(name = "alq_product_trace", indexes = {})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class ProductTrace extends IdEntity{
	
	private String orderNo;//订单号
	
	private String content;//订单跟踪内容

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

