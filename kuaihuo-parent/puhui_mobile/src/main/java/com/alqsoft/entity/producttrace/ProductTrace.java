
package com.alqsoft.entity.producttrace;

import org.alqframework.orm.hibernate.IdEntity;

/**
 * Date:     2017年2月27日 下午5:51:04 <br/>
 * @author   zhangcan
 * @version  订单跟踪
 * @since    JDK 1.8
 * @see 	 
 */

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

