package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;

/**
 * @项目：phshopping-facade-order
 * @描述：线下订单取消详情
 * @作者： 张霞
 * @创建时间： 15:58 2017/6/9
 * @Copyright @2017 by zhangxia
 */
public class PhMemberOrderUnlineCancelDetailVO implements Serializable {
    private static final long serialVersionUID = -1201649270604922038L;
    /**
     * 取消线下订单原因说明
     */
    private String cancelReason;

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}
