package com.ph.shopping.facade.pay.entity;

import com.ph.shopping.common.core.base.BaseEntity;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @项目：phshopping-facade-pay
 * @描述：会员积分流水记录 实体
 * @作者： 张霞
 * @创建时间： 16:10 2017/4/11
 * @Copyright @2017 by zhangxia
 */
@Table(name = "ph_member_score")
public class MemberScore extends BaseEntity{
    private static final long serialVersionUID = 273433423349452846L;
    /**
     * 会员id
     */
    @Column(name = "memberId")
    @NotBlank(message="[会员id]不可为空")
    private Long memberId;
    /**
     * 可用积分
     */
    @Column(name = "enableScore")
    private Long enableScore;
    /**
     * 待用积分
     */
    @Column(name = "standbyScore")
    private Long standbyScore;
    /**
     * 已提现积分
     */
    @Column(name = "drawcashScore")
    private Long drawcashScore;

    /**
     * 分享会员积分
     */
    @Column(name = "shareMemberScore")
    private Long shareMemberScore;

    /**
     * 分享商户积分
     */
    @Column(name = "shareMerchantScore")
    private Long shareMerchantScore;

    /**
     * 分享会员总积分
     */
    @Column(name = "totleShareMerchantScore")
    private Long totleShareMerchantScore;

    /**
     * 分享商户总积分
     */
    @Column(name = "totleShareMemberScore")
    private Long totleShareMemberScore;

    /**
     * 店面经理积分
     */
    @Column(name = "storeManagerScore")
    private Long storeManagerScore;

    /**
     * 店面经理总积分
     */
    @Column(name = "totalStoreManagerScore")
    private Long totalStoreManagerScore;

    public Long getShareMemberScore() {
        return shareMemberScore;
    }

    public void setShareMemberScore(Long shareMemberScore) {
        this.shareMemberScore = shareMemberScore;
    }

    public Long getShareMerchantScore() {
        return shareMerchantScore;
    }

    public void setShareMerchantScore(Long shareMerchantScore) {
        this.shareMerchantScore = shareMerchantScore;
    }

    public Long getTotleShareMerchantScore() {
        return totleShareMerchantScore;
    }

    public void setTotleShareMerchantScore(Long totleShareMerchantScore) {
        this.totleShareMerchantScore = totleShareMerchantScore;
    }

    public Long getTotleShareMemberScore() {
        return totleShareMemberScore;
    }

    public void setTotleShareMemberScore(Long totleShareMemberScore) {
        this.totleShareMemberScore = totleShareMemberScore;
    }

    public Long getStoreManagerScore() {
        return storeManagerScore;
    }

    public void setStoreManagerScore(Long storeManagerScore) {
        this.storeManagerScore = storeManagerScore;
    }

    public Long getTotalStoreManagerScore() {
        return totalStoreManagerScore;
    }

    public void setTotalStoreManagerScore(Long totalStoreManagerScore) {
        this.totalStoreManagerScore = totalStoreManagerScore;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getEnableScore() {
        return enableScore;
    }

    public void setEnableScore(Long enableScore) {
        this.enableScore = enableScore;
    }

    public Long getStandbyScore() {
        return standbyScore;
    }

    public void setStandbyScore(Long standbyScore) {
        this.standbyScore = standbyScore;
    }

    public Long getDrawcashScore() {
        return drawcashScore;
    }

    public void setDrawcashScore(Long drawcashScore) {
        this.drawcashScore = drawcashScore;
    }
}
