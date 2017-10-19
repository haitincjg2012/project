package com.ph.shopping.facade.member.menum.sms;

/**
 * 
 * phshopping-facade-member
 *
 * @description：短信是否有效枚举
 *
 * @author：liuy
 *
 * @createTime：2017年5月24日
 *
 * @Copyright @2017 by liuy
 */
public enum SmsAvailableEnum {
	
	SMS_AVAILABLE((byte)1,"短信有效状态"),
	SMS_INVALID((byte)2,"短信无效效状态");
	
    /**
	 * 编码
	 */
	private Byte code;
	/**
	 * 描述
	 */
	private String remark;
	
	private SmsAvailableEnum(Byte code,String remark){
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
