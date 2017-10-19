package com.ph.shopping.facade.member.dto;

import javax.validation.constraints.NotNull;

import com.ph.shopping.common.core.base.BaseValidate;

/**
 * 
* @ClassName: CheckMemberPromotionDTO
* @Description: 审核推广师dto
* @author liuy
* @date 2017年6月1日 下午5:09:30
 */
public class CheckMemberPromotionDTO extends BaseValidate {

    private static final long serialVersionUID = 1273228380097925190L;

    /**
     * 会员id
     */
	@NotNull(message="[会员id]不能为空")
    private Long memberId;

    /**
     * 推广师新审核状态 1：待审核 2：审核通过 3：审核未通过
     */
	@NotNull(message="[推广师审核状态]不能为空")
    private Byte status;

    /**
     * 推广师原审核状态 1：待审核 2：审核通过 3：审核未通过
     */
	@NotNull(message="[推广师原审核状态]不能为空")
    private Byte originalStatus;
	
    /**
     * 修改人
     */
	@NotNull(message="[修改人]不能为空")
	private Long updaterId;

    /**
     * 创建人Ip
     */
	private String createIp;
	
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getOriginalStatus() {
		return originalStatus;
	}

	public void setOriginalStatus(Byte originalStatus) {
		this.originalStatus = originalStatus;
	}

	public Long getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(Long updaterId) {
		this.updaterId = updaterId;
	}

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}
    
}
