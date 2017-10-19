/**  
 * @Title:  AddOrderVO.java   
 * @Package com.phshopping.api.controller.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月19日 下午3:27:39   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.phshopping.api.controller.vo;

import java.io.Serializable;

/**   
 * @ClassName:  AddOrderVO   
 * @Description:下单返回数据   
 * @author: 李杰
 * @date:   2017年6月19日 下午3:27:39     
 * @Copyright: 2017
 */
public class AddOrderVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 2335183770761532777L;
	/**
	 * 订单ID
	 */
	private Long id;
	/**
	 * 订单编号
	 */
	private String orderNo;
	
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
	
}
