package com.ph.shopping.facade.member.menum.promotion;

/**
 * 
 * phshopping-facade-member
 *
 * @description：推广师删除枚举
 *
 * @author：liuy
 *
 * @createTime：2017年5月24日
 *
 * @Copyright @2017 by liuy
 */
public enum PromotionDeleteEnum {

    PROMOTION_DELETE((byte) 0, "推广师删除"),
    PROMOTION_NORMAL((byte) 1, "推广师未删除");
	
    /**
	 * 编码
	 */
	private Byte code;
	/**
	 * 描述
	 */
	private String remark;
	
	private PromotionDeleteEnum(Byte code,String remark){
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
