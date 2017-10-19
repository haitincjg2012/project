package com.ph.shopping.facade.spm.dto;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 
* @ClassName: UserChargeBackDTO
* @Description: 用于易联支付回调后更新充值订单DTO
* @author 王强
* @date 2017年6月15日 上午11:43:56
 */
public class UserChargeBackDTO implements Serializable {

	private static final long serialVersionUID = -2277082980851329797L;
	
	private Integer chargeType;//充值方式
	
	private Integer chargeStatus;//充值状态
	
	private String responseCode;//交易状态
	
	private String orderNo;//订单号



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

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	

}
