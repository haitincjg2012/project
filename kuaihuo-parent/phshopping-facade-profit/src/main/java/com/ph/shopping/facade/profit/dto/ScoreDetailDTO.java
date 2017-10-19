package com.ph.shopping.facade.profit.dto;

import com.ph.shopping.common.util.page.PageBean;

/**
 * 
 * @ClassName: ScoreDetailDTO
 * @Description: 积分明细DTO
 * @author 王强
 * @date 2017年6月7日 上午11:42:27
 */
public class ScoreDetailDTO extends PageBean {

	private static final long serialVersionUID = 2727575993818343428L;

	private String telPhone;// 会员账号
	private Integer memberType;// 会员类型
	private Integer transCode;// 来源
	private String startDate;// 开始时间
	private String endDate;// 结束时间
	private Integer type;//1导出，2查询(默认)
	
	private Long  memberId;//会员id

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public Integer getTransCode() {
		return transCode;
	}

	public void setTransCode(Integer transCode) {
		this.transCode = transCode;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	
}
