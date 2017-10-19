/**  
 * @Title:  HeadhuntingVO.java   
 * @Package com.ph.member.api.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月7日 下午5:25:30   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.member.api.vo;

import java.io.Serializable;

/**   
 * @ClassName:  HeadhuntingVO   
 * @Description:批发商数据
 * @author: 李杰
 * @date:   2017年7月7日 下午5:25:30     
 * @Copyright: 2017
 */
public class HeadhuntingVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -2842081958214950484L;
	/**
	 * 头像地址
	 */
	private String headImgUrl;
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 账号
	 */
	private String accountNumber;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 工会名称
	 */
	private String laborUnionName;
	/**
	 * 标签
	 */
	private String tags;
	/**
	 * 详细地址
	 */
	private String detailAddress;
	
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getLaborUnionName() {
		return laborUnionName;
	}
	public void setLaborUnionName(String laborUnionName) {
		this.laborUnionName = laborUnionName;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
