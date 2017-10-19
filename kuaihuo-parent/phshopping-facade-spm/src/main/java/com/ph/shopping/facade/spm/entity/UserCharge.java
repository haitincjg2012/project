package com.ph.shopping.facade.spm.entity;

import com.ph.shopping.common.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * 
 * @ClassName: UserChargeRecord
 * @Description: 充值记录表实体
 * @author 王强
 * @date 2017年5月16日 下午5:03:42
 */
@Table(name = "ph_user_charge_record")
public class UserCharge extends BaseEntity {

	private static final long serialVersionUID = 1146073349515838593L;

	/** 代理商id */
	@Column(name = "userId")
	private Long userId;

	/** 充值转换后的积分，充值金额*10000后的数值 */
	private BigDecimal score;
	
	@Transient
	private Double scoreTrans;

	/** 支付订单号 */
	@Column(name = "orderNo")
	private String orderNo;

	/** 0银行卡充值;1支付宝充值 默认0银行卡充值 */
	@Column(name = "chargeType")
	private Byte chargeType = 0;

	/** 充值状态:0充值中，1充值成功，2充值失败 */
	@Column(name = "chargeStatus")
	private Byte chargeStatus = 0;

	/** md5报文加密值 */
	@Column(name = "md5Str")
	private String md5Str;

	/** 返回报文值 */
	@Column(name = "responseCode")
	private String responseCode;

	/** 用户角色区分：2供应商3市级代理4县级代理5社区代理6商户 */
	@Column(name = "userType")
	private Byte userType;
	
	//表示它是临时字段，不是数据库字段
	@Transient
	private String payUrl;//请求连接

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
		this.orderNo = orderNo == null ? null : orderNo.trim();
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
		this.md5Str = md5Str == null ? null : md5Str.trim();
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode == null ? null : responseCode.trim();
	}

	public Byte getUserType() {
		return userType;
	}

	public void setUserType(Byte userType) {
		this.userType = userType;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

	public Double getScoreTrans() {
		return scoreTrans;
	}

	public void setScoreTrans(Double scoreTrans) {
		this.scoreTrans = scoreTrans;
	}
}