package com.alqsoft.vo;

import java.io.Serializable;

public class ProVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long psId;
	
	private int count;
	
	private int payType;

	public Long getPsId() {
		return psId;
	}

	public void setPsId(Long psId) {
		this.psId = psId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}
	
}
