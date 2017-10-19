/**  
 * @Title:  MemberShareRecord.java   
 * @Package com.ph.shopping.facade.member.entity   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月24日 下午5:12:48   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.member.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**   
 * @ClassName:  MemberShareRecord   
 * @Description:会员分享实体   
 * @author: 李杰
 * @date:   2017年7月24日 下午5:12:48     
 * @Copyright: 2017
 */
@Table(name="ph_member_share_record")
public class MemberShareRecord implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 8007340484277086588L;

	@Id
	private Long id;
	/**
	 * 创建时间
	 */
	@Column(name = "createTime")
    private Date createTime;
	/**
	 * 手机号
	 */
	@Column(name = "telPhone")
	private String telPhone;
	/**
	 * 分享人ID
	 */
	@Column(name = "userId")
	private Long userId;
	/**
	 * 被分享人手机号
	 */
	@Column(name = "toTelPhone")
	private String toTelPhone;
	/**
	 * 类型
	 */
	@Column(name = "type")
	private Byte type;
	/**
	 * 创建ip
	 */
	@Column(name = "createIp")
	private String createIp;
	
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
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getToTelPhone() {
		return toTelPhone;
	}
	public void setToTelPhone(String toTelPhone) {
		this.toTelPhone = toTelPhone;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public String getCreateIp() {
		return createIp;
	}
	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}
}
