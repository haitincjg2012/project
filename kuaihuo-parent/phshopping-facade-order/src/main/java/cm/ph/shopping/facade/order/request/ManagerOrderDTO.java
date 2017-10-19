package cm.ph.shopping.facade.order.request;

import com.ph.shopping.common.util.page.PageBean;
/**
 * 
* @ClassName: ManagerOrderRequest
* @Description: 用户请求DTO
* @author 王强
* @date 2017年4月25日 下午5:41:32
 */
public class ManagerOrderDTO extends PageBean {

	private static final long serialVersionUID = 1030475932685176452L;
	private long userId;//用户id

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}
