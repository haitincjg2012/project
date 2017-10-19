package com.ph.shopping.facade.member.dto;

import com.ph.shopping.common.core.base.BaseValidate;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @项目：phshopping-facade-member
 * @描述：推广师dto（推广师认证用）
 * @作者： Mr.Shu
 * @创建时间：2017-05-19
 * @Copyright @2017 by Mr.Shu
 */
public class MemberPromotionDTO extends BaseValidate {

    private static final long serialVersionUID = 3928320486222962171L;

	private Long id;

	/**
	 * 会员ID
	 */
	private Long memberId;

	/**
     * 账号
     */
    private String account;
	/**
	 * 会员电话
	 */
	private String telPhone;
	/**
	 * 姓名
	 */
	private String memberName;
    /**
     * 密码
     */
    private String password;
    /**
     * 账号类型 1：新业态推广师 2 ：本地电商推广师
     */
    private Byte accountType;
    /**
     * 推广师证照片
	 */
	@NotBlank(message="[推广师证图片]不能为空")
	private String url;
	/**
	 * 状态 1：待审核 2：审核通过 3：审核未通过
     */
    private Byte status;

    /**
	 * 申请开始时间
	 */
	private String createTimeStr;
	
	/**
	 * 申请结束时间
	 */
	private String createTimeEnd;
	
	/**
	 * 身份证号
	 */
	private String idCardNo;

	/**
	 * 创建人Id
	 */
	@NotNull(message="[创建人]不能为空")
	private Long createrId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Byte getAccountType() {
		return accountType;
	}

	public void setAccountType(Byte accountType) {
		this.accountType = accountType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

    public Byte getStatus() {
        return status;
	}

    public void setStatus(Byte status) {
        this.status = status;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}
	
}
