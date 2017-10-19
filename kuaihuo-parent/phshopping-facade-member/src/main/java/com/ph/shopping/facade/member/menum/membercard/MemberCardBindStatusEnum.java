package com.ph.shopping.facade.member.menum.membercard;

/**
 * 
 * phshopping-facade-member
 *
 * @description：会员卡绑定状态枚举
 *
 * @author：liuy
 *
 * @createTime：2017年5月24日
 *
 * @Copyright @2017 by liuy
 */
public enum MemberCardBindStatusEnum {

    MEMBERCARD_BINDING((byte) 0, "已绑定"),
    MEMBERCARD_UNBINDING((byte) 1, "未绑定(挂失)");

    /**
	 * 编码
	 */
	private Byte code;
	/**
	 * 描述
	 */
	private String remark;
	
	private MemberCardBindStatusEnum(Byte code,String remark){
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
