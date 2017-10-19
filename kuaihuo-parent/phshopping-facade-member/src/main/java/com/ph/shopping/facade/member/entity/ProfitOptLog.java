package com.ph.shopping.facade.member.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * phshopping-facade-member
 *
 * @description：影响分润操作流水表 会员模块
 *
 * @author：liuy
 *
 * @createTime：2017年5月25日
 *
 * @Copyright @2017 by liuy
 */
@Table(name="ph_member_profit_opt_log")
public class ProfitOptLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8368845859167337452L;

	/**
	 * 主键
	 */
    @Id
    private Long id;
    
	/**
	 * 用户id（解冻或者冻结对象）
	 */
	@Column(name = "userId")
	private Long userId;

	/**
	 * 操作类型 0=解冻，1=冻结，2=审核不通过(推广师通过审核后再次审核为不通过，不分润状态)，3=审核通过
	 */
	@Column(name = "useType")
	private Byte useType;
	
	/**
	 * 是否分润 0=不分润，1=可分润
	 */
	@Column(name = "isProfit")
	private Byte isProfit;
	
	/**
	 * 创建ip
	 */
	@Column(name = "createIp")
	private String createIp;

	/**
	 * 备注(记录内容包含：会员是否是推广师，会员状态，推广师审核状态)
	 */
	@Column(name = "remark")
	private String remark;
	
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "createTime")
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name = "createrId")
    private Long createrId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Byte getUseType() {
		return useType;
	}

	public void setUseType(Byte useType) {
		this.useType = useType;
	}

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	public Byte getIsProfit() {
		return isProfit;
	}

	public void setIsProfit(Byte isProfit) {
		this.isProfit = isProfit;
	}
    
}
