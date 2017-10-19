package com.ph.shopping.facade.pay.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ph.shopping.common.core.base.BaseEntity;

/**
 * 
 * @项目：phshopping-facade-member
 *
 * @描述：会员充值记录表
 *
 * @作者： liuy
 *
 * @创建时间：2017年4月1日
 *
 * @Copyright @2017 by liuy
 */
@Table(name = "ph_member_charge_record")
public class MemberChargeRecord  extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7927754637721609120L;

	@Column(name="memberId")
    private Long memberId;//会员Id

    @Column(name="score")
    private  BigDecimal score;//支付金额

    @Column(name="orderNo")
    private  String orderNo;//订单号

    @Column(name="chargeType")
    private  Byte chargeType;//类型  1 充值   2 供应链支付（银行）  3 线上订单支付（银行）

    @Column(name="chargeStatus")
    private  Byte chargeStatus;//充值状态:1支付中，2支付成功，3失败

    @Column(name="md5Str")
    private  String md5Str;//md5报文加密值

    @Column(name="responseCode")
    private  String responseCode;//报文返回值

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Byte getChargeType() {
		return chargeType;
	}

	public void setChargeType(Byte chargeType) {
		this.chargeType = chargeType;
	}

	public Byte getChargeStatus() {
		return chargeStatus;
	}

	public void setChargeStatus(Byte chargeStatus) {
		this.chargeStatus = chargeStatus;
	}

	public String getMd5Str() {
		return md5Str;
	}

	public void setMd5Str(String md5Str) {
		this.md5Str = md5Str;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
}
