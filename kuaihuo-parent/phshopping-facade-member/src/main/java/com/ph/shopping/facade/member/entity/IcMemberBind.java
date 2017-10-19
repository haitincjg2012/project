package com.ph.shopping.facade.member.entity;

import com.ph.shopping.common.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "ph_member_ic_card_bind")
public class IcMemberBind extends BaseEntity{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = -917515125304847280L;
	
	/**
	 * 会员卡id
	 */
	@Column(name = "icCardId")
	private Long icCardId;
	/**
	 * 会员id
	 */
	@Column(name = "memberId")
	private Long memberId;
	/**
	 * 商户id
	 */
	@Column(name = "merchantId")
	private Long merchantId;
	/**
	 * 绑定状态
	 */
	@Column(name = "status")
	private Byte status;
	/**
	 * 挂失时间
	 */
	@Column(name = "reportTime")
	private Date reportTime;
	
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
	
}
