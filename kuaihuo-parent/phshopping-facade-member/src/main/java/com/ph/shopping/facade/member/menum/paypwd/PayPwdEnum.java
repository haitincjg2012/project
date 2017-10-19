package com.ph.shopping.facade.member.menum.paypwd;

/**
 * 
 * phshopping-facade-member
 *
 * @description：支付密码使用者类型枚举
 *
 * @author：liuy
 *
 * @createTime：2017年5月24日
 *
 * @Copyright @2017 by liuy
 */
public enum PayPwdEnum {

	MEMBER_PAY_PWD((byte)1,"支付密码类型(会员)"),
	PLATFORM_PAY_PWD((byte)2,"支付密码类型(平台)");
	
    /**
	 * 编码
	 */
	private Byte code;
	/**
	 * 描述
	 */
	private String remark;
	
	private PayPwdEnum(Byte code,String remark){
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
