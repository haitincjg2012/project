package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 
* @ClassName: QueryOrderAddressVo
* @Description: 会员收货地址VO
* @author 王强
* @date 2017年4月25日 下午5:17:22
 */
public class QueryOrderAddressVO implements Serializable {
	private static final long serialVersionUID = 1028479222957528765L;

	private long id;//主键id
	private String contact;//联系人
	@JsonIgnore
	private String province;//省id
	@JsonIgnore
	private String city;//市id
	@JsonIgnore
	private String area;//区id
	@JsonIgnore
	private String address;//详细地址
	private String telPhone;//联系电话
	@JsonIgnore
	private String phoneNo;//座机号
	private String addressDetail;//详细地址
	//默认地址 1 默认 2 非默认
	private Integer isDefault;

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
}
