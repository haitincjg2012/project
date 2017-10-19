package com.ph.shopping.facade.member.menum.profitopt;

/**
 * 
 * phshopping-facade-member
 *
 * @description：影响分润操作流水表 操作类型枚举 会员模块
 *
 * @author：liuy
 *
 * @createTime：2017年5月25日
 *
 * @Copyright @2017 by liuy
 */
public enum ProfitOptLogUseTypeEnum {

	UNFROZEN((byte)0,"解冻"),
	FROZEN((byte)1,"冻结"),
	NO_PASS((byte)2,"审核不通过"),
	PASS((byte)3,"审核通过");
	
    /**
	 * 编码
	 */
	private Byte code;
	/**
	 * 描述
	 */
	private String remark;
	
	private ProfitOptLogUseTypeEnum(Byte code,String remark){
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
