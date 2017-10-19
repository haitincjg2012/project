package cm.ph.shopping.facade.order.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 发货DTO
 *
 * @author 郑朋
 * @create 2017/5/18
 **/
public class SendProductDTO extends BaseValidate implements Serializable {

    private static final long serialVersionUID = -3827994980932478007L;

    @NotNull(message = "[订单id]不可为空")
    private Long id;

    /** 发货人id */
    @NotNull(message = "[发货人id]不可为空")
    private Long senderId;

    /** 物流公司 */
    @NotNull(message = "[物流公司]不可为空")
    private String logisticsCompany;

    /** 物流编号 */
    @NotNull(message = "[物流编号]不可为空")
    private String logisticsNo;

    /** 发货地址(ph_manager_order_address表id) */
    @NotNull(message = "[发货地址id]不可为空")
    private Long sendAddressId;

    /** 物流公司id */
    @NotNull(message = "[物流公司id]不可为空")
    private Long logisticsId;


    /** 发货详细地址 */
    private String sendAddress;

    /** 发货联系人 */
    private String sendContacts;

    /** 发货人联系电话 */
    private String sendTelPhone;

    /**
     * 当前订单的状态
     */
    @NotNull(message = "[当前订单的状态]不可为空")
    private Byte currentOrderStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public Long getSendAddressId() {
        return sendAddressId;
    }

    public void setSendAddressId(Long sendAddressId) {
        this.sendAddressId = sendAddressId;
    }

    public Long getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Long logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getSendContacts() {
        return sendContacts;
    }

    public void setSendContacts(String sendContacts) {
        this.sendContacts = sendContacts;
    }

    public String getSendTelPhone() {
        return sendTelPhone;
    }

    public void setSendTelPhone(String sendTelPhone) {
        this.sendTelPhone = sendTelPhone;
    }

    public Byte getCurrentOrderStatus() {
        return currentOrderStatus;
    }

    public void setCurrentOrderStatus(Byte currentOrderStatus) {
        this.currentOrderStatus = currentOrderStatus;
    }
}
