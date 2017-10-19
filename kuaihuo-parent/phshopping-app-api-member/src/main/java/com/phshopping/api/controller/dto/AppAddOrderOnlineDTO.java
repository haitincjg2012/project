package com.phshopping.api.controller.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ph.shopping.common.core.base.BaseEntityForToken;
import com.ph.shopping.common.core.base.BaseValidate;

import cm.ph.shopping.facade.order.entity.Dish;
import cm.ph.shopping.facade.order.entity.PhMemberOrderOnlineSku;
/**
 * 
 * @ClassName:  AddOrderUnlineDTO   
 * @Description:添加线上订单DTO   
 * @author: 李治桦
 * @date:   2017年8月22日 下午5:39:04     
 * @Copyright: 2017
 */
public class AppAddOrderOnlineDTO extends  BaseEntityForToken {

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 商户ID
	 */
	@NotNull(message="[商户ID]不能为空")
	private Long merchantId;
	/**
	 * 会员ID
	 */
	@NotNull(message="[会员ID]不能为空")
	private Long memberId;//会员
	/**
	 * 买家留言
	 */
	private String message;
	/**
	 * 到店时间
	 */
	@NotNull(message="[到店时间]不能为空")
	private String hopeServiceDate;
	/**
	 * 预计离店时间
	 */
	private Date predictServiceDate;//
	/**
	 * 用户请求标识
	 */
	private String token;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getHopeServiceDate() {
		return hopeServiceDate;
	}
	public void setHopeServiceDate(String hopeServiceDate) {
		this.hopeServiceDate = hopeServiceDate;
	}
	public Date getPredictServiceDate() {
		return predictServiceDate;
	}
	public void setPredictServiceDate(Date predictServiceDate) {
		this.predictServiceDate = predictServiceDate;
	}

	
}
