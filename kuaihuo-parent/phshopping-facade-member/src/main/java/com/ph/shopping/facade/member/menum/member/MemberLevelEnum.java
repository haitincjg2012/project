package com.ph.shopping.facade.member.menum.member;

/**
 * 
 * phshopping-facade-member
 *
 * @description：会员等级枚举
 *
 * @author：liuy
 *
 * @createTime：2017年5月24日
 *
 * @Copyright @2017 by liuy
 */
public enum MemberLevelEnum {

	MEMBER_GENERAL((byte)1,"普通会员"),
	MEMBER_VIP((byte)2,"VIP");
	
    /**
	 * 编码
	 */
	private Byte code;
	/**
	 * 描述
	 */
	private String remark;
	
	private MemberLevelEnum(Byte code,String remark){
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
