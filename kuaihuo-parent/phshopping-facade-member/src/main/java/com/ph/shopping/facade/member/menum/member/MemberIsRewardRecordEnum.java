package com.ph.shopping.facade.member.menum.member;

public enum MemberIsRewardRecordEnum {

	IS_YES((byte)1,"需要记录奖励金流水");
	
    /**
	 * 编码
	 */
	private Byte code;
	/**
	 * 描述
	 */
	private String remark;
	private MemberIsRewardRecordEnum(Byte code, String remark) {
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
