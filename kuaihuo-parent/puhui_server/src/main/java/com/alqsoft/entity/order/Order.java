package com.alqsoft.entity.order;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.industryassociation.IndustryAssociation;
import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.product.Product;
import com.alqsoft.entity.productspecification.ProductSpecification;
import com.alqsoft.entity.producttype.ProductType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Date:     2017年2月27日 下午5:16:20 <br/>
 * @author   zhangcan
 * @version  
 * @since    JDK 1.8
 * @see
 * @used
 */
@Entity
@Table(name = "alq_order", indexes = {})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
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
	
	private String productName;//商品名称
	
	private Long productSalePrice;//商品销售价格
	
	private String productSpecificationName;//商品规格名称
	
	private String productTypeName;//商品分类名称
	
	
	private String message;//买家留言
	
	private Integer payType;//支付类型 0预付定金 1全款
	
	private Long subscriptionMoney;//定金价
	
	private String orderNo;//订单号
	
	private String orderSubNo;//子订单号
	
	private Long serviceMoney;//服务费
	
	private Date payTime;//付款时间
	
	private Date subscriptionTime;//付定金时间

	private Date sendTime;//发货时间
	
	private Date receiveTime;//收货时间(尾款支付时间)
	
	private Long negotiatePrice;//协商价格（尾款价格）
	
	private Integer negotiatePriceStatus;//0发货之后未输入  1 已输入待确认   2被驳回需重输  3已通过 此时维护negotiatePrice字段  再次单机付尾款则直接让其支付
	
	private Long actualMoney;//买家实际付款
	
	private Integer status;//0代付款 1已付定金 2待发货 3待收货 4已评价 5申请退款   6已退款 7已收货 8 已取消

	private String refundMsg;//退款留言
	
	private String hunterRefundMsg;//批发商退款留言
	
	private Integer refundType;//退款类型  0是用户申请退款  1是批发商直接退款
	
	private Date applyRefundTime;//申请退款时间
	
	private Date refundTime;//退款时间
	
	private Integer num;//商品购买总数
	
	private Long totalPrice;//改商品的订单价格（定金价格+尾款价格）
	
	private Long directHunterFen;//直营批发商分润
	
	private Long recommendHunterFen;//推荐批发商分润
	
	private Long industryFen;//行业分润
	
	private Long puhuiFen;//普惠基金
	
	private Long agentFen;//代理分润
	
	private Long kuhuoFen;//快火分润
	
	private Long manageFen;//管理费

	private Long promoterFen;//推荐人分润

	private Long serviceFen;//服务费  8%
	
	private Long directHunterAfterMoney;//直营批发商结算后金额
	
	private Long recommendHunterAfterMoney;//推荐批发商结算后金额
	
	private Long industryAfterMoney;//行业分润计算后金额
	
	private String serialNum;//微信支付流水号
	
	private Integer fenRunStatus;//订单分润状态 null 0未分润   1 已分润
	
	private Integer puhuiRunStatus;//普惠分润标记位
	
	private Date hopeServiceDate;//期望送达时间
	
	private Date predictServiceDate;//预计送达时间

	private Date agreeTime;//接单时间

	private Date profitTimel;//分润时间

	private Long agentId;//代理Id

	public Long getPromoterFen() {
		return promoterFen;
	}

	public void setPromoterFen(Long promoterFen) {
		this.promoterFen = promoterFen;
	}

	public Long getServiceFen() {
		return serviceFen;
	}

	public void setServiceFen(Long serviceFen) {
		this.serviceFen = serviceFen;
	}

	public Date getAgreeTime() {
		return agreeTime;
	}

	public void setAgreeTime(Date agreeTime) {
		this.agreeTime = agreeTime;
	}

	public Date getProfitTimel() {
		return profitTimel;
	}

	public void setProfitTimel(Date profitTimel) {
		this.profitTimel = profitTimel;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public Long getPromoterId() {
		return promoterId;
	}

	public void setPromoterId(Long promoterId) {
		this.promoterId = promoterId;
	}

	private Long promoterId;//推荐人Id
	

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

	public Integer getNegotiatePriceStatus() {
		return negotiatePriceStatus;
	}

	public void setNegotiatePriceStatus(Integer negotiatePriceStatus) {
		this.negotiatePriceStatus = negotiatePriceStatus;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getProductSalePrice() {
		return productSalePrice;
	}

	public void setProductSalePrice(Long productSalePrice) {
		this.productSalePrice = productSalePrice;
	}

	public String getProductSpecificationName() {
		return productSpecificationName;
	}

	public void setProductSpecificationName(String productSpecificationName) {
		this.productSpecificationName = productSpecificationName;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public Long getPuhuiFen() {
		return puhuiFen;
	}

	public void setPuhuiFen(Long puhuiFen) {
		this.puhuiFen = puhuiFen;
	}

	public Integer getPuhuiRunStatus() {
		return puhuiRunStatus;
	}

	public void setPuhuiRunStatus(Integer puhuiRunStatus) {
		this.puhuiRunStatus = puhuiRunStatus;
	}

	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}

	public String getOrderSubNo() {
		return orderSubNo;
	}

	public void setOrderSubNo(String orderSubNo) {
		this.orderSubNo = orderSubNo;
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

	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "hunter_id")
	public Hunter getHunter() {
		return hunter;
	}

	public void setHunter(Hunter hunter) {
		this.hunter = hunter;
	}
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "industry_association_id")
	public IndustryAssociation getIndustryAssociation() {
		return industryAssociation;
	}

	public void setIndustryAssociation(IndustryAssociation industryAssociation) {
		this.industryAssociation = industryAssociation;
	}
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_type_id")
	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_specification_id")
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
	@Temporal(TemporalType.TIMESTAMP)
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
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

	public Integer getFenRunStatus() {
		return fenRunStatus;
	}

	public void setFenRunStatus(Integer fenRunStatus) {
		this.fenRunStatus = fenRunStatus;
	}

	public Date getSubscriptionTime() {
		return subscriptionTime;
	}

	public void setSubscriptionTime(Date subscriptionTime) {
		this.subscriptionTime = subscriptionTime;
	}

	public Long getAgentFen() {
		return agentFen;
	}

	public void setAgentFen(Long agentFen) {
		this.agentFen = agentFen;
	}

	public Long getKuhuoFen() {
		return kuhuoFen;
	}

	public void setKuhuoFen(Long kuhuoFen) {
		this.kuhuoFen = kuhuoFen;
	}

	public Long getManageFen() {
		return manageFen;
	}

	public void setManageFen(Long manageFen) {
		this.manageFen = manageFen;
	}
}

