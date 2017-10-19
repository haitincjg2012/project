package com.ph.shopping.facade.profit.dto;

import com.ph.shopping.common.util.page.PageBean;

/**
 * 会员余额DTO
* @ClassName: MemberScoreDetailedDTO
* @Description: TODO(账号结算下会员余额DTO)
* @author Mr.Dong
* @date 2017年6月12日 下午6:07:33
 */
public class MemberScoreDetailedDTO extends PageBean {

	private static final long serialVersionUID = -203317114226559430L;
	
	private String telPhone;//会员账号
	
	private Byte isMarketing;//会员类型     是否是推广师;1=是；2=否
	
	private Byte status;//状态   未冻结 0 已冻结 1  积分冻结状态
	
	private String date;//查询时间

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public Byte getIsMarketing() {
		return isMarketing;
	}

	public void setIsMarketing(Byte isMarketing) {
		this.isMarketing = isMarketing;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
}
