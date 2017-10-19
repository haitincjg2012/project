package com.ph.shopping.facade.member.dto;

import java.io.Serializable;
/**
 * 
 * @ClassName:  IcCradindInfoDTO   
 * @Description:会员卡认证参数  
 * @author: 李杰
 * @date:   2017年4月25日 上午11:15:39     
 * @Copyright: 2017
 */
public class IcCradindInfoDTO implements Serializable{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = -976311716984782662L;

	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 联系人
	 */
	private String name;
	/**
	 * 条形码
	 */
	private String outerCode;
	/**
	 * 商户ID
	 */
	private Long merchantId;
	/**
	 * 内码 卡号
	 */
	private String innerCode;
	
	
	public String getOuterCode() {
		return outerCode;
	}
	public void setOuterCode(String outerCode) {
		this.outerCode = outerCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public String getInnerCode() {
		return innerCode;
	}
	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}
	
}
