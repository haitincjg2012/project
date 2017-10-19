package cm.ph.shopping.facade.order.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ph.shopping.common.core.base.BaseEntity;
/**
 * 
* @ClassName: PhOrderOnline
* @Description: 线上订单实体
* @author 王强
* @date 2017年4月25日 下午5:32:08
 */
@Table(name="ph_order_online")
public class PhOrderOnline extends BaseEntity {	
	private static final long serialVersionUID = 7677529328875177877L;
	

    /** 收货日期 */
    @Column(name="acceptDate")
    private Date acceptDate;

    /** 详细地址 */
    @Column(name="addrDetail")
    private String addrDetail;

    /** 会员地址id */
    @Column(name="addrId")
    private Long addrId;

    /** 代理商id */
    @Column(name="agnetId")
    private Long agnetId;

    /** 联系人 */
    private String contact;

    @JsonIgnore
    private BigDecimal cost;

    /** 下单日期 */
    @Column(name="deliveryDate")
    private Date deliveryDate;

    /** 物流名称 */
    @Column(name="logisticName")
    private String logisticName;

    /** 物流编号 */
    @Column(name="logisticNo")
    private String logisticNo;

    /** 会员id */
	@Column(name="memberId")
    private Long memberId;

    /** 商户id */
    @Column(name="merchantId")
    private Long merchantId;

    /** 数量 */
    private Integer num;

    /** 订单编号 */
    @Column(name="orderNo")
    private String orderNo;

    /** 付款日期 */
    @Column(name="payDate")
    private Date payDate;

    /** 付款金额 */
    @Column(name="payMoney")
    private Long payMoney;

    /** 支付方式 */
    @Column(name="payTtype")
    private Integer payTtype;

    /** 商品单价 */
    private Long price;

    /** 商品id */
	@Column(name="productId")
    private Long productId;

    /** 发货日期 */
    @Column(name="sendDate")
    private Date sendDate;

    /** 状态(1待付款，2待发货，3确认收货，4交易完成，5交易关闭, 6退款中，7已退货) */
    private Integer status;

    /** 供应商id */
    @Column(name="supplyerId")
    private Long supplyerId;
    
    /** 手机号 */
    @Column(name="telPhone")
    private String telPhone;
    
    /** 配送方式 */
    @Column(name="deliveryType")
    private int deliveryType;
    
    /** 提货点id*/
    @Column(name="pickPointId")
    private Long pickPointId; 
    
    /** 邮费*/
    private Long postage;
    
    private String md5;

	public Date getAcceptDate() {
        return acceptDate;
    }

	public String getAddrDetail() {
		return addrDetail;
	}

	public Long getAddrId() {
        return addrId;
    }

    public Long getAgnetId() {
        return agnetId;
    }

    public String getContact() {
        return contact;
    }

    public BigDecimal getCost() {
		return cost;
	}

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public String getLogisticName() {
        return logisticName;
    }

    public String getLogisticNo() {
        return logisticNo;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public Integer getNum() {
        return num;
    }

	public String getOrderNo() {
        return orderNo;
    }

	public Date getPayDate() {
        return payDate;
    }

    public Long getPayMoney() {
        return payMoney;
    }

    public Integer getPayTtype() {
        return payTtype;
    }

    public Long getPrice() {
		return price;
	}

    public Long getProductId() {
        return productId;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public Integer getStatus() {
        return status;
    }

    public Long getSupplyerId() {
        return supplyerId;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}

    public void setAddrId(Long addrId) {
        this.addrId = addrId;
    }

    public void setAgnetId(Long agnetId) {
        this.agnetId = agnetId;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setLogisticName(String logisticName) {
        this.logisticName = logisticName == null ? null : logisticName.trim();
    }

    public void setLogisticNo(String logisticNo) {
        this.logisticNo = logisticNo == null ? null : logisticNo.trim();
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public void setPayMoney(Long payMoney) {
        this.payMoney = payMoney;
    }

    public void setPayTtype(Integer payTtype) {
        this.payTtype = payTtype;
    }

    public void setPrice(Long price) {
		this.price = price;
	}

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = new Date();
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setSupplyerId(Long supplyerId) {
        this.supplyerId = supplyerId;
    }

	public void setTelPhone(String telPhone) {
        this.telPhone = telPhone == null ? null : telPhone.trim();
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

	public Long getPostage() {
		return postage;
	}

	public void setPostage(Long postage) {
		this.postage = postage;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
}