package com.ph.shopping.facade.member.menum.membercard;

/**
 * 
 * phshopping-facade-member
 *
 * @description：会员卡状态枚举
 *
 * @author：liuy
 *
 * @createTime：2017年5月24日
 *
 * @Copyright @2017 by liuy
 */
public enum MemberCardStatusEnum {

	CARD_DELETE((byte)0,"删除"),
	CARD_NORMAL((byte)1,"正常"),
	CARD_FROZEN((byte)2,"冻结");
	
    /**
	 * 编码
	 */
	private Byte code;
	/**
	 * 描述
	 */
	private String remark;
	
	private MemberCardStatusEnum(Byte code,String remark){
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
