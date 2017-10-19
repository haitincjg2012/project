package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 
* @ClassName: QueryOnLineOrderDetailVo
* @Description: 线上订单详情VO
* @author 王强
* @date 2017年4月25日 下午5:07:44
 */
public class QueryOnLineOrderDetailVO implements Serializable {
	private static final long serialVersionUID = 49779340443724099L;

	private int status;//状态(1待付款，2待发货，3待收货，4已完成，5交易关闭，6退款中，7已退货)

	private long memberId;//会员id

	private String memberName;//会员名称
	
	private String memberPhone;//会员电话

	private String contact;//联系人

	private String telPhone;//联系电话

	private String addrDetail;//详细地址

	private long supplierId;//供应商id

	private String supplierName;//供应商名称

	private String personName;//推荐人名称

	private String personTel;//推荐人电话

	private String address;// 发货地址

	private String orderNo;//订单号

	private long productId;//商品id

	private String productName;//商品名称

	private int num;//购买数量

	private long price1;//商品单价(处理前)

	private double price;//商品单价(处理后)

	private double totalMoney;//总价(处理后)

	private double productMoney;//商品总价(处理后)

	private long postage1;//邮费(处理前)

	private double postage;//邮费(处理后)

	private long payMoney1;//订单总金额(处理前)

	private double payMoney;//订单总金额(处理后)

	private String logisticName;//物流名称

	private String logisticNo;//物流编号

	private Date deliveryDate;//发货日期

	private Date payDate;//付款日期

	private Date acceptDate;//收货日期

	private Date sendDate;//发货日期

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
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

	public String getAddrDetail() {
		return addrDetail;
	}

	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonTel() {
		return personTel;
	}

	public void setPersonTel(String personTel) {
		this.personTel = personTel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public long getPrice1() {
		return price1;
	}

	public void setPrice1(long price1) {
		this.price1 = price1;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public double getProductMoney() {
		return productMoney;
	}

	public void setProductMoney(double productMoney) {
		this.productMoney = productMoney;
	}

	public long getPostage1() {
		return postage1;
	}

	public void setPostage1(long postage1) {
		this.postage1 = postage1;
	}

	public double getPostage() {
		return postage;
	}

	public void setPostage(double postage) {
		this.postage = postage;
	}

	public long getPayMoney1() {
		return payMoney1;
	}

	public void setPayMoney1(long payMoney1) {
		this.payMoney1 = payMoney1;
	}

	public double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(double payMoney) {
		this.payMoney = payMoney;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getLogisticName() {
		return logisticName;
	}

	public void setLogisticName(String logisticName) {
		this.logisticName = logisticName;
	}

	public String getLogisticNo() {
		return logisticNo;
	}

	public void setLogisticNo(String logisticNo) {
		this.logisticNo = logisticNo;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

}
