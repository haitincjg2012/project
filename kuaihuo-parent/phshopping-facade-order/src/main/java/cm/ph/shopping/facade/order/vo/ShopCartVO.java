package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * 购物车VO
 * 
 * @author gaoge
 *
 */
public class ShopCartVO implements Serializable {

	private static final long serialVersionUID = -1321597620742554113L;

	protected Long id;
	private Long buyNum;// 购买数量
	private Long type;// 类型 0、菜品 1、包间
	private Long memberId;// 会员id
	private String merchantName;
	private Long merchantId;// 商户id
	private Long dishId;// 商品id
	private Long price;// 价格
	private Date hopetime;// 期望时间
	private Long count;// 库存
	private Long isDelete;// 商品的状态 0 上架 1 下架

	public Long getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Long isDelete) {
		this.isDelete = isDelete;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Date getHopetime() {
		return hopetime;
	}

	public void setHopetime(Date hopetime) {
		this.hopetime = hopetime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Long buyNum) {
		this.buyNum = buyNum;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getDishId() {
		return dishId;
	}

	public void setDishId(Long dishId) {
		this.dishId = dishId;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

}
