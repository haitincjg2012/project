package cm.ph.shopping.facade.order.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @项目：phshopping-facade-order
 * @描述：会员线上子订单状态变更记录
 * @作者： 张霞
 * @创建时间： 8:48 2017/5/24
 * @Copyright @2017 by zhangxia
 */
@Table(name = "ph_member_sub_order_online_record")
public class PhMemberSubOrderOnlineRecord implements Serializable{
    private static final long serialVersionUID = -2282197982321131649L;

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
    /** 订单状态描述(状态变更前状态)
     *
     */
    @Column(name = "statusBefore")
    private String statusBefore;
    /** 处理状态描述(变更状态后状态)
     *
     */
    @Column(name = "statusAfter")
    private String statusAfter;
    /** 创建人(当由角色为定时器创建时，默认为0，其他填写对应人的id)
     *
     */
    @Column(name = "createrId")
    private Long createrId;
    /** 创建时间
     *
     */
    @Column(name = "createTime")
    private Date createTime;
    /** 创建人类型(角色：线上订单只有会员、平台(定时器)和供应商操作):0=会员；1=供应商；2=定时器。
     *
     */
    @Column(name = "createrRoleType")
    private Byte createrRoleType;

    /**
     * 变更内容:0=更新订单是否分润,1=更新订单是否结算,2=更新订单状态
     */
    @Column(name = "updateContent")
    private String updateContent;

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

    public String getStatusBefore() {
        return statusBefore;
    }

    public void setStatusBefore(String statusBefore) {
        this.statusBefore = statusBefore;
    }

    public String getStatusAfter() {
        return statusAfter;
    }

    public void setStatusAfter(String statusAfter) {
        this.statusAfter = statusAfter;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getCreaterRoleType() {
        return createrRoleType;
    }

    public void setCreaterRoleType(Byte createrRoleType) {
        this.createrRoleType = createrRoleType;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }
}
