package com.alqsoft.entity.memberconnect;

import org.apache.ibatis.type.Alias;

import com.alqsoft.entity.IdEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

/***
 * 
*@Description: 第三方登录实体类
* 
* @author : zhuangll
* @ProjectName : haitunwu-interface
* @version : v4.0 - 2014
* @createTime : 2014-8-18 下午3:20:06
*
 */
@Alias("memberconnect")
public class MemberConnect extends IdEntity{
	
	@JsonProperty("memberconnectId")
	private Long id;
	private String thirdAccount;//第三方帐号
	private String thirdType;//第三方帐号类型 QQ weibo 
	private int isBind;//是否绑定 0-否 1-是
	private Long memberId;//绑定会员帐号ID
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getThirdAccount() {
		return thirdAccount;
	}
	public void setThirdAccount(String thirdAccount) {
		this.thirdAccount = thirdAccount;
	}
	public String getThirdType() {
		return thirdType;
	}
	public void setThirdType(String thirdType) {
		this.thirdType = thirdType;
	}
	public int getIsBind() {
		return isBind;
	}
	public void setIsBind(int isBind) {
		this.isBind = isBind;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
}
