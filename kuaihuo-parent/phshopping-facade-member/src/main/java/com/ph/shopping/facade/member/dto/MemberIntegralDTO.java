package com.ph.shopping.facade.member.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @ClassName:  MemberIntegralDto   
 * @Description:会员积分DTO 
 * @author: 李杰
 * @date:   2017年4月25日 上午11:16:51     
 * @Copyright: 2017
 */
public class MemberIntegralDTO implements Serializable{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = 3493491824841381788L;

	/**
	 * 
	 */
	private Integer pageNum;
	/**
	 * 
	 */
	private Integer pageSize;
	/**
	 * 会员ID
	 */
	private Long memberId;
	/**
	 * 会员名称
	 */
	private String memberName;
	/**
	 * 交易码
	 */
	private Integer transCode;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 交易码描述
	 */
	private String remark;
	/**
	 * 订单编号
	 */
	private String orderNo;
	/**
	 * 创建开始时间（用于搜索）
	 */
	private String createStartTime;
	/**
	 * 创建结束时间（用于搜索）
	 */
	private String createEndTime;
	/**
	 * 积分
	 */
	private Double score;
	
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
	 * 创建人Id
	 */
	private Long createrId;

	/**
	 * 是否冻结 未冻结 0 已冻结 1
	 */
	private Byte status;

	/**
	 * 可用积分
	 */
	private BigDecimal enableScore;
	/**
	 * 待用积分
	 */
	private BigDecimal standbyScore;
	/**
	 * 已提现积分
	 */
	private BigDecimal drawcashScore;
	/**
	 * 会员分润积分
	 */
	private BigDecimal memberProfitScore;
	/**
	 * 会员推荐会员奖励积分
	 */
	private BigDecimal memberRewardScore;
	/**
	 * 会员推荐商户奖励积分
	 */
	private BigDecimal merchantRewardScore;
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Integer getTransCode() {
		return transCode;
	}
	public void setTransCode(Integer transCode) {
		this.transCode = transCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}
	public BigDecimal getEnableScore() {
		return enableScore;
	}
	public void setEnableScore(BigDecimal enableScore) {
		this.enableScore = enableScore;
	}
	public BigDecimal getStandbyScore() {
		return standbyScore;
	}
	public void setStandbyScore(BigDecimal standbyScore) {
		this.standbyScore = standbyScore;
	}
	public BigDecimal getDrawcashScore() {
		return drawcashScore;
	}
	public void setDrawcashScore(BigDecimal drawcashScore) {
		this.drawcashScore = drawcashScore;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public BigDecimal getMemberProfitScore() {
		return memberProfitScore;
	}
	public void setMemberProfitScore(BigDecimal memberProfitScore) {
		this.memberProfitScore = memberProfitScore;
	}
	public BigDecimal getMemberRewardScore() {
		return memberRewardScore;
	}
	public void setMemberRewardScore(BigDecimal memberRewardScore) {
		this.memberRewardScore = memberRewardScore;
	}
	public BigDecimal getMerchantRewardScore() {
		return merchantRewardScore;
	}
	public void setMerchantRewardScore(BigDecimal merchantRewardScore) {
		this.merchantRewardScore = merchantRewardScore;
	}
	
}
