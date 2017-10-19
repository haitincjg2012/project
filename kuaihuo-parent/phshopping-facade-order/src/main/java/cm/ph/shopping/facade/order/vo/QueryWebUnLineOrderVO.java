package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;

public class QueryWebUnLineOrderVO implements Serializable {
	private static final long serialVersionUID = 8521049301719245483L;
	private long id;
	private String orderNo;
	private String merchantName;
	private String createTime;
	private String merchantTel;
	private Long payMoney1;
	private Double payMoney;
	private int status;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMerchantTel() {
		return merchantTel;
	}
	public void setMerchantTel(String merchantTel) {
		this.merchantTel = merchantTel;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getPayMoney1() {
		return payMoney1;
	}
	public void setPayMoney1(Long payMoney1) {
		this.payMoney1 = payMoney1;
	}
	public Double getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}
	
	
}
