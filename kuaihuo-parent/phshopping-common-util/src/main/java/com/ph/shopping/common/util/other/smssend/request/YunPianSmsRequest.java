/**  
 * @Title:  YunPianSmsRequest.java   
 * @Package com.ph.shopping.common.util.other.smssend.request   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月11日 上午10:06:28   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.util.other.smssend.request;

import java.io.Serializable;

/**   
 * @ClassName:  YunPianSmsRequest   
 * @Description:yunpian 短信请求参数  
 * @author: 李杰
 * @date:   2017年5月11日 上午10:06:28     
 * @Copyright: 2017
 */
public class YunPianSmsRequest implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -428106263963330240L;
	/**
	 * 用户唯一标识，在管理控制台获取
	 */
	private String apikey;
	/**
	 * 接收的手机号，仅支持单号码发送
	 */
	private String mobile;
	/**
	 * 已审核短信模板
	 */
	private String text;
	/**
	 * 下发号码扩展号，纯数字
	 */
	private String extend;
	/**
	 * 该条短信在您业务系统内的ID，如订单号或者短信发送记录流水号
	 */
	private String uid;
	/**
	 * 短信发送后将向这个地址推送(运营商返回的)发送报告。
	 * 如推送地址固定，建议在"数据推送与获取”做批量设置。
	 * 如后台已设置地址，且请求内也包含此参数，将以请求内地址为准
	 */
	private String callback_url;
	/**
	 * 是否为注册验证码短信，如果传入true，
	 * 则该条短信作为注册验证码短信统计注册成功率，需联系客服开通。
	 */
	private Boolean register;
	
	public String getApikey() {
		return apikey;
	}
	public void setApikey(String apikey) {
		this.apikey = apikey;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getCallback_url() {
		return callback_url;
	}
	public void setCallback_url(String callback_url) {
		this.callback_url = callback_url;
	}
	public Boolean getRegister() {
		return register;
	}
	public void setRegister(Boolean register) {
		this.register = register;
	}
	
}
