package com.ph.shopping.facade.spm.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ph.shopping.common.core.base.BaseEntity;

/**
 * 
 * phshopping-facade-spm
 *
 * @description：商户充值记录表
 *
 * @author：liuy
 *
 * @createTime：2017年4月1日
 *
 * @Copyright @2017 by liuy
 */
@Table(name = "ph_merchant_charge_record")
public class MerchantChargeRecord  extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7123061705317204258L;

	@Column(name="merchantId")
    private Long merchantId;//商户Id

    @Column(name="score")
    private  BigDecimal score;//积分

    @Column(name="orderNum")
    private  String orderNum;//订单号

    @Column(name="chargeType")
    private  Integer chargeType;//类型  1 充值   2 供应链支付（银行）  3 线上订单支付（银行）

    @Column(name="chargeStatus")
    private  Integer chargeStatus;//充值状态:0充值中，1充值成功，2充值失败

    @Column(name="md5Str")
    private  String md5Str;//md5报文加密值

    @Column(name="creater")
    private  Long creater;//创建人
    
    @Column(name="operater")
    private  Long operater;//更新人


	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getChargeType() {
		return chargeType;
	}

	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
	}

	public Integer getChargeStatus() {
		return chargeStatus;
	}

	public void setChargeStatus(Integer chargeStatus) {
		this.chargeStatus = chargeStatus;
	}

	public String getMd5Str() {
		return md5Str;
	}

	public void setMd5Str(String md5Str) {
		this.md5Str = md5Str;
	}

	public Long getCreater() {
		return creater;
	}

	public void setCreater(Long creater) {
		this.creater = creater;
	}

	public Long getOperater() {
		return operater;
	}

	public void setOperater(Long operater) {
		this.operater = operater;
	}
    
    
}
