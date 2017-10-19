package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @ClassName: BalanceDetailVO
 * @Description: 余额明细VO
 * @author 王强
 * @date 2017年6月8日 下午1:43:40
 */
public class BalanceDetailVO implements Serializable {

	private static final long serialVersionUID = -4129427366635137903L;

	private String telPhone;// 供链账号
	private String enterpriseType;// 企业类型
	private String enterpriseName;// 企业名称
	private String source;// 来源
	private String orderNo;// 订单号
	private String createTime;// 操作时间
	private Long score1;
	private String score;// 金额
	private Long handingCharge1;
	private String handingCharge;// 手续费

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getScore1() {
		return score1;
	}

	public void setScore1(Long score1) {
		this.score1 = score1;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Long getHandingCharge1() {
		return handingCharge1;
	}

	public void setHandingCharge1(Long handingCharge1) {
		this.handingCharge1 = handingCharge1;
	}

	public String getHandingCharge() {
		return handingCharge;
	}

	public void setHandingCharge(String handingCharge) {
		this.handingCharge = handingCharge;
	}

	public String getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
