package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author: 王雪洋
 * @project: phshopping-facade-order
 * @ClassName: OrderOnlineVO
 * @Date: 2017年8月22日下午2:22:41
 * @Desc:订单信息列表展示
 */
public class OrderOnlineVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单id
	 */
	private Long id;
	/**
	 * 预计到店时间
	 */
	private String hopeServiceDate;
	/**
	 * 会员图片
	 */
	private String memberHeadImage;
	/**
	 * 会员电话 
	 */
	private String memberTelPhone;
	/**
	 * 会员名称
	 */
	private String memberName;
	/**
	 * 用餐人数
	 */
	private Integer num;
	/**
	 * 餐位
	 */
	
	private List<DishDetailsVO> seats;
	/**
	 * 定金（合计）
	 */
	private Double subscriptionMoney;//定金价
	/**
	 * 订单编号
	 */
	private String orderNo;//订单号 
	
	/**
	 * 订单状态
	 */
	private Integer status;//0未确认 1确认 2未评价 3 已评价 7是取消订单
	/**
	 * 商户名称
	 */
	private String merchantName;
	/**
	 * 商户电话
	 */
	private String merchantTelPhone;
	/**
	 * 点菜
	 */
	private List<DishDetailsVO> dlist;
	
	/**
	 * 商户图片路径
	 */
	private String merchantHeadImg;
	/**
	 * 预定时长
	 */
	private String time;
	/**
	 * 订单的状态
	 */
	private Integer onLineType;//0是未取消，1是取消订单

	public Integer getOnLineType() {
		return onLineType;
	}

	public void setOnLineType(Integer onLineType) {
		this.onLineType = onLineType;
	}

	private String predictServiceDate;//预计离店时间
	/**
	 * 预定时长
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHopeServiceDate() {
		return hopeServiceDate;
	}
	public void setHopeServiceDate(String hopeServiceDate) {
		this.hopeServiceDate = hopeServiceDate;
	}
	public String getMemberHeadImage() {
		return memberHeadImage;
	}
	public void setMemberHeadImage(String memberHeadImage) {
		this.memberHeadImage = memberHeadImage;
	}
	public String getMemberTelPhone() {
		return memberTelPhone;
	}
	public void setMemberTelPhone(String memberTelPhone) {
		this.memberTelPhone = memberTelPhone;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Double getSubscriptionMoney() {
		return subscriptionMoney;
	}

	public void setSubscriptionMoney(Double subscriptionMoney) {
		this.subscriptionMoney = subscriptionMoney;
	}

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
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMerchantTelPhone() {
		return merchantTelPhone;
	}
	public void setMerchantTelPhone(String merchantTelPhone) {
		this.merchantTelPhone = merchantTelPhone;
	}

	public String getMerchantHeadImg() {
		return merchantHeadImg;
	}
	public void setMerchantHeadImg(String merchantHeadImg) {
		this.merchantHeadImg = merchantHeadImg;
	}
	public String getPredictServiceDate() {
		return predictServiceDate;
	}
	public void setPredictServiceDate(String predictServiceDate) {
		this.predictServiceDate = predictServiceDate;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public List<DishDetailsVO> getSeats() {
		return seats;
	}

	public void setSeats(List<DishDetailsVO> seats) {
		this.seats = seats;
	}

	public List<DishDetailsVO> getDlist() {
		return dlist;
	}

	public void setDlist(List<DishDetailsVO> dlist) {
		this.dlist = dlist;
	}
}