/**  
 * @Title:  MemberShareRecordVO.java   
 * @Package com.ph.shopping.facade.member.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月24日 下午5:26:30   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.member.vo;

import java.io.Serializable;
import java.util.Date;

/**   
 * @ClassName:  MemberShareRecordVO   
 * @Description:分享记录返回实体  
 * @author: 李杰
 * @date:   2017年7月24日 下午5:26:30     
 * @Copyright: 2017
 */
public class MemberShareRecordVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 6818671667796042493L;

	private Long id;
	/**
	 * 分享人手机号
	 */
	private String telPhone;
	/**
	 * 被分享人手机号
	 */
	private String toTelPhone;
	/**
	 * 分享人ID
	 */
	private Long userId;
	/**
	 * 分享类型
	 */
	private Byte type;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 分享人名称
	 */
	private String userName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public String getToTelPhone() {
		return toTelPhone;
	}
	public void setToTelPhone(String toTelPhone) {
		this.toTelPhone = toTelPhone;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
