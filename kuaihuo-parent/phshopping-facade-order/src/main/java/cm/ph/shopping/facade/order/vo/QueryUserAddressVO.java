package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;
/**
 * 
* @ClassName: QueryUserAddressVo
* @Description: 查询用户地址VO
* @author 王强
* @date 2017年4月25日 下午5:18:50
 */
public class QueryUserAddressVO implements Serializable {
	private static final long serialVersionUID = 5089011626775103504L;
	private String address;//详细地址
	private long sendAddressId;//地址id
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public long getSendAddressId() {
		return sendAddressId;
	}
	public void setSendAddressId(long sendAddressId) {
		this.sendAddressId = sendAddressId;
	}
}
