package cm.ph.shopping.facade.order.request;
/**
 * 
* @ClassName: UpdateOrderDTO
* @Description: 更新订单状态请求DTO
* @author 王强
* @date 2017年4月25日 下午5:50:56
 */
public class UpdateOrderDTO {
	private String orderNo;//订单编号
	private int status;//订单状态

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
