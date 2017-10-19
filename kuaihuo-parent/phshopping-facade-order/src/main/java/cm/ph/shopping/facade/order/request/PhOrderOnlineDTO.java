package cm.ph.shopping.facade.order.request;


import java.io.Serializable;
/**
 * 
* @ClassName: PhOrderOnlineDTO
* @Description: 下单请求DTO
* @author 王强
* @date 2017年4月25日 下午5:47:10
 */
public class PhOrderOnlineDTO implements Serializable {

	private static final long serialVersionUID = 8307690606358747483L;

	/** 商品单价 */
	private double price;

	/** 付款金额 */
	private double payMoney;

	/** 邮费 */
	private double postage;

	/** 详细地址 */
	private String addrDetail;

	/** 会员地址id */
	private Long addrId;

	/** 联系人 */
	private String contact;

	/** 会员id */
	private Long memberId;

	/** 数量 */
	private Integer num;

	/** 商品id */
	private Long productId;

	/** 手机号 */
	private String telPhone;

	/** 配送方式 */
	private int deliveryType;

	/** 提货点id */
	private Long pickPointId;
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(double payMoney) {
		this.payMoney = payMoney;
	}

	public double getPostage() {
		return postage;
	}

	public void setPostage(double postage) {
		this.postage = postage;
	}

	public String getAddrDetail() {
		return addrDetail;
	}

	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}

	public Long getAddrId() {
		return addrId;
	}

	public void setAddrId(Long addrId) {
		this.addrId = addrId;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public int getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(int deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Long getPickPointId() {
		return pickPointId;
	}

	public void setPickPointId(Long pickPointId) {
		this.pickPointId = pickPointId;
	}
}