package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;

/**
 * 
* @ClassName: QueryManagerAddressVo
* @Description: 代理商收货地址VO
 * @author 王强
* @date 2017年3月20日 下午2:27:03
 */
public class QueryManagerAddressVO implements Serializable {
	private static final long serialVersionUID = -2748695051628900527L;

	private String contact;//联系人
	
	private String telPhone;//电话
	
	private String adrressDetail;//详细地址
	
	private String phoneNo;//座机号

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getAdrressDetail() {
		return adrressDetail;
	}

	public void setAdrressDetail(String adrressDetail) {
		this.adrressDetail = adrressDetail;
	}
}
