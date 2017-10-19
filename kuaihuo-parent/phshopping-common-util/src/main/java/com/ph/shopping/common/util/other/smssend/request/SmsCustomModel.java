package com.ph.shopping.common.util.other.smssend.request;

import java.util.List;
/**
 * 
 * @ClassName:  SmsCustomModel   
 * @Description:自定义发送模板请求数据   
 * @author: 李杰
 * @date:   2017年4月27日 下午3:39:55     
 * @Copyright: 2017
 */
public class SmsCustomModel {

	/**
	 * url
	 */
	private String url;
	/**
	 * 
	 */
	private String sdkappid;
	/**
	 * 区号
	 */
	private String nationcode;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 公司名称
	 */
	private String sign;
	/**
	 * 模板编号
	 */
	private int tplId;
	/**
	 * 
	 */
	private String type;
	/**
	 * 模板参数
	 */
	private List<Object> params;
	/**
	 * appKey
	 */
	private String appKey;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSdkappid() {
		return sdkappid;
	}
	public void setSdkappid(String sdkappid) {
		this.sdkappid = sdkappid;
	}
	public String getNationcode() {
		return nationcode;
	}
	public void setNationcode(String nationcode) {
		this.nationcode = nationcode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public int getTplId() {
		return tplId;
	}
	public void setTplId(int tplId) {
		this.tplId = tplId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Object> getParams() {
		return params;
	}
	public void setParams(List<Object> params) {
		this.params = params;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	
}
