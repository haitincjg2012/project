package cm.ph.shopping.facade.order.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @项目：phshopping-facade-order
 * @描述：线上发货DTO
 * @作者： 张霞
 * @创建时间： 12:22 2017/6/19
 * @Copyright @2017 by zhangxia
 */
public class SendOnlineOrderDTO implements Serializable {
    private static final long serialVersionUID = 6950132567015596083L;

    /**
     * 子订单id
     */
    @NotNull(message = "[订单号]不能为空")
    private Long orderId;
    /**
     * 发货地址id
     */
    @NotNull(message = "[发货地址id]不能为空")
    private Long sendAddressId;
    /**
     * 发货地址(即供应商提供的仓库地址)
     */
    @NotNull(message = "[发货地址]不能为空")
    private String sendAddressName;
    /**
     * 物流公司id
     */
    @NotNull(message = "[物流公司id]不能为空")
    private Long logisticsId;
    /**
     * 物流(物流公司的名称)
     */
    @NotNull(message = "[物流公司的名称]不能为空")
    private String logisticsName;
    /**
     * 物流编号
     */
    @NotNull(message = "[物流编号]不能为空")
    private String logisticsNo;

    public Long getSendAddressId() {
        return sendAddressId;
    }

    public void setSendAddressId(Long sendAddressId) {
        this.sendAddressId = sendAddressId;
    }

    public String getSendAddressName() {
        return sendAddressName;
    }

    public void setSendAddressName(String sendAddressName) {
        this.sendAddressName = sendAddressName;
    }

    public Long getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Long logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
