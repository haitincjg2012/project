/**  
 * @Title:  ConsumeRemindData.java   
 * @Package com.ph.shopping.common.core.other.sms.data   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月11日 下午1:51:10   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.util.other.smssend.model.data;

import java.io.Serializable;

/**   
 * @ClassName:  ConsumeRemindData   
 * @Description:消费信息提醒详细信息   
 * @author: 李杰
 * @date:   2017年5月11日 下午1:51:10     
 * @Copyright: 2017
 */
public class ConsumeRemindModelData implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -4706494334770200235L;
	/**
	 * 短信接收人类型(比如：会员)
	 */
	private String smsAcceptType;
	/**
	 * 年
	 */
	private String year;
	/**
	 * 月
	 */
	private String month;
	/**
	 * 日
	 */
	private String day;
	/**
	 * 消费金额
	 */
	private String money;
	/**
	 * 订单编号
	 */
	private String orderNo;
	/**
	 * 补贴积分
	 */
	private String subsidyScore;
	/**
	 * 可用积分
	 */
	private String usableScore;
	/**
	 * 待用积分
	 */
	private String standbyScore;
	
	public String getSmsAcceptType() {
		return smsAcceptType;
	}
	public void setSmsAcceptType(String smsAcceptType) {
		this.smsAcceptType = smsAcceptType;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getSubsidyScore() {
		return subsidyScore;
	}
	public void setSubsidyScore(String subsidyScore) {
		this.subsidyScore = subsidyScore;
	}
	public String getUsableScore() {
		return usableScore;
	}
	public void setUsableScore(String usableScore) {
		this.usableScore = usableScore;
	}
	public String getStandbyScore() {
		return standbyScore;
	}
	public void setStandbyScore(String standbyScore) {
		this.standbyScore = standbyScore;
	}
}
