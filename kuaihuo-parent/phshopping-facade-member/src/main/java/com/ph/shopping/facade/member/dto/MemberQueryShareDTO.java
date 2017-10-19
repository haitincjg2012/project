/**  
 * @Title:  MemberQueryShareDTO.java   
 * @Package com.ph.shopping.facade.member.dto   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月25日 上午11:40:34   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.member.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ph.shopping.common.core.base.BaseValidate;

/**   
 * @ClassName:  MemberQueryShareDTO   
 * @Description:查询分享记录数据   
 * @author: 李杰
 * @date:   2017年7月25日 上午11:40:34     
 * @Copyright: 2017
 */
public class MemberQueryShareDTO extends BaseValidate {

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -8367015672789605550L;
	/**
	 * 被分享人手机号
	 */
	@NotBlank(message="被分享人手机号不能为空")
	private String toTelPhone;
	/**
	 * 分享的类型 0、会员 1、商户
	 */
	@NotNull(message="分享人类型不能为空")
	private Byte type;
	
	public String getToTelPhone() {
		return toTelPhone;
	}
	public void setToTelPhone(String toTelPhone) {
		this.toTelPhone = toTelPhone;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
}
