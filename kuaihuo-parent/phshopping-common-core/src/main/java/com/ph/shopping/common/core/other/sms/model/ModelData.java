/**  
 * @Title:  ModelData.java   
 * @Package com.ph.shopping.common.core.other.sms   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月12日 下午4:22:07   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.core.other.sms.model;

import java.io.Serializable;

import com.ph.shopping.common.core.customenum.SmsCodeType;

/**   
 * @ClassName:  ModelData   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年7月12日 下午4:22:07     
 * @Copyright: 2017
 */
public class ModelData implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -9147976298145806507L;
	/**
	 * 短信发送类型
	 */
	private SmsCodeType smsCodeType;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 短信来源对象
	 */
	private String smsSource;
	/**
	 * 短信类型
	 */
	private String item;
	/**
	 * 自定义短信内容
	 */
	private Object customData;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Object getCustomData() {
		return customData;
	}
	public void setCustomData(Object customData) {
		this.customData = customData;
	}
	public String getSmsSource() {
		return smsSource;
	}
	public void setSmsSource(String smsSource) {
		this.smsSource = smsSource;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public SmsCodeType getSmsCodeType() {
		return smsCodeType;
	}
	public void setSmsCodeType(SmsCodeType smsCodeType) {
		this.smsCodeType = smsCodeType;
	}
	
}
