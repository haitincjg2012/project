package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 
* @ClassName: QueryWebOnLineOrderDetailVo
* @Description: 查询商城订单详情VO
* @author 王强
* @date 2017年4月25日 下午5:19:45
 */
public class QueryWebOnLineOrderDetailVO implements Serializable {
	private static final long serialVersionUID = -1722124312759200968L;
	private long id;//主键id
	private String orderNo;//订单号
	private String contact;//联系人
	private String province;//省id
	private String city;//市id
	private String area;//区id
	private String address;//详细地址
	private String telPhone;//联系电话
	private String phoneNo;//座机号
	private int status;//状态(1待付款，2待发货，3待收货，4已完成，5交易关闭，6退款中，7已退货)
	private String productName;//商品名称
	private String url;//图片路径
	@JsonIgnore
	private long price1;//商品单价(处理前)
	private double price;//商品单价(处理后)
	@JsonIgnore
	private long payMoney1;//订单总金额(处理前)
	private double payMoney;//订单总金额(处理后)
	private int num;//购买数量
	@JsonIgnore
	private long postage1;//邮费(处理前)
	private double postage;//邮费(处理后)
	private String addressDetail;//详细地址
	
	private Date deliveryDate;//下单日期
	
	private Date payDate;//付款日期
	
	private Date sendDate;//发货日期
	
	private Date acceptDate;//收货日期
	
	private int deliveryType;//配送方式(1送货到家，2自提商品)

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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
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

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
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

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	public int getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(int deliveryType) {
		this.deliveryType = deliveryType;
	}
}
