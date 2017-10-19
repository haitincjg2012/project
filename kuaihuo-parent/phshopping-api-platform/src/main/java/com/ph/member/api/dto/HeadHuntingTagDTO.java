/**  
 * @Title:  HeadHuntingTagDTO.java   
 * @Package com.ph.member.api.dto   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月7日 下午4:21:52   
 * @version V1.0 
 * @Copyright: 2017
 */
package com.ph.member.api.dto;

import org.hibernate.validator.constraints.NotBlank;

import com.ph.shopping.common.core.base.BaseValidate;

/**
 * @ClassName: HeadHuntingTagDTO
 * @Description:批发商标签
 * @author: 李杰
 * @date: 2017年7月7日 下午4:21:52
 * @Copyright: 2017
 */
public class HeadHuntingTagDTO extends BaseValidate {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -1502845812496205496L;
	/**
	 * 标签ID
	 */
	@NotBlank(message="[标签ID]不能为空")
	private String tagId;
	/**
	 * 标签名称
	 */
	@NotBlank(message="标签名不能为空")
	private String tagName;
	
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
}
