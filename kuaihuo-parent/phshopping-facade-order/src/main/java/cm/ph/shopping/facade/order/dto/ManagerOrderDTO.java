package cm.ph.shopping.facade.order.dto;

public class ManagerOrderDTO {
	private int type;//1代理商,2供应商
	private long userId;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
}
