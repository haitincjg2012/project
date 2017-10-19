package com.ph.shopping.facade.profit.dto;

import com.ph.shopping.common.util.page.PageBean;

/**
 * 
 * 线下订单结算DTO
* @ClassName: UnLineSettleDTO
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月7日 上午11:32:58
 */
public class UnLineSettleDTO extends PageBean {

	private static final long serialVersionUID = 5580625129410271915L;
	private String startDate;// 开始日期
	private String endDate;// 结束日期
	private String orderNo;// 订单号
	private String merchantTel;// 商户账号
	private String memberTel;// 会员账号
	private byte isSettle;// 结算状态0未结算，1已结算，-1查询全部
	private byte payType;//支付方式    支付方式0 =现金 , 1=积分, 2=支付宝支付, 3=微信支付
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getMerchantTel() {
		return merchantTel;
	}
	public void setMerchantTel(String merchantTel) {
		this.merchantTel = merchantTel;
	}
	public String getMemberTel() {
		return memberTel;
	}
	public void setMemberTel(String memberTel) {
		this.memberTel = memberTel;
	}
	public byte getIsSettle() {
		return isSettle;
	}
	public void setIsSettle(byte isSettle) {
		this.isSettle = isSettle;
	}
	public byte getPayType() {
		return payType;
	}
	public void setPayType(byte payType) {
		this.payType = payType;
	}
	
	
	
}
