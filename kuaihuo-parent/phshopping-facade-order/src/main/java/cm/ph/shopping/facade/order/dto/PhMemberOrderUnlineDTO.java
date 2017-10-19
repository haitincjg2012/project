package cm.ph.shopping.facade.order.dto;

import java.io.Serializable;
import java.util.Date;

import com.ph.shopping.common.core.base.BaseEntityForToken;

/**
 * @项目：phshopping-facade-order
 * @描述：线下订单页面数据对接对象
 * @作者： 张霞
 * @创建时间： 14:56 2017/5/27
 * @Copyright @2017 by zhangxia
 */
public class PhMemberOrderUnlineDTO extends BaseEntityForToken implements Serializable {
    private static final long serialVersionUID = 1458052295728792001L;
    private Long id;//主键
    /** 订单编号 */
    private String orderNo;

    /** 订单金额 */
    private Double orderMoney;

    /** 服务费用 订单金额的 */
    private Double serviceCost;

    /** 状态(0=待付款；1=付款中；2=交易完成；3=交易取消（商户取消）)(原值：1支付完成，2退货) */
    private Byte status;

    /** 支付方式0 =现金支付 , 1=积分支付, 2=支付宝支付, 3=微信支付(1 现金  2积分 3扫码支付) */
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
    private String personTel;

    /** 会员卡号 */
    private String memberCardNo;
    /**
     * 支付密码(现金余额支付)
     */
    private String password;
    /**
     * 验证码(积分支付)
     */
    private String validateNo;

    /** 会员联系人名字 */
    private String memberName;

    /** 会员联系人电话 */
    private String memberPhone;
    /**
     * 下单时间
     */
    private String createTime;

    /** 省 */
    private String provinceId;

    /** 市 */
    private String cityId;

    /** 区 */
    private String countyId;

    /** 社区 */
    private String townId;

    /**
     * 开始时间
     */
    private String starTime;
    /**
     * 结束时间
     */
    private String endTime;
    
    /**
     * 支付类型  String类型
     */
    private String payTypeString;
    
    public String getPayTypeString() {
		return payTypeString;
	}

	public void setPayTypeString(String payTypeString) {
		this.payTypeString = payTypeString;
	}

	private int index;  //分页
    private int pageSize; //分页条数
    
    private Integer type;
    
    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

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

    public Double getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(Double serviceCost) {
        this.serviceCost = serviceCost;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
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

    public String getPersonTel() {
        return personTel;
    }

    public void setPersonTel(String personTel) {
        this.personTel = personTel;
    }

    public String getMemberCardNo() {
        return memberCardNo;
    }

    public void setMemberCardNo(String memberCardNo) {
        this.memberCardNo = memberCardNo;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getTownId() {
        return townId;
    }

    public void setTownId(String townId) {
        this.townId = townId;
    }

    public String getStarTime() {
        return starTime;
    }

    public void setStarTime(String starTime) {
        this.starTime = starTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidateNo() {
        return validateNo;
    }

    public void setValidateNo(String validateNo) {
        this.validateNo = validateNo;
    }
}
