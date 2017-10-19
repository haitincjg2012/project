/**  
 * @Title:  SmsSendData.java   
 * @Package com.ph.shopping.common.core.other.sms.data   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月17日 下午4:47:46   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.core.other.sms.data;

import java.io.Serializable;

import com.ph.shopping.common.core.customenum.SmsCodeType;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;

/**   
 * @ClassName:  SmsSendData   
 * @Description:短信发送数据  
 * @author: 李杰
 * @date:   2017年5月17日 下午4:47:46     
 * @Copyright: 2017
 */
public class SmsSendData implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -2864492858089870240L;

	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 发送类型
	 */
	private SmsCodeType type;
	/**
	 * 短信验证码
	 */
	private String smsCode;
	/**
	 * 来源
	 */
	private SmsSourceEnum sourceEnum;
	/**
	 * 模板填充数据
	 */
	private Object modelData;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public SmsCodeType getType() {
		return type;
	}
	public void setType(SmsCodeType type) {
		this.type = type;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public SmsSourceEnum getSourceEnum() {
		return sourceEnum;
	}
	public void setSourceEnum(SmsSourceEnum sourceEnum) {
		this.sourceEnum = sourceEnum;
	}
	public Object getModelData() {
		return modelData;
	}
	public void setModelData(Object modelData) {
		this.modelData = modelData;
	}
	
}
