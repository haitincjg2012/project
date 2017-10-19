
package cm.ph.shopping.facade.order.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ph.shopping.common.core.base.BaseEntityForToken;

/**
 * Date:     2017年2月27日 下午6:52:00 <br/>
 * @author   chen
 * @version  购物车
 * @since    JDK 1.8
 * @see 	 
 */
@Table(name = "ph_shop_cart")
public class ShopCart extends BaseEntityForToken implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1321597620742554113L;
	@Id
	private Long id;
	@Column(name = "dishId")
	private Long dishId;//商品
	@Column(name = "memberId")
	private Long memberId;//会员
	@Column(name = "buyNum")
	private Long buyNum;//购买数量
	@Column(name = "merchantId")
	private Long merchantId;//商户
	@Column(name = "type")
	private Long type;//类型  0、菜品  1、包间
	@Column(name = "created_time")
	private Date createdTime;//
	@Column(name = "update_time")
	private Date updateTime;//
	@Column(name = "hopeTime")
	private Date hopeTime;//
	@Column(name = "orderId")
	private Long orderId;//订单id

	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDishId() {
		return dishId;
	}
	public void setDishId(Long dishId) {
		this.dishId = dishId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(Long buyNum) {
		this.buyNum = buyNum;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
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
	public Date getHopeTime() {
		return hopeTime;
	}
	public void setHopeTime(Date hopeTime) {
		this.hopeTime = hopeTime;
	}
	
}

