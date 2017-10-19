package com.phshopping.api.controller.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ph.shopping.common.core.base.BaseValidate;
/**
 * 
 * @ClassName:  AddOrderUnlineDTO   
 * @Description:添加线下订单DTO   
 * @author: 李杰
 * @date:   2017年5月31日 下午5:39:04     
 * @Copyright: 2017
 */
public class AppAddOrderUnlineDTO extends BaseValidate {

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
	 * 订单金额
	 */
	@NotBlank(message="[订单金额]不能为空")
	private String orderMoney;
	/**
	 * 用户请求标识
	 */
	private String token;
	/**
	 * 条形码标识
	 */
	@NotBlank(message="[条形码标识]不能为空")
	private String barcodeMark;
	/**
	 * 添加商户名称
	 */
	@NotBlank(message="[商户名称]不能为空")
	private String merchantName;
	/**
	 * 支付方式 （1=积分支付, 2=支付宝支付, 3=微信支付）
	 */
	@NotNull(message="[支付方式]不能为空")
	private Byte payType;
	/**
	 * 支付密码
	 */
	private String payPwd;
	/**
	 * 金额签名(订单金额+商户ID+签名字符串)
	 */
	@NotBlank(message="[签名]不能为空")
	private String sign;
	
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getBarcodeMark() {
		return barcodeMark;
	}
	public void setBarcodeMark(String barcodeMark) {
		this.barcodeMark = barcodeMark;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Byte getPayType() {
		return payType;
	}
	public void setPayType(Byte payType) {
		this.payType = payType;
	}
	public String getPayPwd() {
		return payPwd;
	}
	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(String orderMoney) {
		this.orderMoney = orderMoney;
	}
}
