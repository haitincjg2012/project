
package com.alqsoft.entity.order;

import java.util.Date;

import org.alqframework.orm.hibernate.IdEntity;

import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.industryassociation.IndustryAssociation;
import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.product.Product;
import com.alqsoft.entity.productspecification.ProductSpecification;
import com.alqsoft.entity.producttype.ProductType;

/**
 * Date:     2017年2月27日 下午5:16:20 <br/>
 * @author   zhangcan
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */

public class Order extends IdEntity{
	
private Product product;//商品表
	
	private Hunter hunter;//批发商
	
	private IndustryAssociation industryAssociation;//行业协会
	
	private Member member;//会员
	
	private ProductType productType;//商品类型
	
	private ProductSpecification productSpecification;//规格

	private String receiveName;//收货人名称  快照
	
	private String receivePhone;//收货人手机号 快照
	
	private String receiveAddress;//收货人地址 快照
	
	
	private String message;//买家留言
	
	private Integer payType;//支付类型 0预付定金 1全款
	
	private Long subscriptionMoney;//定金价
	
	private String orderNo;//订单号
	
	private String orderSubNo;//子订单号
	
	private Long serviceMoney;//服务费
	
	private Date payTime;//付款时间
	
	private Date sendTime;//发货时间
	
	private Date receiveTime;//收货时间
	
	private Long negotiatePrice;//协商价格（尾款价格）
	
	private Integer negotiatePriceStatus;//0发货之后未输入  1 已输入待确认   2被驳回需重输  3已通过 此时维护negotiatePrice字段  再次单机付尾款则直接让其支付
	
	private Long actualMoney;//买家实际付款
	
	private Integer status;//0代付款 1已付定金 2待发货 3待收货 4已评价 5申请退款   6已退款 7已收货 8已取消

	private String refundMsg;//退款留言
	
	private String hunterRefundMsg;//批发商退款留言

	private Integer refundType;//退款类型  0是用户申请退款  1是批发商直接退款

	private Date applyRefundTime;//申请退款时间

	private Date refundTime;//退款时间

	private Integer num;//商品购买总数

	private Long totalPrice;//改商品的订单价格

	private Long directHunterFen;//直营批发商分润

	private Long recommendHunterFen;//推荐批发商分润

	private Long industryFen;//行业分润

	private Long directHunterAfterMoney;//直营批发商结算后金额

	private Long recommendHunterAfterMoney;//推荐批发商结算后金额

	private Long industryAfterMoney;//行业分润计算后金额

	private String serialNum;//微信支付流水号

	private Integer fenRunStatus;//订单分润状态 null 0未分润   1 已分润
	
	private Date hopeServiceDate;//期望送达时间
	
	private Date predictServiceDate;//预计送达时间
	
	public Integer getNegotiatePriceStatus() {
		return negotiatePriceStatus;
	}

	public void setNegotiatePriceStatus(Integer negotiatePriceStatus) {
		this.negotiatePriceStatus = negotiatePriceStatus;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Hunter getHunter() {
		return hunter;
	}

	public void setHunter(Hunter hunter) {
		this.hunter = hunter;
	}
	
	public IndustryAssociation getIndustryAssociation() {
		return industryAssociation;
	}

	public void setIndustryAssociation(IndustryAssociation industryAssociation) {
		this.industryAssociation = industryAssociation;
	}
	
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	
	public ProductSpecification getProductSpecification() {
		return productSpecification;
	}

	public void setProductSpecification(ProductSpecification productSpecification) {
		this.productSpecification = productSpecification;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getReceivePhone() {
		return receivePhone;
	}

	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
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

	public Long getServiceMoney() {
		return serviceMoney;
	}

	public void setServiceMoney(Long serviceMoney) {
		this.serviceMoney = serviceMoney;
	}
	
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Long getNegotiatePrice() {
		return negotiatePrice;
	}

	public void setNegotiatePrice(Long negotiatePrice) {
		this.negotiatePrice = negotiatePrice;
	}

	public Long getActualMoney() {
		return actualMoney;
	}

	public void setActualMoney(Long actualMoney) {
		this.actualMoney = actualMoney;
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

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getOrderSubNo() {
		return orderSubNo;
	}

	public void setOrderSubNo(String orderSubNo) {
		this.orderSubNo = orderSubNo;
	}

	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}

	public String getHunterRefundMsg() {
		return hunterRefundMsg;
	}

	public void setHunterRefundMsg(String hunterRefundMsg) {
		this.hunterRefundMsg = hunterRefundMsg;
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

	public Long getDirectHunterFen() {
		return directHunterFen;
	}

	public void setDirectHunterFen(Long directHunterFen) {
		this.directHunterFen = directHunterFen;
	}

	public Long getRecommendHunterFen() {
		return recommendHunterFen;
	}

	public void setRecommendHunterFen(Long recommendHunterFen) {
		this.recommendHunterFen = recommendHunterFen;
	}

	public Long getIndustryFen() {
		return industryFen;
	}

	public void setIndustryFen(Long industryFen) {
		this.industryFen = industryFen;
	}

	public Long getDirectHunterAfterMoney() {
		return directHunterAfterMoney;
	}

	public void setDirectHunterAfterMoney(Long directHunterAfterMoney) {
		this.directHunterAfterMoney = directHunterAfterMoney;
	}

	public Long getRecommendHunterAfterMoney() {
		return recommendHunterAfterMoney;
	}

	public void setRecommendHunterAfterMoney(Long recommendHunterAfterMoney) {
		this.recommendHunterAfterMoney = recommendHunterAfterMoney;
	}

	public Long getIndustryAfterMoney() {
		return industryAfterMoney;
	}

	public void setIndustryAfterMoney(Long industryAfterMoney) {
		this.industryAfterMoney = industryAfterMoney;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public Integer getFenRunStatus() {
		return fenRunStatus;
	}

	public void setFenRunStatus(Integer fenRunStatus) {
		this.fenRunStatus = fenRunStatus;
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

