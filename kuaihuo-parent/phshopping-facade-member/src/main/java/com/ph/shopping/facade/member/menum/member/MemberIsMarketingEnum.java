package com.ph.shopping.facade.member.menum.member;

/**
 * 
 * phshopping-facade-member
 *
 * @description：会员是否是推广师枚举
 *
 * @author：liuy
 *
 * @createTime：2017年5月24日
 *
 * @Copyright @2017 by liuy
 */
public enum MemberIsMarketingEnum {

	IS_MARKETING_BYYES((byte)1,"推广师"),
	IS_MARKETING_BYNO((byte)2,"会员");
	
    /**
	 * 编码
	 */
	private Byte code;
	/**
	 * 描述
	 */
	private String remark;
	
	private MemberIsMarketingEnum(Byte code,String remark){
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
