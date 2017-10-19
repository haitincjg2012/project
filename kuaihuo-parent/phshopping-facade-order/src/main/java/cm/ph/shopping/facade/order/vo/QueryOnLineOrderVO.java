package cm.ph.shopping.facade.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class QueryOnLineOrderVO implements Serializable {
	
	private static final long serialVersionUID = 1375441115324624047L;

	private long id;//编号
	
	private String orderNo;//订单号
	
	private long supplierId;//供应商id
	
	private String supplierName;//供应商名称
	
	private String supplyerTel;//供应商电话
	
	private String productName;//商品名称
	
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date deliveryDate;//下单时间
	
	private String contact;//联系人
	
	private String telPhone;//电话号码

	private double price;//商品金额(处理后)
	
	private long price1;//商品金额(处理前)
	
	private int num;//数量
	
	private double postage;//物流费用(处理后)
	
	private long postage1;//邮费(处理前)
	
	private double payMoney;//订单总金额(处理后)

	private long payMoney1;//订单总金额(处理前)

	private String addrDetail;//详细地址

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

	public String getSupplyerTel() {
		return supplyerTel;
	}

	public void setSupplyerTel(String supplyerTel) {
		this.supplyerTel = supplyerTel;
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getPrice1() {
		return price1;
	}

	public void setPrice1(long price1) {
		this.price1 = price1;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getPostage() {
		return postage;
	}

	public void setPostage(double postage) {
		this.postage = postage;
	}

	public long getPostage1() {
		return postage1;
	}

	public void setPostage1(long postage1) {
		this.postage1 = postage1;
	}

	public double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(double payMoney) {
		this.payMoney = payMoney;
	}

	public long getPayMoney1() {
		return payMoney1;
	}

	public void setPayMoney1(long payMoney1) {
		this.payMoney1 = payMoney1;
	}

	public String getAddrDetail() {
		return addrDetail;
	}

	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	/*private int payTtype;

	private long supplyerId;

	private long merchantId;

	private long agnetId;*/

}
