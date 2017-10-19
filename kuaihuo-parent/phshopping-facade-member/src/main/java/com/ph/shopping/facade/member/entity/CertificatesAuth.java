package com.ph.shopping.facade.member.entity;

import com.ph.shopping.common.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 
 * phshopping-facade-member
 *
 * @description：证件认证表
 *
 * @author：liuy
 *
 * @createTime：2017年5月17日
 *
 * @Copyright @2017 by liuy
 */
@Table(name="ph_certificates_auth")
public class CertificatesAuth extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4194610398480076707L;
	
	/**
	 * 证件号码
	 */
	@Column(name = "certificatesCode")
	private String certificatesCode;
	/**
	 * 证件类型
	 */
	@Column(name = "certificatesType")
	private Byte certificatesType;
	/**
	 * 名称
	 */
	@Column(name = "certificatesName")
	private String certificatesName;
	/**
	 * 认证状态 1、认证成功   0、认证失败
	 */
	@Column(name = "status")
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
