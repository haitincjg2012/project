package cm.ph.shopping.facade.order.dto;

import com.ph.shopping.common.core.base.BaseValidate;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author 熊克文
 * @Description: 收货地址DTO
 */
public class PhOrderAddressDTO extends BaseValidate {

    private static final long serialVersionUID = -8457371595984442106L;

    /**
     * id
     */
    private Long id;

    /**
     * 会员id
     */
    @NotNull(message = "会员id不能为空")
    private Long memberId;

    /**
     * 省
     */
    @NotNull(message = "省不能为空")
    private Long provinceId;

    /**
     * 市
     */
    @NotNull(message = "市不能为空")
    private Long cityId;

    /**
     * 区
     */
    @NotNull(message = "区不能为空")
    private Long areaId;

    /**
     * 社区
     */
    @NotNull(message = "社区不能为空")
    private Long townId;

    /**
     * 详细地址
     */
    @NotBlank(message = "详细地址不能为空")
    private String address;

    /**
     * 联系人
     */
    @NotBlank(message = "联系人不能为空")
    private String contacts;

    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空")
    private String telPhone;

    /**
     * 座机号码
     */
    private String phoneNo;

    /**
     * 是否默认 (1是，2否)
     */
    private Integer isDefault;

    @NotNull(message = "地址不能为空")
    private Long positionId;

    /**
     * 是否删除(1删除，2未删除）
     */
    private Byte isDelete;

    private Long createrId;

    private Long updaterId;

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }

    public Long getTownId() {
        return townId;
    }

    public void setTownId(Long townId) {
        this.townId = townId;
    }
}