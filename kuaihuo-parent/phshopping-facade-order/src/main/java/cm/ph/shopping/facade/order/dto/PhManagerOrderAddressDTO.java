package cm.ph.shopping.facade.order.dto;

import java.io.Serializable;
import java.util.Date;

public class PhManagerOrderAddressDTO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    /** 用户id(ph_permission_common_user表id) */
    private Long userId;

    /** 省id */
    private Long provinceId;

    /** 市id */
    private Long cityId;

    /** 区id */
    private Long areaId;

    /** 省 */
    private String provinceName;

    /** 市 */
    private String cityName;

    /** 区 */
    private String areaName;


    private  String townName;

    /** 详细地址 */
    private String address;

    /** 联系人 */
    private String contacts;

    /** 手机号码 */
    private String telPhone;

    /** 座机号 */
    private String phoneNo;

    /** 是否默认 */
    private Integer isDefault;

    private Date createTime;

    private Date updateTime;

    private Integer isable;

    private  Long  positionId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        this.address = address == null ? null : address.trim();
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone == null ? null : telPhone.trim();
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo == null ? null : phoneNo.trim();
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getIsable() {
        return isable;
    }

    public void setIsable(Integer isable) {
        this.isable = isable;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }


}
