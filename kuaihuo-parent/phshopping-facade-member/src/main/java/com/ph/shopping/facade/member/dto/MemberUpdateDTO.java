package com.ph.shopping.facade.member.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.ph.shopping.common.core.base.BaseValidate;

/**
 * 
* @ClassName: MemberUpdateDTO
* @Description: 会员修改传输数据   
* @author liuy
* @date 2017年6月7日 下午6:04:11
 */
public class MemberUpdateDTO  extends BaseValidate{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = 7444501116180669284L;

	/**
	 * 会员ID
	 */
	@NotNull(message="[会员ID]不能为空")
	private Long id;
	/**
	 * 姓名
	 */
	private String memberName;
	/**
	 * 性别
	 */
	private Byte sex;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 会员等级
	 */
	private Byte level;
	
	/**
	 * 头像图片 url
	 */
	private String headImage;
	/**
	 * 修改人Id
	 */
	@NotNull(message="[修改人Id]不能为空")
	private Long updaterId;

	private Long probMoney;//剩余金额

	
	public Byte getLevel() {
		return level;
	}
	public void setLevel(Byte level) {
		this.level = level;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Byte getSex() {
		return sex;
	}
	public void setSex(Byte sex) {
		this.sex = sex;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public Long getUpdaterId() {
		return updaterId;
	}
	public void setUpdaterId(Long updaterId) {
		this.updaterId = updaterId;
	}

	public Long getProbMoney() {
		return probMoney;
	}

	public void setProbMoney(Long probMoney) {
		this.probMoney = probMoney;
	}
}
