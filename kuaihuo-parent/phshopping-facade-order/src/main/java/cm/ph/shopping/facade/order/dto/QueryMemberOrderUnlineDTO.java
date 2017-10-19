package cm.ph.shopping.facade.order.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @项目：phshopping-facade-order
 * @描述：线下订单查询条件dto
 * @作者： 张霞
 * @创建时间： 10:57 2017/6/12
 * @Copyright @2017 by zhangxia
 */
public class QueryMemberOrderUnlineDTO implements Serializable {
    private static final long serialVersionUID = 6950132567015596083L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 下单时间--开始时间
     */
    private Date startTime;
    /**
     * 下单时间--结束时间
     */
    private Date endTime;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 会员账号
     */
    private String memberPhone;
    /**
     * 订单状态
     */
    private Byte status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
