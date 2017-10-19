package com.ph.shopping.facade.member.vo;

import com.ph.shopping.common.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @项目：phshopping-facade-spm
 * @描述：商户信息表
 * @作者 何文浪 @时间：2017-5-12
 * @version: 2.1
 */
public class MerchantTimeSectionVO extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7615786485331647942L;


	/**商户id*/
	private Long merchantId;

	/**开始时间*/
	private String startTime;

	/**结束时间*/
	private String endTime;

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}