package com.ph.shopping.facade.profit.dto;

import com.ph.shopping.common.util.page.PageBean;

/**
 * 供应链结算DTO
* @ClassName: PurchaseSettleDTO
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月9日 下午3:23:26
 */
public class PurchaseSettleDTO extends PageBean  {

	private static final long serialVersionUID = -5886308518656258903L;
	private String startDate;// 开始日期
	
	private String endDate;// 结束日期
	
	private String orderNo;// 订单号
	
	private String purchaserTel;// 进货人账号
	
	private String senderTel;// 发货人账号
	
	private byte isSettle;// 结算状态0未结算，1已结算，-1查询全部
	
	private byte payType;//支付方式    支付方式： 0-余额支付 1-第三方支付

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

	public String getPurchaserTel() {
		return purchaserTel;
	}

	public void setPurchaserTel(String purchaserTel) {
		this.purchaserTel = purchaserTel;
	}

	public String getSenderTel() {
		return senderTel;
	}

	public void setSenderTel(String senderTel) {
		this.senderTel = senderTel;
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
