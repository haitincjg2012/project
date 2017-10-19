package cm.ph.shopping.facade.order.request;

import java.io.Serializable;
/**
 * 
* @ClassName: WebOnLineDTO
* @Description: 线上订单请求DTO
* @author 王强
* @date 2017年4月25日 下午5:57:48
 */
public class WebOnLineDTO implements Serializable {
	private static final long serialVersionUID = -3201095590737696772L;
	private int deliveryType;//配送方式
	private long orderId;//订单id
	public int getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(int deliveryType) {
		this.deliveryType = deliveryType;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
}
