package cm.ph.shopping.facade.order.request;

/**
 * 
* @ClassName: OrderAddressDefDTO
* @Description: 收货地址请求DTO
* @author 王强
* @date 2017年4月25日 下午5:45:22
 */
public class OrderAddressDefDTO {
	private long memberId;//会员id
	
	private long addrId;//收货地址id

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public long getAddrId() {
		return addrId;
	}

	public void setAddrId(long addrId) {
		this.addrId = addrId;
	}
}
