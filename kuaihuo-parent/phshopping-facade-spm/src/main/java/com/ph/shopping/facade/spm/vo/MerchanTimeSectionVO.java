package com.ph.shopping.facade.spm.vo;

import java.io.Serializable;

public class MerchanTimeSectionVO implements Serializable{

	private static final long serialVersionUID = 6084962640634535102L;

	/** 主键id */
	private Long id;
	
	/**商户id*/
	private Long merchantId;

	/**开始时间*/
	private String startTime;

	/**结束时间*/
	private String endTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
