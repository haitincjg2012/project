package com.ph.shopping.facade.pay.vo;

import java.io.Serializable;

/**
 * @项目：phshopping-facade-pay
 *
 * @描述：易联支付订单查询vo
 *
 * @作者： Mr.chang
 *
 * @创建时间：2017年3月29日
 *
 * @Copyright @2017 by Mr.chang
 */
public class PayecoOrderVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2589345008663623740L;
	
	private String orderNum;//订单号
	private Long score;//金额
	private String md5Str;//MD5密文
	
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public Long getScore() {
		return score;
	}
	public void setScore(Long score) {
		this.score = score;
	}
	public String getMd5Str() {
		return md5Str;
	}
	public void setMd5Str(String md5Str) {
		this.md5Str = md5Str;
	}

}
