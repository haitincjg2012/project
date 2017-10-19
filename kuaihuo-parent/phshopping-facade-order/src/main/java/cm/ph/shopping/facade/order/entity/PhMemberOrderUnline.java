package cm.ph.shopping.facade.order.entity;

import com.ph.shopping.common.core.base.BaseEntity;
import com.ph.shopping.common.core.base.BaseEntityForToken;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Date;

@Table(name = "ph_member_order_unline")
public class PhMemberOrderUnline  implements Serializable{
    private static final long serialVersionUID = -1201649270604922038L;
    /**
     * id
     */
    @Id
    private Long id;
    /**
     * 订单编号
     */
    @Column(name = "orderNo")
    private String orderNo;
    /**
     * 订单金额
     */
    @Column(name = "orderMoney")
    private Long orderMoney;
    
    /**
     * 订单金额
     */
    @Column(name = "guaMoney")
    private Long guaMoney;
    /**
     * 状态(0=待付款；1=付款中；2=交易完成；3=交易取消（商户取消）)(原值：1支付完成，2退货)
     */
    @Column(name = "status")
    private Byte status;
    /**
     * 支付方式0 =现金支付 , 1=积分支付, 2=支付宝支付, 3=微信支付(1 现金  2积分 3扫码支付)
     * 				9 远程支付 8 快捷支付（扫码）
     */
    @Column(name = "payType")
    private Byte payType;
    /**
     * 会员id
     */
    @Column(name = "memberId")
    private Long memberId;
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 商户id
     */
    @Column(name = "merchantId")
    private Long merchantId;
    /**
     * 服务费用
     */
    @Column(name = "serviceCost")
    private Long serviceCost;
    /**
     * 是否结算（0=未结算；1=结算）
     * 默认：0
     */
    @Column(name = "isSettle")
    private byte isSettle;
    /**
     * 是否已返积分（0=未返；1=已返）
     * 默认：0
     */
    @Column(name = "isBackScore")
    private byte isBackScore;
    /**
     * 微信支付账号
     */
    @Column(name = "weChatAccount")
    private String weChatAccount;
    /**
     * 取消订单时间
     */
    @Column(name = "cancelTime")
    private Date cancelTime;
    /**
     * 取消订单原因
     */
    @Column(name = "cancelReason")
    private String cancelReason;
    /**
     * 是否分润(0=未分润；1=已分润)
     */
    @Column(name = "isProfit")
    private byte isProfit;
    /**
     * 支付时间
     */
    @Column(name = "payTime")
    private Date payTime;
    /**
     * 结算时间
     */
    @Column(name = "settleTime")
    private Date settleTime;
    /**
     * 分润时间
     */
    @Column(name = "profitTime")
    private Date profitTime;
    /**
     * 交易编码(会员第三方支付时交易码)
     */
    @Column(name = "dealCode")
    private String dealCode;
    /**
     * 条形码标识(会员扫码支付时的条形码)
     */
    @Column(name = "barcodeMark")
    private String barcodeMark;
    /**
     * 商户名称
     */
    @Column(name ="merchantName")
    private String merchantName;
    /**
     * 完成时间
     */
    @Column(name="doneTime")
    private Date doneTime;
    /**
     * 商户电话号码
     */
    @Column(name = "merchantPhone")
    private String merchantPhone;
    /**
     * 会员电话号码
     */
    @Column(name = "memberPhone")
    private String memberPhone;
    /**
     * 外码 条形码
     */
    @Column(name = "outerCode")
    private String outerCode;
    
    @Column(name = "type")
	private Integer type;//  0、天天返  1、刮刮乐
    
    @Column(name = "updateTime")
    private Date updateTime;

    @Column(name = "createrId")
    private Long createrId;

    @Column(name = "updaterId")
    private Long updaterId;

    @Column(name = "createTime")
    private Date createTime;
    
   
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
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
        this.remark = remark == null ? null : remark.trim();
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

    public Long getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Long orderMoney) {
        this.orderMoney = orderMoney;
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

    public Long getGuaMoney() {
        return guaMoney;
    }

    public void setGuaMoney(Long guaMoney) {
        this.guaMoney = guaMoney;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


}
