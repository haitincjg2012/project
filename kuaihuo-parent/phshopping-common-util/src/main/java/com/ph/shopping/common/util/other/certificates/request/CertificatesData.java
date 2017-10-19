package com.ph.shopping.common.util.other.certificates.request;

import java.io.Serializable;
/**
 * 
 * @ClassName:  CertificatesData   
 * @Description:证件认证参数  
 * @author: 李杰
 * @date:   2017年4月27日 下午3:38:53     
 * @Copyright: 2017
 */
public class CertificatesData implements Serializable{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -7023521212295124291L;
	/**
	 * 证件名称
	 */
	private String idName;
	/**
	 * 证件码
	 */
	private String idCode;
	/**
	 * 认证地址
	 */
	private String authUrl;
	
	public String getIdName() {
		return idName;
	}
	public void setIdName(String idName) {
		this.idName = idName;
	}
	public String getIdCode() {
		return idCode;
	}
	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
	public String getAuthUrl() {
		return authUrl;
	}
	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}
	
}
