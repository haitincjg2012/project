/**  
 * @Title:  CommunityUpdateDTO.java   
 * @Package com.ph.shopping.facade.system.dto   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月15日 下午2:19:47   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.system.dto;

import javax.validation.constraints.NotNull;

import com.ph.shopping.common.core.base.BaseValidate;

/**   
 * @ClassName:  CommunityUpdateDTO   
 * @Description:社区修改   
 * @author: 李杰
 * @date:   2017年6月15日 下午2:19:47     
 * @Copyright: 2017
 */
public class CommunityUpdateDTO extends BaseValidate{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -3522924566631415719L;

	/**
	 * 主键ID
	 */
	@NotNull(message = "ID不能为空")
	private Long id;
	/**
	 * 社区名称
	 */
	@NotNull(message = "社区名称不能为空")
	private String townName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	
}
