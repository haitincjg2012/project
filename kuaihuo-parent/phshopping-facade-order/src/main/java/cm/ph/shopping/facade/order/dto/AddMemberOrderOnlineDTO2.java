package cm.ph.shopping.facade.order.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.ph.shopping.common.core.base.BaseEntityForToken;

/**
 * @项目：phshopping-facade-order
 * @描述：创建订单
 * @作者： lzh
 * @创建时间： 14:22 2017/8/22
 */
public class AddMemberOrderOnlineDTO2 extends  BaseEntityForToken implements Serializable{
    /**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -3385182392083477336L;

	private Long id;
	/**
     * 收货人手机
     */
    private String memberTelphone;
    /**
     * 后台商户id（注意是ph_permission_common_user登录信息表中的id）
     */
    @NotNull(message = "[商户id]不能为空")
    private Long merchantId;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 留言
     */
    private String message;
	/**
	 * 订单号
	 */
    private String orderNo;
    /**
     * 订单号id
     */
    private Long orderOnlineId;
    /**
     * 订单状态
     */
    private Integer status;
   
    /**
     * 预计到达时间
     * @return
     */
    private Date hopeServiceDate;
    /**
     * 预计离店时间
     */
    private Date predictServiceDate;
    /**
     * 订单总价
     */
	private Long totalPrice;
	/**
	 * 销量
	 */
	private Long saleNum;
	/**
	 * 商品ID
	 */
	private Long dishId;
	/**
	 * 商户名
	 */
	private String merchantName;
	
	private Date createTime;
	
	private Integer type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getOrderOnlineId() {
		return orderOnlineId;
	}

	public void setOrderOnlineId(Long orderOnlineId) {
		this.orderOnlineId = orderOnlineId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public String getMemberTelphone() {
		return memberTelphone;
	}

	public void setMemberTelphone(String memberTelphone) {
		this.memberTelphone = memberTelphone;
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

	public Long getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Long saleNum) {
		this.saleNum = saleNum;
	}

	public Long getDishId() {
		return dishId;
	}

	public void setDishId(Long dishId) {
		this.dishId = dishId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


    
}
