package cm.ph.shopping.facade.order.dto;

import java.io.Serializable;
import java.util.Date;

import com.ph.shopping.common.core.base.BaseEntityForToken;

public class PhMemberOrderUnlineDTOS extends BaseEntityForToken implements Serializable{

	    private static final long serialVersionUID = 1458052295728792001L;
	    /**
	     * id
	     */
	    private Long id;
	    /**
	     * 订单编号
	     */
	    private String orderNo;
	    /**
	     * 订单金额
	     */
	    private Double orderMoney;
	    
	    /**
	     * 订单金额
	     */
	    private Double guaMoney;
	    /**
	     * 状态(0=待付款；1=付款中；2=交易完成；3=交易取消（商户取消）)(原值：1支付完成，2退货)
	     */
	    private Byte status;
	    /**
	     * 支付方式0 =现金支付 , 1=积分支付, 2=支付宝支付, 3=微信支付(1 现金  2积分 3扫码支付)
	     */
	    private Byte payType;
	    /**
	     * 会员id
	     */
	    private Long memberId;
	    /**
	     * 备注
	     */
	    private String remark;
	    /**
	     * 商户id
	     */
	    private Long merchantId;
	    /**
	     * 服务费用
	     */
	    private Long serviceCost;
	    /**
	     * 是否结算（0=未结算；1=结算）
	     * 默认：0
	     */
	    private byte isSettle;
	    /**
	     * 是否已返积分（0=未返；1=已返）
	     * 默认：0
	     */
	    private byte isBackScore;
	    /**
	     * 微信支付账号
	     */
	    private String weChatAccount;
	    /**
	     * 取消订单时间
	     */
	    private Date cancelTime;
	    /**
	     * 取消订单原因
	     */
	    private String cancelReason;
	    /**
	     * 是否分润(0=未分润；1=已分润)
	     */
	    private byte isProfit;
	    /**
	     * 支付时间
	     */
	    private Date payTime;
	    /**
	     * 结算时间
	     */
	    private Date settleTime;
	    /**
	     * 分润时间
	     */
	    private Date profitTime;
	    /**
	     * 交易编码(会员第三方支付时交易码)
	     */
	    private String dealCode;
	    /**
	     * 条形码标识(会员扫码支付时的条形码)
	     */
	    private String barcodeMark;
	    /**
	     * 商户名称
	     */
	    private String merchantName;
	    /**
	     * 完成时间
	     */
	    private Date doneTime;
	    /**
	     * 商户电话号码
	     */
	    private String merchantPhone;
	    /**
	     * 会员电话号码
	     */
	    private String memberPhone;
	    /**
	     * 外码 条形码
	     */
	    private String outerCode;
	    
		private Integer type;//  0、天天返  1、刮刮乐
	    
	    private Date updateTime;

	    private Long createrId;

	    private Long updaterId;
	    
	    private Date createTime;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		public Double getOrderMoney() {
			return orderMoney;
		}

		public void setOrderMoney(Double orderMoney) {
			this.orderMoney = orderMoney;
		}

		public Double getGuaMoney() {
			return guaMoney;
		}

		public void setGuaMoney(Double guaMoney) {
			this.guaMoney = guaMoney;
		}

		public Byte getStatus() {
			return status;
		}

		public void setStatus(Byte status) {
			this.status = status;
		}

		public Byte getPayType() {
			return payType;
		}

		public void setPayType(Byte payType) {
			this.payType = payType;
		}

		public Long getMemberId() {
			return memberId;
		}

		public void setMemberId(Long memberId) {
			this.memberId = memberId;
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

		public Long getServiceCost() {
			return serviceCost;
		}

		public void setServiceCost(Long serviceCost) {
			this.serviceCost = serviceCost;
		}

		public byte getIsSettle() {
			return isSettle;
		}

		public void setIsSettle(byte isSettle) {
			this.isSettle = isSettle;
		}

		public byte getIsBackScore() {
			return isBackScore;
		}

		public void setIsBackScore(byte isBackScore) {
			this.isBackScore = isBackScore;
		}

		public String getWeChatAccount() {
			return weChatAccount;
		}

		public void setWeChatAccount(String weChatAccount) {
			this.weChatAccount = weChatAccount;
		}

		public Date getCancelTime() {
			return cancelTime;
		}

		public void setCancelTime(Date cancelTime) {
			this.cancelTime = cancelTime;
		}

		public String getCancelReason() {
			return cancelReason;
		}

		public void setCancelReason(String cancelReason) {
			this.cancelReason = cancelReason;
		}

		public byte getIsProfit() {
			return isProfit;
		}

		public void setIsProfit(byte isProfit) {
			this.isProfit = isProfit;
		}

		public Date getPayTime() {
			return payTime;
		}

		public void setPayTime(Date payTime) {
			this.payTime = payTime;
		}

		public Date getSettleTime() {
			return settleTime;
		}

		public void setSettleTime(Date settleTime) {
			this.settleTime = settleTime;
		}

		public Date getProfitTime() {
			return profitTime;
		}

		public void setProfitTime(Date profitTime) {
			this.profitTime = profitTime;
		}

		public String getDealCode() {
			return dealCode;
		}

		public void setDealCode(String dealCode) {
			this.dealCode = dealCode;
		}

		public String getBarcodeMark() {
			return barcodeMark;
		}

		public void setBarcodeMark(String barcodeMark) {
			this.barcodeMark = barcodeMark;
		}

		public String getMerchantName() {
			return merchantName;
		}

		public void setMerchantName(String merchantName) {
			this.merchantName = merchantName;
		}

		public Date getDoneTime() {
			return doneTime;
		}

		public void setDoneTime(Date doneTime) {
			this.doneTime = doneTime;
		}

		public String getMerchantPhone() {
			return merchantPhone;
		}

		public void setMerchantPhone(String merchantPhone) {
			this.merchantPhone = merchantPhone;
		}

		public String getMemberPhone() {
			return memberPhone;
		}

		public void setMemberPhone(String memberPhone) {
			this.memberPhone = memberPhone;
		}

		public String getOuterCode() {
			return outerCode;
		}

		public void setOuterCode(String outerCode) {
			this.outerCode = outerCode;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public Date getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}

		public Long getCreaterId() {
			return createrId;
		}

		public void setCreaterId(Long createrId) {
			this.createrId = createrId;
		}

		public Long getUpdaterId() {
			return updaterId;
		}

		public void setUpdaterId(Long updaterId) {
			this.updaterId = updaterId;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
	    
	    

}
