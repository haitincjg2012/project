package cm.ph.shopping.facade.order.entity;

import com.ph.shopping.common.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;
/**
 * @author 熊克文
 * @Description: 收货地址实体
 */
@Table(name = "ph_member_order_address")
public class PhOrderAddress extends BaseEntity {
	
	private static final long serialVersionUID = -8457371595984442106L;

    /** 会员id */
	@Column(name="memberId")
    private Long memberId;

    /** 省 */
    @Column(name = "provinceId")
    private Long provinceId;

    /** 市 */
    @Column(name = "cityId")
    private Long cityId;

    /** 区 */
    @Column(name = "areaId")
    private Long areaId;

    /**
     * 社区
     */
    @Column(name = "townId")
    private Long townId;

    /** 详细地址 */
    @Column(name = "address")
    private String address;

    /** 联系人 */
    @Column(name = "contacts")
    private String contacts;

    /** 联系电话 */
    @Column(name="telPhone")
    private String telPhone;

    /** 座机号码 */
    @Column(name="phoneNo")
    private String phoneNo;

    /** 是否默认 (1是，2否)*/
    @Column(name="isdefault")
    private Integer isDefault;
    
    @Column(name="positionId")
    private Long positionId;

    /**
     * 是否删除(1删除，2未删除）
     */
    @Column(name = "isDelete")
    private Byte isDelete;

    public Long getTownId() {
        return townId;
    }

    public void setTownId(Long townId) {
        this.townId = townId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
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

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }
}