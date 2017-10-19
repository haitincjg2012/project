package com.ph.shopping.facade.member.menum.sms;

/**
 * 
 * phshopping-facade-member
 *
 * @description：
 *
 * @author：liuy
 *
 * @createTime：2017年5月24日
 *
 * @Copyright @2017 by liuy
 */
public enum SmsSendStatusEnum {

	SMS_SEND((byte)1,"短信发送成功状态"),
	SMS_SEND_FAIL((byte)0,"短信发送失败状态");
	
    /**
	 * 编码
	 */
	private Byte code;
	/**
	 * 描述
	 */
	private String remark;
	
	private SmsSendStatusEnum(Byte code,String remark){
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
