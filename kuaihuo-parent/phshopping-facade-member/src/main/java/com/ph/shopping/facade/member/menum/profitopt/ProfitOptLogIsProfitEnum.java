package com.ph.shopping.facade.member.menum.profitopt;

/**
 * 
 * phshopping-facade-member
 *
 * @description：是否可分润枚举（用于记录会员分润权限操作流水）
 *
 * @author：liuy
 *
 * @createTime：2017年5月25日
 *
 * @Copyright @2017 by liuy
 */
public enum ProfitOptLogIsProfitEnum {

	PROFIT_NO((byte)0,"不分润"),
	PROFIT_YES((byte)1,"可分润");
	
    /**
	 * 编码
	 */
	private Byte code;
	/**
	 * 描述
	 */
	private String remark;
	
	private ProfitOptLogIsProfitEnum(Byte code,String remark){
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
