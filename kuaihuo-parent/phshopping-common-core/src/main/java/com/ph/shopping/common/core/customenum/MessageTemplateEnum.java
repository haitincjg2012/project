package com.ph.shopping.common.core.customenum;
/**
 * 
 * @ClassName:  MessageTemplateEnum   
 * @Description:消息模板枚举   
 * @author: 李杰
 * @date:   2017年6月2日 上午11:06:08     
 * @Copyright: 2017
 */
public enum MessageTemplateEnum {

	UNLINE_ORDER_PAY(1l,"线下订单-下单后付款成功"),
	MEMBER_DRAW_CASH(8l, "会员提现成功");
	
	/**
	 * 模板ID
	 */
	private Long templateId;
	/**
	 * 描述
	 */
	private String desc;
	
	MessageTemplateEnum(Long templateId,String desc){
		this.templateId = templateId;
		this.desc = desc;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
