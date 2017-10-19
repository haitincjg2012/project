package com.ph.shopping.facade.member.dto;

import java.io.Serializable;
import java.util.Date;
/**
 *
 * @ClassName:  MemberPageDto
 * @Description:会员分页传输数据
 * @author: 李杰
 * @date:   2017年4月25日 上午11:17:16     
 * @Copyright: 2017
 */
public class MemberDTO  implements Serializable{

	/**
	 * @Fields field:field:{todo}(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 7444501116180669284L;


	/**
	 * 当前页码
	 */
	private Integer pageNum;
	/**
	 * 每页条数
	 */
	private Integer pageSize;

	/**
	 * 会员ID
	 */
	private Long id;
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
	 * 是否是推广师
	 */
	private Byte isMarketing;
	/**
	 * 认证
	 */
	private Byte certification;
	/**
	 * 状态
	 */
	private Byte status;
	/**
	 * 搜索开始时间  liuy重构 2017年05月15日
	 */
	private String createStartTime;
	/**
	 * 搜索结束时间 liuy重构 2017年05月15日
	 */
	private String createEndTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 会员等级
	 */
	private Byte level;

	/**
	 * 头像图片 url
	 */
	private String headImage;
	/**
	 * 身份证号
	 */
	private String idCardNo;

	/**
	 * 证件Id
	 */
	private Long certificatesAuthId;

	/**
	 * 推广师审核状态（用于查询推广师是否能分润）
	 */
	private Byte promotionStatus;

	/**
	 * 创建人Ip
	 */
	private String createIp;

	/**
	 * 创建人Id
	 */
	private Long createrId;

	/**
	 * 修改人Id
	 */
	private Long updaterId;

	/**
	 * 代理id
	 */
	private Long agentId;

	private String memberPwd;

	private Integer isFrozen;



	public Integer getIsFrozen() {
		return isFrozen;
	}
	public void setIsFrozen(Integer isFrozen) {
		this.isFrozen = isFrozen;
	}
	public String getMemberPwd() {
		return memberPwd;
	}
	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}
	public Long getAgentId() {
		return agentId;
	}
	public void setAgentId(Long agentId) {
		this.agentId = agentId;
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
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreateStartTime() {
		return createStartTime;
	}
	public void setCreateStartTime(String createStartTime) {
		this.createStartTime = createStartTime;
	}
	public String getCreateEndTime() {
		return createEndTime;
	}
	public void setCreateEndTime(String createEndTime) {
		this.createEndTime = createEndTime;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
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
	public Byte getIsMarketing() {
		return isMarketing;
	}
	public void setIsMarketing(Byte isMarketing) {
		this.isMarketing = isMarketing;
	}
	public Byte getCertification() {
		return certification;
	}
	public void setCertification(Byte certification) {
		this.certification = certification;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Byte getPromotionStatus() {
		return promotionStatus;
	}
	public void setPromotionStatus(Byte promotionStatus) {
		this.promotionStatus = promotionStatus;
	}
	public String getCreateIp() {
		return createIp;
	}
	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}
	public Long getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}
	public Long getUpdaterId() {
		return updaterId;
	}
	public void setUpdaterId(Long updaterId) {
		this.updaterId = updaterId;
	}

}
