package com.ph.shopping.facade.member.vo;

import java.io.Serializable;

/**
 * 
* @ClassName: MemberIntegralSourceVo  
* @Description: 会员积分来源信息
* @author lijie  
* @date 2017年3月24日  
*
 */
public class MemberIntegralSourceVO implements Serializable{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = -3134641432909948319L;
	/**
	 * 交易码
	 */
	private Integer transCode;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 交易码描述
	 */
	private String remark;
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getTransCode() {
		return transCode;
	}
	public void setTransCode(Integer transCode) {
		this.transCode = transCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	} 
	
	
}
