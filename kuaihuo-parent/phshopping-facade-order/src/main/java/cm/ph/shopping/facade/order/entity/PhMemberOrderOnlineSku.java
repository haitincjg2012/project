package cm.ph.shopping.facade.order.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "ph_member_order_online_sku")
public class PhMemberOrderOnlineSku  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4159749956384436455L;
	@Id
	private Long id;
	@Column(name = "created_time")
	private Date createdTime;//
	@Column(name = "update_time")
	private Date updateTime;//
	@Column(name = "dishId")
	private Long dishId;//
	@Column(name = "dishName")
	private String dishName;//菜品名称
	@Column(name = "dishSpecification")
	private String dishSpecification;//菜品/包间规格
	@Column(name = "money")
	private Long money;//单价
	@Column(name = "type")
	private int type;//类型
	@Column(name = "dishType")
	private String dishType;//包间名称 
	@Column(name = "orderOnlineId")
	private String orderOnlineId;//订单id
	@Column(name = "dCount")
	private String dCount;//订单id
	public String getdCount() {
		return dCount;
	}
	public void setdCount(String dCount) {
		this.dCount = dCount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getDishId() {
		return dishId;
	}
	public void setDishId(Long dishId) {
		this.dishId = dishId;
	}
	public String getDishName() {
		return dishName;
	}
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
	public String getDishSpecification() {
		return dishSpecification;
	}
	public void setDishSpecification(String dishSpecification) {
		this.dishSpecification = dishSpecification;
	}
	public Long getMoney() {
		return money;
	}
	public void setMoney(Long money) {
		this.money = money;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDishType() {
		return dishType;
	}
	public void setDishType(String dishType) {
		this.dishType = dishType;
	}
	public String getOrderOnlineId() {
		return orderOnlineId;
	}
	public void setOrderOnlineId(String orderOnlineId) {
		this.orderOnlineId = orderOnlineId;
	}
	
}
