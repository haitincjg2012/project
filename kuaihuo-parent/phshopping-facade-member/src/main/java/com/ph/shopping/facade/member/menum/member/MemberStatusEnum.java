package com.ph.shopping.facade.member.menum.member;

/**
 * 
 * phshopping-facade-member
 *
 * @description：会员状态枚举
 *
 * @author：liuy
 *
 * @createTime：2017年5月24日
 *
 * @Copyright @2017 by liuy
 */
public enum MemberStatusEnum {

	MEMBER_DELETE((byte)1,"会员删除"),
	MEMBER_NORMAL((byte)2,"会员正常"),
	MEMBER_FROZEN((byte)3,"会员冻结");
	
    /**
	 * 编码
	 */
	private Byte code;
	/**
	 * 描述
	 */
	private String remark;
	
	private MemberStatusEnum(Byte code,String remark){
		this.code = code;
		this.remark = remark;
	}

	public Byte getCode() {
		return code;
	}

	public void setCode(Byte code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
