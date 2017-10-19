package cm.ph.shopping.facade.order.dto;

import cm.ph.shopping.facade.order.entity.Dish;

public class DishOrder extends Dish{

	/**
	 * 获取订单id菜品数量实体类
	 * @author lzh
	 */
	private static final long serialVersionUID = 1L;
	private Long orderOnlineId;
	
	private Long dCount1;
	

	public Long getdCount1() {
		return dCount1;
	}

	public void setdCount1(Long dCount1) {
		this.dCount1 = dCount1;
	}

	public Long getOrderOnlineId() {
		return orderOnlineId;
	}

	public void setOrderOnlineId(Long orderOnlineId) {
		this.orderOnlineId = orderOnlineId;
	}
	
	
}
