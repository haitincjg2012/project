package cm.ph.shopping.facade.order.request;
/**
 * 
* @ClassName: OnLineOrderSendRequest
* @Description: 线上订单发货请求DTO
* @author 王强
* @date 2017年4月25日 下午5:43:14
 */
public class OnLineOrderSendDTO {
	private int status;//订单状态
	private String logisticName;//物流名称
	private String logisticNo;//物流编号
	private long orderId;//订单ID
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getLogisticName() {
		return logisticName;
	}
	public void setLogisticName(String logisticName) {
		this.logisticName = logisticName;
	}
	public String getLogisticNo() {
		return logisticNo;
	}
	public void setLogisticNo(String logisticNo) {
		this.logisticNo = logisticNo;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
}
