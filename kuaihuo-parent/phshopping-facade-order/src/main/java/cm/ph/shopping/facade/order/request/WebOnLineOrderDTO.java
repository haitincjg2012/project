package cm.ph.shopping.facade.order.request;

import com.ph.shopping.common.util.page.PageBean;
/**
 * 
* @ClassName: WebOnLineOrderDTO
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 王强
* @date 2017年4月25日 下午5:54:39
 */
public class WebOnLineOrderDTO extends PageBean {

	private static final long serialVersionUID = -8562018163667488171L;
	
	private long memberId;//会员id
	
	private int status;//0查全部，1待付款，3待收货，4已完成，7已退货

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
}
