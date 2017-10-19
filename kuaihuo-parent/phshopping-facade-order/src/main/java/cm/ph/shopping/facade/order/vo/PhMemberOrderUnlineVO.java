package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * @项目：phshopping-facade-order
 * @描述：线下订单返回页面交互数据
 * @作者： 张霞
 * @创建时间： 14:14 2017/5/27
 * @Copyright @2017 by zhangxia
 */
public class PhMemberOrderUnlineVO implements Serializable {
	private static final long serialVersionUID = -1201649270604922038L;

	private Long id;//主键
    /** 订单编号 */
    private String orderNo;

    /** 订单金额 */
    private Double orderMoney;
    
    /** 服务费用 订单金额的 */
    private Double serviceCost;

    /** 状态(0=待付款；1=付款中；2=交易完成；3=交易取消（商户取消）)(原值：1支付完成，2退货) */
    private Integer status;

    /** 支付方式0 =现金支付 , 1=积分支付, 2=支付宝支付, 3=微信支付(1 现金  2积分 3扫码支付)*/
    private Integer payType;

    /** 会员id */
    private Long memberId;

    /** 付款日期 */
    private Date payDate;

    /** 备注 */
    private String remark;

    /** 商户id */
    private Long merchantId;

    /** 商品单价 */
    private Long price;

    /** 供应商id */
    private Long supplyerId;

    /** 代理商id */
    private Long agentId;
    
    /** 商户名称 */
    private String merchantName;
    
    /** 商户联系人 */
    private String  personName;
    
    /** 商户联系人电话 */
    private String merchantTelPhone;
    
    /** 会员卡号 */
    private String memberCard;
    
    /** 会员联系人名字 */
    private String memberName;
    
    /** 会员联系人电话 */
    private String memberTelPhone;
    
    private String createTime;//下单时间
	/**
	 * 是否已分润
	 */
	private Integer isProfit;
	/**
	 * 是否已结算
	 */
	private Integer isSettle;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}



	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getSupplyerId() {
		return supplyerId;
	}

	public void setSupplyerId(Long supplyerId) {
		this.supplyerId = supplyerId;
	}



	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}


	public String getMemberCard() {
		return memberCard;
	}

	public void setMemberCard(String memberCard) {
		this.memberCard = memberCard;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}


	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Double getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}

	public Double getServiceCost() {
		return serviceCost;
	}

	public void setServiceCost(Double serviceCost) {
		this.serviceCost = serviceCost;
	}

	public String getMerchantTelPhone() {
		return merchantTelPhone;
	}

	public void setMerchantTelPhone(String merchantTelPhone) {
		this.merchantTelPhone = merchantTelPhone;
	}

	public String getMemberTelPhone() {
		return memberTelPhone;
	}

	public void setMemberTelPhone(String memberTelPhone) {
		this.memberTelPhone = memberTelPhone;
	}

	public Integer getIsProfit() {
		return isProfit;
	}

	public void setIsProfit(Integer isProfit) {
		this.isProfit = isProfit;
	}

	public Integer getIsSettle() {
		return isSettle;
	}

	public void setIsSettle(Integer isSettle) {
		this.isSettle = isSettle;
	}
}
