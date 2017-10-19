package com.ph.shopping.facade.member.dto;

import java.io.Serializable;
/**
 * 
 * @ClassName:  MerberCertificatesDto   
 * @Description:会员认证信息传输数据 
 * @author: 李杰
 * @date:   2017年4月25日 上午11:17:48     
 * @Copyright: 2017
 */
public class MerberCertificatesDTO implements Serializable{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = -2887605518102722879L;
	/**
	 * 图片根路径
	 */
	private String url;
	/**
	 * 证件类型
	 */
	private Byte certificatesType;
	/**
	 *证件 操作人id
	 */
	private Long createrId;
	/**
	 * 证件号码
	 */
	private String certificatesCodeNo;
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
