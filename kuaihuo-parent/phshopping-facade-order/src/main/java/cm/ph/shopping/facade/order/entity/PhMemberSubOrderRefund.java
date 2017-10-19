package cm.ph.shopping.facade.order.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @项目：phshopping-facade-order
 * @描述：会员子订单退款申请(线上)
 * @作者： 张霞
 * @创建时间： 23:17 2017/5/23
 * @Copyright @2017 by zhangxia
 */
@Table(name = "ph_member_sub_order_refund")
public class PhMemberSubOrderRefund implements Serializable {
    private static final long serialVersionUID = -2282197982321131689L;

    /** 主键id
     *
     */
    @Id
    private Long id;
    /** 子订单id
     *
     */
    @Column(name = "subOrderId")
    private Long subOrderId;
    /** 申请人id(创建人id)
     *
     */
    @Column(name = "createrId")
    private Long createrId;
    /** 申请原因
     *
     */
    @Column(name = "appliReason")
    private String appliReason;
    /** 申请状态 0=退款审核中；1=拒绝退款；2=退款中；3=退款完成
     *
     */
    @Column(name = "appliStatus")
    private Byte appliStatus;
    /** 驳回人联系电话
     *
     */
    @Column(name = "telphone")
    private String telphone;
    /** 驳回原因
     *
     */
    @Column(name = "rejectedReason")
    private String rejectedReason;
    /** 修改人id
     *
     */
    @Column(name = "updaterId")
    private Long updaterId;
    /** 修改时间
     *
     */
    @Column(name = "updateTime")
    private Date updateTime;
    /** 创建时间
     *
     */
    @Column(name = "createTime")
    private  Date createTime;
    /**
     * 审核申请退款是否通过
     */
    @Column(name = "checkIsAccess")
    private Byte checkIsAccess;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(Long subOrderId) {
        this.subOrderId = subOrderId;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public String getAppliReason() {
        return appliReason;
    }

    public void setAppliReason(String appliReason) {
        this.appliReason = appliReason;
    }

    public Byte getAppliStatus() {
        return appliStatus;
    }

    public void setAppliStatus(Byte appliStatus) {
        this.appliStatus = appliStatus;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getCheckIsAccess() {
        return checkIsAccess;
    }

    public void setCheckIsAccess(Byte checkIsAccess) {
        this.checkIsAccess = checkIsAccess;
    }
}
