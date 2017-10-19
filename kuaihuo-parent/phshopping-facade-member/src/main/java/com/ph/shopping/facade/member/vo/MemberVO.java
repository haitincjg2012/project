package com.ph.shopping.facade.member.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 
 * @ClassName:  MemberVo   
 * @Description:会员返回数据  
 * @author: 李杰
 * @date:   2017年4月25日 上午11:23:54     
 * @Copyright: 2017
 */
public class MemberVO implements Serializable {
	
	/**
	 * @Fields field:field:{todo}(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -4724722202863029234L;
	
	
	private String equipmentId;

	/**
	 * 会员类型 0：会员，1：批发商
	 */
	private Byte type;

	/**
	 * token(识别批发商访问的唯一标识)
	 */
	private String token;
	/**
	 * tokenToMobile(返给手机端的积分使用)
	 */
	private String tokenToMobile;
	/**
	 * 推广人ID
	 */
	private Long promoterId;
	
	/**
	 * 代理ID
	 */
	private Long agentId;
	
	/**
	 * 业务员添加时间
	 */
	private Date salemanTime;
	
	/**
	 * 刮奖金额
	 */
	private Long probMoney;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;
	/**
	 * ID
	 */
	private Long id;

	/**
	 * 修改时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date updateTime;

	/**
	 * 手机号
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
	 * 身份证号
	 */
	private String idCardNo;
	/**
	 * 密码
	 */
	private String memberPwd;
	/**
	 * 是否是推广师
	 */
	private Byte isMarketing;
	/**
	 * 状态
	 */
	private Byte status;
	/**
	 * 头像图片 url
	 */
	private String headImage;
	/**
	 * 认证
	 */
	private Byte certification;
	/**
	 * 等级
	 */
	private Byte level;
	
	/**
	 * 证件Id
	 */
	private Long certificatesAuthId;
	/**
	 * 会员昵称
	 */
	private String nikeName;
	/**
	 * 是否店面经理
	 */
	private Byte isStoreManager;

	public String getNikeName() {
		return nikeName;
	}

	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}
	public Byte getLevel() {
		return level;
	}
	public void setLevel(Byte level) {
		this.level = level;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
	public String getMemberPwd() {
		return memberPwd;
	}
	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}
	public Byte getIsMarketing() {
		return isMarketing;
	}
	public void setIsMarketing(Byte isMarketing) {
		this.isMarketing = isMarketing;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public Byte getCertification() {
		return certification;
	}
	public void setCertification(Byte certification) {
		this.certification = certification;
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
	public Long getCertificatesAuthId() {
		return certificatesAuthId;
	}
	public void setCertificatesAuthId(Long certificatesAuthId) {
		this.certificatesAuthId = certificatesAuthId;
	}
	public String getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTokenToMobile() {
		return tokenToMobile;
	}
	public void setTokenToMobile(String tokenToMobile) {
		this.tokenToMobile = tokenToMobile;
	}
	public Long getPromoterId() {
		return promoterId;
	}
	public void setPromoterId(Long promoterId) {
		this.promoterId = promoterId;
	}
	public Long getAgentId() {
		return agentId;
	}
	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
	public Date getSalemanTime() {
		return salemanTime;
	}
	public void setSalemanTime(Date salemanTime) {
		this.salemanTime = salemanTime;
	}
	public Long getProbMoney() {
		return probMoney;
	}
	public void setProbMoney(Long probMoney) {
		this.probMoney = probMoney;
	}

	public Byte getIsStoreManager() {
		return isStoreManager;
	}

	public void setIsStoreManager(Byte isStoreManager) {
		this.isStoreManager = isStoreManager;
	}
}
