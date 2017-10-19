/**  
 * @Title:  AuthDetailVO.java   
 * @Package com.phshopping.api.controller.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月19日 下午5:07:06   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.phshopping.api.controller.vo;

import java.io.Serializable;

/**   
 * @ClassName:  AuthDetailVO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年6月19日 下午5:07:06     
 * @Copyright: 2017
 */
public class AuthDetailVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -459593736978168141L;
	/**
	 * 证件号码
	 */
	private String certificatesCode;
	/**
	 * 证件类型
	 */
	private Byte certificatesType;
	/**
	 * 名称
	 */
	private String certificatesName;
	/**
	 * 认证状态 1、认证成功   0、认证失败
	 */
	private Byte status;
	
	public String getCertificatesCode() {
		return certificatesCode;
	}
	public void setCertificatesCode(String certificatesCode) {
		this.certificatesCode = certificatesCode;
	}
	public Byte getCertificatesType() {
		return certificatesType;
	}
	public void setCertificatesType(Byte certificatesType) {
		this.certificatesType = certificatesType;
	}
	public String getCertificatesName() {
		return certificatesName;
	}
	public void setCertificatesName(String certificatesName) {
		this.certificatesName = certificatesName;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
}
