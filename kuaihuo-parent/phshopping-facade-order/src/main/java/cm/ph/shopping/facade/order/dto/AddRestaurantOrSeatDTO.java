package cm.ph.shopping.facade.order.dto;

import java.io.Serializable;
import java.util.Date;

import com.ph.shopping.common.core.base.BaseValidate;

/**
 * 
 * <pre>项目名称：phshopping-facade-order    
 * 类名称：AddRestaurantOrSeatDTO    
 * 类描述：新增餐位DTO    
 * 创建人：赵俊彪    
 * 创建时间：2017年8月22日 下午3:21:16    
 * 修改人：赵俊彪     
 * 修改时间：2017年8月22日 下午3:21:16    
 * 修改备注：       
 * @version </pre>
 */
public class AddRestaurantOrSeatDTO extends BaseValidate implements Serializable{

	private static final long serialVersionUID = 488872064558420538L;
	
	private Long id;
	
	private String dishName; //餐位名称
	
	private Long saleNum;//人数

	private Long subscriptionMoney;//最低消费

	private Long  merchantId;//商户Id

	private Integer type;	//餐位
	
	private String description;//简介

	private String typeName;//分类名称
	
	private Date created_time;
	
	private Date update_time;
	
	private Long hopeTime;//进餐时间
	
	private Long dishId;//商品 餐位id

	private String address;//图片地址

	private Date hopeServiceDate;//到店时间

	private Date predictServiceDate;//预计离店时间

	private String orderNo;//订单号

	private Integer status;//订单状态
	
	private Long imgId;//大图Id

	//private List<MerchantRestaurantImageDTO> imgList;  //餐位 商品  图片表
	private Long isDelete;//0 上架  1下架
	private Long dishTypeId;
	private Long count;//包间人数
	private Long money;//单价
	private String token;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public Long getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Long saleNum) {
		this.saleNum = saleNum;
	}

	public Long getSubscriptionMoney() {
		return subscriptionMoney;
	}

	public void setSubscriptionMoney(Long subscriptionMoney) {
		this.subscriptionMoney = subscriptionMoney;
	}
	
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Date getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public Long getHopeTime() {
		return hopeTime;
	}

	public void setHopeTime(Long hopeTime) {
		this.hopeTime = hopeTime;
	}

	public Long getDishId() {
		return dishId;
	}

	public void setDishId(Long dishId) {
		this.dishId = dishId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Long getImgId() {
		return imgId;
	}

	public void setImgId(Long imgId) {
		this.imgId = imgId;
	}

	public Long getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Long isDelete) {
		this.isDelete = isDelete;
	}

	public Long getDishTypeId() {
		return dishTypeId;
	}

	public void setDishTypeId(Long dishTypeId) {
		this.dishTypeId = dishTypeId;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	



	
	
	
	 
}
