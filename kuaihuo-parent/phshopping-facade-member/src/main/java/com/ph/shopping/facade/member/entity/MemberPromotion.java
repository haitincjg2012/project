package com.ph.shopping.facade.member.entity;

import com.ph.shopping.common.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @项目：phshopping-facade-member
 * @描述：推广师申请实体
 * @作者： Mr.zheng
 * @创建时间：2017-03-17
 * @Copyright @2017 by Mr.zheng
 */
@Table(name = "ph_member_auth")
public class MemberPromotion extends BaseEntity {
    private static final long serialVersionUID = 2042468827605796028L;

    /**
     * 账号
     */
    @Column(name = "account")
    private String account;
    /**
     * 账号类型 1：新业态推广师 2 ：本地电商推广师
     */
    @Column(name = "accountType")
    private Byte accountType;
    /**
     * 推广师证图片
     */
    @Column(name = "url")
    private String url;
    /**
     * 会员id
     */
    @Column(name = "memberId")
    private Long memberId;
    /**
     * 1：待审核 2：审核通过 3：审核未通过
     */
    @Column(name = "status")
    private Byte status;
    /**
     * 审核通过时间
     */
    @Column(name = "checkTime")
    private Date checkTime;

    /**
     * 是否删除 1 删除 2 未删除
     */
    @Column(name = "isDelete")
    private Byte isDelete;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Byte getAccountType() {
        return accountType;
    }

    public void setAccountType(Byte accountType) {
        this.accountType = accountType;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
    
}
