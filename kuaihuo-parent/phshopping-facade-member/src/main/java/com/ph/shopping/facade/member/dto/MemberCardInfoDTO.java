package com.ph.shopping.facade.member.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @ClassName:  MemberCardInfoDto   
 * @Description:会员卡数据   
 * @author: 李杰
 * @date:   2017年4月25日 上午11:15:56     
 * @Copyright: 2017
 */
public class MemberCardInfoDTO implements Serializable{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = -7099117017500541951L;

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 会员id
	 */
	private Long memberId;//之前这里用的id，因为多个DTO合并成一个，统一改为memberId
	/**
	 * 会员手机号
	 */
	private String telPhone;
	/**
	 * 姓名
	 */
	private String memberName;
	/**
	 * 性别
	 */
	private Byte sex;
	/**
	 * 头像图片 url
	 */
	private String headImage;
	/**
	 * 身份证号
	 */
	private String idCardNo;
	/**
	 * 内码 卡号
	 */
	private String innerCode;
	/**
	 * 外码 条形码
	 */
	private String outerCode;
	/**
	 * 会员创建时间
	 */
	private Date memberCreateTime;
	/**
	 * 绑定时间
	 */
	private Date bindCreateTime;
	
	/**
	 * 商户id
	 */
	private Long merchantId;
	/**
	 * 绑定状态
	 */
	private Byte status;
	/**
	 * 会员卡状态
	 */
	private Byte isDelete;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 会员卡id
	 */
	private Long icCardId;
	/**
	 * 修改人Id
	 */
	private Long updaterId;

	/**
	 * 创建人Id
	 */
	private Long createrId;

	/**
	 * 短信验证码
	 */
	private String msgCode;
	
	public Date getMemberCreateTime() {
		return memberCreateTime;
	}
	public void setMemberCreateTime(Date memberCreateTime) {
		this.memberCreateTime = memberCreateTime;
	}
	public Date getBindCreateTime() {
		return bindCreateTime;
	}
	public void setBindCreateTime(Date bindCreateTime) {
		this.bindCreateTime = bindCreateTime;
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
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public Byte getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Byte isDelete) {
		this.isDelete = isDelete;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Byte getSex() {
		return sex;
	}
	public void setSex(Byte sex) {
		this.sex = sex;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
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
	public Long getIcCardId() {
		return icCardId;
	}
	public void setIcCardId(Long icCardId) {
		this.icCardId = icCardId;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	
}
