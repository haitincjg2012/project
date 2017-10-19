package com.ph.shopping.facade.member.dto;

import java.io.Serializable;
/**
 * 
 * @ClassName:  UnbundMemberCardDto   
 * @Description:会员解绑数据   
 * @author: 李杰
 * @date:   2017年4月25日 上午11:19:06     
 * @Copyright: 2017
 */
public class UnbundMemberCardDTO implements Serializable{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = -5517125669726783270L;
	
	//会员相关信息
	/**
	 * 手机号
	 */
	private String telPhone;
	/**
	 * 会员ID
	 */
	private Long memberId;
	
	//会员卡相关信息
	/**
	 * 会员卡id
	 */
	private Long icCardId;
	
	//平台相关用户信息
	/**
	 * 修改人Id
	 */
	private Long updaterId;

	/**
	 * 创建人Id
	 */
	private Long createrId;

	public Long getIcCardId() {
		return icCardId;
	}
	public void setIcCardId(Long icCardId) {
		this.icCardId = icCardId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public Long getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(Long updaterId) {
		this.updaterId = updaterId;
	}

	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}
}
