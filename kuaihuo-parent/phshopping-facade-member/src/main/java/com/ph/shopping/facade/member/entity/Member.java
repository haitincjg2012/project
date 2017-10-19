/**
 * 
 */
package com.ph.shopping.facade.member.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ph.shopping.common.core.base.BaseEntity;
import org.apache.catalina.Manager;
import org.apache.catalina.Store;

/**
 * @项目：phshopping-facade-member
 *
 * @描述：
 *
 * @作者： Mr.chang
 *
 * @创建时间：2017年3月9日
 *
 * @Copyright @2017 by Mr.chang
 */
@Table(name="ph_member")
public class Member extends BaseEntity{

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
	 * 性别
	 */
	@Column(name = "sex")
	private Byte sex;
	/**
	 * 身份证号
	 */
	@Column(name = "idCardNo")
	private String idCardNo;
	/**
	 * 密码
	 */
	@Column(name = "memberPwd")
	private String memberPwd;
	/**
	 * 是否是推广师
	 */
	@Column(name = "isMarketing")
	private Byte isMarketing;
	/**
	 * 状态
	 */
	@Column(name = "status")
	private Byte status;
	/**
	 * 头像图片 url
	 */
	@Column(name = "headImage")
	private String headImage;
	/**
	 * 认证
	 */
	@Column(name = "certification")
	private Byte certification;
	/**
	 * 会员等级
	 */
	@Column(name = "level")
	private Byte level;
	
	/**
	 * 证件Id
	 */
	@Column(name = "certificatesAuthId")
	private Long certificatesAuthId;
	
	/**
	 * 设备Id
	 */
	@Column(name = "equipmentId")
	private String equipmentId;

	/**
	 * 会员类型 0：会员，1：批发商
	 */
	@Column(name = "type")
	private Byte type;

	/**
	 * token(识别批发商访问的唯一标识)
	 */
	@Column(name = "token")
	private String token;
	/**
	 * tokenToMobile(返给手机端的积分使用)
	 */
	@Column(name = "tokenToMobile")
	private String tokenToMobile;
	/**
	 * 推广人ID
	 */
	@Column(name = "promoterId")
	private Long promoterId;
	
	/**
	 * 代理ID
	 */
	@Column(name = "agentId")
	private Long agentId;
	/** 是否冻结 0:否 1:是 */
	@Column(name = "isFrozen")
	private Byte isFrozen;
	/**
	 * 业务员添加时间
	 */
	@Column(name = "salemanTime")
	private Date salemanTime;
	
	/**
	 * 刮奖金额
	 */
	@Column(name = "probMoney")
	private Long probMoney;

	@Column(name="nikeName")
	private String nikeName;

	/**
	 * 是否店面经理  0否  1是
	 */
	@Column(name = "isStoreManager")
	private Byte isStoreManager;

	/**
	 * 店面经理管理商户
	 */
	@Column(name = "userId")
	private Long userId;

	public Byte getIsStoreManager() {
		return isStoreManager;
	}

	public void setIsStoreManager(Byte isStoreManager) {
		this.isStoreManager = isStoreManager;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNikeName() {
		return nikeName;
	}

	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Byte getLevel() {
		return level;
	}

	public void setLevel(Byte level) {
		this.level = level;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getPromoterId() {
		return promoterId;
	}

	public void setPromoterId(Long promoterId) {
		this.promoterId = promoterId;
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

	public String getTokenToMobile() {
		return tokenToMobile;
	}

	public void setTokenToMobile(String tokenToMobile) {
		this.tokenToMobile = tokenToMobile;
	}

	public Byte getIsFrozen() {
		return isFrozen;
	}

	public void setIsFrozen(Byte isFrozen) {
		this.isFrozen = isFrozen;
	}


}
