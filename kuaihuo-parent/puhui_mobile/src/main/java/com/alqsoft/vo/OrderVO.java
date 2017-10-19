package com.alqsoft.vo;

import java.util.Date;

/**
 *  订单vo
 * @author Xuejizheng
 * @date 2017-03-06 11:14
 */
public class OrderVO {

    private Long id;//订单ID
    private Long   productId;   //商品ID
    private Long   hunterId;  //批发商ID
    private Long industryAssociationId;  //行业协会ID
    private Long memberId;//会员ID
    private Long   productTypeId;   //商品分类
    private Long productSpecificationId;//商品规格ID
    private String productStatus;//商品状态
    private Date createdTime;//订单创建时间
    private Date updateTime;//订单更新时间

    private String receiveName;//收货人名称  快照

    private String receivePhone;//收货人手机号 快照

    private String receiveAddress;//收货人地址 快照

    private String message;//买家留言

    private Integer payType;//支付类型 0预付定金 1全款

    private Long subscriptionMoney;//定金价

    private String orderNo;//订单号
    private String orderSubNo;//子订单编号

    private Long serviceMoney;//服务费

    private Date payTime;//付款时间
    private Date subscriptionTime;//预付定金时间

    private Date sendTime;//发货时间

    private Date receiveTime;//收货时间

    private Long negotiatePrice;//协商价格

    private Long actualMoney;//买家实际付款

    private Integer negotiatePriceStatus;//尾款状态

    private Integer status;//0代付款 1已付定金 2待发货 3待收货 4已评价 5退款

    private String refundMsg;//退款留言
    private Integer refundType;//退款类型
    /* 2017.03.20添加*/
    private String hunterRefundMsg;//批发商退款留言
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
    /*2017.03.23 添加*/
    private Long puhuiFen;//普惠分润
    private Integer puhuiRunStatus;//普惠分润标记位
    private String productName;//商品名称
    private Long productSalePrice;//商品销售价格
    private String productSpecificationName;//商品规格名称
    private String productTypeName;//商品分类名称

    private String imgAddress; //图片地址
    
    /*2017-07-18*/
    private Date hopeServiceDate;//期望送达时间
    private Date predictServiceDate;//预期送达时间

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getHunterId() {
        return hunterId;
    }

    public void setHunterId(Long hunterId) {
        this.hunterId = hunterId;
    }

    public Long getIndustryAssociationId() {
        return industryAssociationId;
    }

    public void setIndustryAssociationId(Long industryAssociationId) {
        this.industryAssociationId = industryAssociationId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Long getProductSpecificationId() {
        return productSpecificationId;
    }

    public void setProductSpecificationId(Long productSpecificationId) {
        this.productSpecificationId = productSpecificationId;
    }

    public Integer getNegotiatePriceStatus() {
        return negotiatePriceStatus;
    }

    public void setNegotiatePriceStatus(Integer negotiatePriceStatus) {
        this.negotiatePriceStatus = negotiatePriceStatus;
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

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
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

    public Integer getNum() {
        return num;
    }

    public String getHunterRefundMsg() {
        return hunterRefundMsg;
    }

    public void setHunterRefundMsg(String hunterRefundMsg) {
        this.hunterRefundMsg = hunterRefundMsg;
    }

    public Date getSubscriptionTime() {
        return subscriptionTime;
    }

    public void setSubscriptionTime(Date subscriptionTime) {
        this.subscriptionTime = subscriptionTime;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
