package cm.ph.shopping.facade.order.dto;

import com.ph.shopping.common.core.base.BaseValidate;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @项目：phshopping-facade-order
 * @描述：创建线上订单DTO
 * @作者： 张霞
 * @创建时间： 14:17 2017/5/27
 * @Copyright @2017 by zhangxia
 */
public class AddMemberOrderOnlineDTO extends BaseValidate implements Serializable {
    private static final long serialVersionUID = 4217176085679759216L;

    /**
     * 子订单信息
     */
    @NotNull(message = "订单参数不能为空")
    List<MemberOrderOnlineDTO> memberOrderOnlineDTOS = new ArrayList<>();

    public List<MemberOrderOnlineDTO> getMemberOrderOnlineDTOS() {
        return memberOrderOnlineDTOS;
    }

    public void setMemberOrderOnlineDTOS(List<MemberOrderOnlineDTO> memberOrderOnlineDTOS) {
        this.memberOrderOnlineDTOS = memberOrderOnlineDTOS;
    }
}
