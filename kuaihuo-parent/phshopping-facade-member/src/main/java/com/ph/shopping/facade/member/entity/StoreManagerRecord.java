/**
 * 
 */
package com.ph.shopping.facade.member.entity;

import com.ph.shopping.common.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @项目：phshopping-facade-member
 *
 * @描述： 店面经理
 *
 * @作者： Mr.chang
 *
 * @创建时间：2017年3月9日
 *
 * @Copyright @2017 by Mr.chang
 */
@Table(name="ph_store_manager_record")
public class StoreManagerRecord extends BaseEntity{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = 6199149469513426448L;
	/**
	 * 手机号
	 */
	@Column(name = "telPhone")
	private String telPhone;
	/**
	 * 姓名
	 */
	@Column(name = "memberName")
	private String memberName;
	/**
	 * 身份证号
	 */
	@Column(name = "idCardNo")
	private String idCardNo;
	/**
		 * 状态  0在职 1解聘
	 */
	@Column(name = "status")
	private Byte status;
	/**
	 * 认证 0审核中 1已审核 2审核失败
	 */
	@Column(name = "certification")
	private Byte certification;
	/**
	 * 店面经理管理商户
	 */
	@Column(name = "userId")
	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Byte getCertification() {
		return certification;
	}

	public void setCertification(Byte certification) {
		this.certification = certification;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

}
