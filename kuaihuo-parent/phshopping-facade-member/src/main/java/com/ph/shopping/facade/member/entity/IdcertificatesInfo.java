package com.ph.shopping.facade.member.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * phshopping-facade-member
 *
 * @description：会员身份证件信息表
 *
 * @author：liuy
 *
 * @createTime：2017年5月23日
 *
 * @Copyright @2017 by liuy
 */
@Table(name="ph_member_idcertificates_info")
public class IdcertificatesInfo implements Serializable{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)
	*/  
	private static final long serialVersionUID = 503733369039545902L;

	@Id
	private Long id;
	/**
	 * 创建时间
	 */
	@Column(name="createTime")
	private Date createTime;
	/**
	 * 修改时间
	 */
	@Column(name="updateTime")
	private Date updateTime;
	/**
	 * 图片地址
	 */
	private String url;
	/**
	 * 证件类型
	 */
	@Column(name = "certificatesType")
	private Byte certificatesType;
	/**
	 * 操作人id
	 */
	@Column(name = "createrId")
	private Long createrId;
	/**
	 * 证件号码
	 */
	@Column(name = "certificatesCodeNo")
	private String certificatesCodeNo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Byte getCertificatesType() {
		return certificatesType;
	}
	public void setCertificatesType(Byte certificatesType) {
		this.certificatesType = certificatesType;
	}
	public Long getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}
	public String getCertificatesCodeNo() {
		return certificatesCodeNo;
	}
	public void setCertificatesCodeNo(String certificatesCodeNo) {
		this.certificatesCodeNo = certificatesCodeNo;
	}
}
