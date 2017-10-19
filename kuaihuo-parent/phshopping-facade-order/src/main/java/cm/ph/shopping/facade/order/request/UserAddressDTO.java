package cm.ph.shopping.facade.order.request;

import java.io.Serializable;
/**
 * 
* @ClassName: UserAddressRequest
* @Description: 用户请求DTO
* @author 王强
* @date 2017年4月25日 下午5:51:32
 */
public class UserAddressDTO implements Serializable {
	private static final long serialVersionUID = -3947742440464569742L;
	private long userId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}
