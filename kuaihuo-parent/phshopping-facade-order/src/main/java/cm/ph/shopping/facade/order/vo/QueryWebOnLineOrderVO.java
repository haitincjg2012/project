package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 
* @ClassName: QueryWebOnLineOrderVo
* @Description: 查询商城列表VO
* @author 王强
* @date 2017年4月25日 下午5:28:28
 */
public class QueryWebOnLineOrderVO implements Serializable {
	private static final long serialVersionUID = 5483541762898245780L;
	private long id;//主键id
	private String orderNo;//订单号
	private String supplierName;//供应商名称
	private String deliveryDate;//下单日期
	private String productName;//商品名称
	private String url;//图片路径
	private int num;//购买数量
	@JsonIgnore
	private long price1;//商品单价(处理前)
	private double price;//商品单价(处理后)
	private String contact;//联系人
	@JsonIgnore
	private long payMoney1;//订单总金额(处理前)
	private double payMoney;//订单总金额(处理后)
	private int status;//状态(1待付款，2待发货，3待收货，4已完成，5交易关闭，6退款中，7已退货)
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

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
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

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(double payMoney) {
		this.payMoney = payMoney;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getPrice1() {
		return price1;
	}

	public void setPrice1(long price1) {
		this.price1 = price1;
	}

	public long getPayMoney1() {
		return payMoney1;
	}

	public void setPayMoney1(long payMoney1) {
		this.payMoney1 = payMoney1;
	}

	public int getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(int deliveryType) {
		this.deliveryType = deliveryType;
	}
}
