package cm.ph.shopping.facade.order.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "ph_member_order_online")
public class PhMemberOrderOnline  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4159749956384436455L;
	@Id
	private Long id;
	@Column(name = "created_time")
	private Date created_time;
	@Column(name = "update_time")
	private Date update_time;
	@Column(name = "merchantId")
	private Long merchantId;//商户
	@Column(name = "memberId")
	private Long memberId;//会员
	@Column(name = "memberName")
	private String memberName;//收货人名称  快照
	@Column(name = "memberPhone")
	private String memberPhone;//收货人手机号 快照
	@Column(name = "message")
	private String message;//买家留言
	@Column(name = "payType")
	private Integer payType;//支付类型 
	@Column(name = "subscriptionMoney")
	private Long subscriptionMoney;//定金价
	@Column(name = "orderNo")
	private String orderNo;//订单号 
	@Column(name = "payTime")
	private Date payTime;//尾款支付时间
	@Column(name = "subscriptionTime")
	private Date subscriptionTime;//付定金时间
	@Column(name = "sendTime")
	private Date sendTime;//商户同意时间
	@Column(name = "status")
	private Integer status;//0待付款 1已付定金 2商户已接单 3尾款已支付 4已评价 5申请退款   6已退款 7已关闭
	@Column(name = "refundMsg")
	private String refundMsg;//用户退款留言
	@Column(name = "hunterRefundMsg")
	private String hunterRefundMsg;//商户退款留言
	@Column(name = "refundType")
	private Integer refundType;//退款类型  0是用户申请退款  1是过时商户退单
	@Column(name = "applyRefundTime")
	private Date applyRefundTime;//申请退款时间
	@Column(name = "refundTime")
	private Date refundTime;//退款时间
	@Column(name = "totalPrice")
	private Long totalPrice;//订单总价格（定金价格+尾款价格）
	@Column(name = "fenRunStatus")
	private Integer fenRunStatus;//订单分润状态 null 0未分润   1 已分润
	@Column(name = "puhuiRunStatus")
	private Integer puhuiRunStatus;//普惠返积分标记位
	@Column(name = "hopeServiceDate")
	private Date hopeServiceDate;//到店时间
	@Column(name = "predictServiceDate")
	private Date predictServiceDate;//预计离店时间
	@Column(name = "type")
	private int type;//  0、天天返  1、刮刮乐
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
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Long getSubscriptionMoney() {
		return subscriptionMoney;
	}
	public void setSubscriptionMoney(Long subscriptionMoney) {
		this.subscriptionMoney = subscriptionMoney;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public Date getSubscriptionTime() {
		return subscriptionTime;
	}
	public void setSubscriptionTime(Date subscriptionTime) {
		this.subscriptionTime = subscriptionTime;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRefundMsg() {
		return refundMsg;
	}
	public void setRefundMsg(String refundMsg) {
		this.refundMsg = refundMsg;
	}
	public String getHunterRefundMsg() {
		return hunterRefundMsg;
	}
	public void setHunterRefundMsg(String hunterRefundMsg) {
		this.hunterRefundMsg = hunterRefundMsg;
	}
	public Integer getRefundType() {
		return refundType;
	}
	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}
	public Date getApplyRefundTime() {
		return applyRefundTime;
	}
	public void setApplyRefundTime(Date applyRefundTime) {
		this.applyRefundTime = applyRefundTime;
	}
	public Date getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}
	public Long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getFenRunStatus() {
		return fenRunStatus;
	}
	public void setFenRunStatus(Integer fenRunStatus) {
		this.fenRunStatus = fenRunStatus;
	}
	public Integer getPuhuiRunStatus() {
		return puhuiRunStatus;
	}
	public void setPuhuiRunStatus(Integer puhuiRunStatus) {
		this.puhuiRunStatus = puhuiRunStatus;
	}
	public Date getHopeServiceDate() {
		return hopeServiceDate;
	}
	public void setHopeServiceDate(Date hopeServiceDate) {
		this.hopeServiceDate = hopeServiceDate;
	}
	public Date getPredictServiceDate() {
		return predictServiceDate;
	}
	public void setPredictServiceDate(Date predictServiceDate) {
		this.predictServiceDate = predictServiceDate;
	}
	
}
