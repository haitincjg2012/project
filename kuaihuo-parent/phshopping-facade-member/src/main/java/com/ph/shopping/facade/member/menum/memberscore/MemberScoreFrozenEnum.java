package com.ph.shopping.facade.member.menum.memberscore;

/**
 * 
 * phshopping-facade-member
 *
 * @description：会员是否冻结枚举
 *
 * @author：liuy
 *
 * @createTime：2017年5月24日
 *
 * @Copyright @2017 by liuy
 */
public enum MemberScoreFrozenEnum {

	MEMBER_SCORE_FROZEN((byte)1,"会员积分冻结"),
	MEMBER_SCORE_NORMAL((byte)0,"会员积分非冻结");
	
    /**
	 * 编码
	 */
	private Byte code;
	/**
	 * 描述
	 */
	private String remark;
	
	private MemberScoreFrozenEnum(Byte code,String remark){
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
