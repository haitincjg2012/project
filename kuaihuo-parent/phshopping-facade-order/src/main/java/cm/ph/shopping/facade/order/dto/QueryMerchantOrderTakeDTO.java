package cm.ph.shopping.facade.order.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * @项目：phshopping-facade-order
 * @描述：商户提货单DTO
 * @作者：Mr.Dong
 * @创建时间：2017年3月30日
 * @Copyright @2017 by Mr.Dong
 */
public class QueryMerchantOrderTakeDTO implements Serializable{
	private static final long serialVersionUID = 6950132567015596003L;
	private Long id;
	private Long merchantId;
	private String  orderNo;
	private String supplierName;
	private String  productName;
	private Date deliveryDate;//下单时间
	private String memberName;
	private String memberPhone;
	private Long commodityAmount1;//金额
	private Long  postage1;//邮费
	private Long payMoney1;//订单总额
	private Double commodityAmount;//金额
	private Double  postage;//邮费
	private Double payMoney;//订单总额
	private String  status;//状态  2待提货    4已提货 
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
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public Long getCommodityAmount1() {
		return commodityAmount1;
	}
	public void setCommodityAmount1(Long commodityAmount1) {
		this.commodityAmount1 = commodityAmount1;
	}
	public Long getPostage1() {
		return postage1;
	}
	public void setPostage1(Long postage1) {
		this.postage1 = postage1;
	}
	public Long getPayMoney1() {
		return payMoney1;
	}
	public void setPayMoney1(Long payMoney1) {
		this.payMoney1 = payMoney1;
	}
	public Double getCommodityAmount() {
		return commodityAmount;
	}
	public void setCommodityAmount(Double commodityAmount) {
		this.commodityAmount = commodityAmount;
	}
	public Double getPostage() {
		return postage;
	}
	public void setPostage(Double postage) {
		this.postage = postage;
	}
	public Double getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
