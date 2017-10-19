/**  
 * @Title:  SynHeadhuntingDetailVO.java   
 * @Package com.ph.member.api.vo.syn   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月11日 下午3:42:32   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.member.api.vo.syn;

import java.io.Serializable;

/**   
 * @ClassName:  SynHeadhuntingDetailVO   
 * @Description:北京 批发商查询返回详细信息
 * @author: 李杰
 * @date:   2017年7月11日 下午3:42:32     
 * @Copyright: 2017
 */
public class SynHeadhuntingDetailVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 5220273310865701841L;
	/**
	 * 批发商ID
	 */
	private Long hunterId;
	/**
	 * 批发商账号
	 */
	private String phone;
	/**
	 * 头像地址
	 */
	private String logoAddress;
	/**
	 * 公会名称
	 */
	private String associationName;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 行业分类名称
	 */
	private String industryName;
	/**
	 * 乡镇ID
	 */
	private Long townId;
	
	public Long getHunterId() {
		return hunterId;
	}
	public void setHunterId(Long hunterId) {
		this.hunterId = hunterId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLogoAddress() {
		return logoAddress;
	}
	public void setLogoAddress(String logoAddress) {
		this.logoAddress = logoAddress;
	}
	public String getAssociationName() {
		return associationName;
	}
	public void setAssociationName(String associationName) {
		this.associationName = associationName;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public Long getTownId() {
		return townId;
	}
	public void setTownId(Long townId) {
		this.townId = townId;
	}
}
