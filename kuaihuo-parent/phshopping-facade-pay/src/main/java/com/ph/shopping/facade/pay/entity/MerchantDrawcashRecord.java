package com.ph.shopping.facade.pay.entity;

import com.ph.shopping.common.core.base.BaseEntity;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @项目：phshopping-facade-pay
 * @描述：商户提现记录 实体
 * @作者： 张霞
 * @创建时间： 8:56 2017/3/23
 * @Copyright @2017 by zhangxia
 */
@Table(name = "ph_merchant_drawcash_record")
public class MerchantDrawcashRecord extends BaseEntity{
    private static final long serialVersionUID = 273433423349452846L;
    /**
     * 商户id
     */
    @NotBlank(message="[商户id]不可为空")
    @Column(name = "merchantId")
    private Long merchantId;
    /**
     * 提现金额
     */
    @Column(name = "score")
    private Long score;
    /**
     * 提现手续费
     */
    @Column(name = "handingCharge")
    private Long handingCharge;
    /**
     * 提现状态:-1提现失败，0待审核，1，提现中，2提现成功
     */
    @Column(name = "status")
    private byte status;
    /**
     * 预计到账日期
     */
    @Column(name = "expectedDate")
    private Date expectedDate;
    /**
     * 申请提现日期
     */
    @Column(name = "createDate")
    private Date createDate;
    /**
     * 提现编号
     */
    @Column(name = "drawcashNo")
    private String drawcashNo;
    /**
     * 银行名称
     */
    @Column(name = "bankName")
    private String bankName;
    /**
     * 审核状态:-1提现失败，0待审核，1，提现中，2提现成功
     */
    @Column(name = "auditState")
    private byte auditState;
    /**
     * 提现ip地址
     */
    @Column(name = "drawcashIp")
    private String drawcashIp;
    /**
     * 收款人姓名
     */
    @Column(name = "receiver")
    private String receiver;
    /**
     * 银行卡号
     */
    @Column(name = "bankNo")
    private String bankNo;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getHandingCharge() {
        return handingCharge;
    }

    public void setHandingCharge(Long handingCharge) {
        this.handingCharge = handingCharge;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Date getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Date expectedDate) {
        this.expectedDate = expectedDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDrawcashNo() {
        return drawcashNo;
    }

    public void setDrawcashNo(String drawcashNo) {
        this.drawcashNo = drawcashNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public byte getAuditState() {
        return auditState;
    }

    public void setAuditState(byte auditState) {
        this.auditState = auditState;
    }

    public String getDrawcashIp() {
        return drawcashIp;
    }

    public void setDrawcashIp(String drawcashIp) {
        this.drawcashIp = drawcashIp;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }
}
