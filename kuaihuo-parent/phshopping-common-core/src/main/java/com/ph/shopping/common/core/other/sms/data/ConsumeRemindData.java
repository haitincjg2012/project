/**  
 * @Title:  ConsumeRemindData.java   
 * @Package com.ph.shopping.common.core.other.sms.data   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月11日 下午1:51:10   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.core.other.sms.data;

import java.io.Serializable;

/**   
 * @ClassName:  ConsumeRemindData   
 * @Description:消费信息提醒详细信息   
 * @author: 李杰
 * @date:   2017年5月11日 下午1:51:10     
 * @Copyright: 2017
 */
public class ConsumeRemindData implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 5617385928645056351L;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 消费金额
	 */
	private Double orderMoney;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 获得补贴积分
	 */
	private Double score;
	/**
	 * 累计补贴积分
	 */
	private Double standbyScore;
	/**
	 * 可用积分
	 */
	private Double enableScore;

	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Double getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Double getStandbyScore() {
		return standbyScore;
	}

	public void setStandbyScore(Double standbyScore) {
		this.standbyScore = standbyScore;
	}

	public Double getEnableScore() {
		return enableScore;
	}

	public void setEnableScore(Double enableScore) {
		this.enableScore = enableScore;
	}
	
}
