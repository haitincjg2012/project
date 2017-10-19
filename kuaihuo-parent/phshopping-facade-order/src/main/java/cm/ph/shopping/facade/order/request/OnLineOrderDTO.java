package cm.ph.shopping.facade.order.request;

import java.io.Serializable;

/**
 * 
* @ClassName: OnLineOrderRequest
* @Description: 线上订单请求DTO
* @author 王强
* @date 2017年4月25日 下午5:42:37
 */
public class OnLineOrderDTO implements Serializable {
	
	private static final long serialVersionUID = -6007234823555835924L;
	
	private long id;//订单id

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
