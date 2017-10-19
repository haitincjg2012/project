package com.ph.shopping.facade.member.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @ClassName:  MemberCardInfoVO   
 * @Description:会员卡  
 * @author: 李杰
 * @date:   2017年4月25日 上午11:22:55     
 * @Copyright: 2017
 */
public class MemberCardInfoVO implements Serializable{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = -7099117017500541951L;
	/**
	 * 序号
	 */
	private Long id;

	/**
	 * 会员 卡id
	 */
	private Long icCardId;

	/**
	 * 会员ID
	 */
	private Long memberId;
	/**
	 * 商户ID
	 */
	private Long merchantId;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;
	/**
	 * 修改时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date updateTime;
	/**
	 * 绑定状态
	 */
	private Byte status;
	/**
	 * 挂失时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date reportTime;
	/**
	 * 创建人ID
	 */
	private Long createrId;
	/**
	 * 内码 IC卡号
	 */
	private String innerCode;
	/**
	 * 外码 条形码
	 */
	private String outerCode;

	/**
	 * 手机号
	 */
	private String telPhone;
	/**
	 * 会员名称
	 */
	private String memberName;
	/**
	 * 会员卡状态
	 */
	private Byte isDelete;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
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

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

	public String getOuterCode() {
		return outerCode;
	}

	public void setOuterCode(String outerCode) {
		this.outerCode = outerCode;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Byte getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Byte isDelete) {
		this.isDelete = isDelete;
	}
}
