package com.ph.shopping.facade.member.dto;

import com.ph.shopping.common.core.base.BaseValidate;

/**
 * 
 * @ClassName:  PromotionRecordDTO   
 * @Description:推广师相关信息查询DTO（推广企业用）   
 * @author: lijie
 * @date:   2017年5月2日 下午2:26:10     
 * @Copyright: 2017
 */
public class PromotionRecordDTO extends BaseValidate{

	/**   
	 * @Fields serialVersionUID :   
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 推广师账号
	 */
	private String account;
	/**
	 * 推广师名称
	 */
	private String memberName;
	/**
	 * 企业账号
	 */
	private String companyAccount;
	/**
	 * 企业名称
	 */
	private String companyName;
	/**
	 * 企业类型ID
	 */
	private Long companyTypeId;
	/**
	 * 会员ID
	 */
	private Long memberId;
	/**
     * 推广师证图片
     */	
    private String url;
	/**
	 * 当前页码
	 */
	private Integer pageNum;
	/**
	 * 每页条数
	 */
	private Integer pageSize;

	/**
	 * 创建人Id
	 */
	private Long createrId;

	/**
	 * 企业状态
	 */
	private Byte companyStatus;
	
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getCompanyAccount() {
		return companyAccount;
	}
	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Long getCompanyTypeId() {
		return companyTypeId;
	}
	public void setCompanyTypeId(Long companyTypeId) {
		this.companyTypeId = companyTypeId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Byte getCompanyStatus() {
		return companyStatus;
	}
	public void setCompanyStatus(Byte companyStatus) {
		this.companyStatus = companyStatus;
	}
	
}
