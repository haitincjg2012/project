package com.ph.shopping.facade.member.menum.promotion;

/**
 * 
 * phshopping-facade-member
 *
 * @description：推广师审核状态
 *
 * @author：liuy
 *
 * @createTime：2017年5月24日
 *
 * @Copyright @2017 by liuy
 */
public enum PromotionStatusEnum {

    PROMOTION_AUDIT((byte) 0, "推广师待审核"),
    PROMOTION_ADOPT((byte) 1, "推广师审核通过"),
    PROMOTION_FAIL((byte) 2, "推广师审核未通过"),
    PROMOTION_NOT_PROFIT((byte) 3, "推广师不分润");
    
    /**
	 * 编码
	 */
	private Byte code;
	/**
	 * 描述
	 */
	private String remark;
	
	private PromotionStatusEnum(Byte code,String remark){
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
