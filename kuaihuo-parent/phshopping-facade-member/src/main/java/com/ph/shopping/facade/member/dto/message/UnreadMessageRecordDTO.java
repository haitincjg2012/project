package com.ph.shopping.facade.member.dto.message;

import javax.validation.constraints.NotNull;

import com.ph.shopping.common.core.base.BaseValidate;
/**
 * 
 * @ClassName:  UnreadMessageRecordDTO   
 * @Description:未读记录查询dto   
 * @author: 李杰
 * @date:   2017年5月31日 下午4:25:01     
 * @Copyright: 2017
 */
public class UnreadMessageRecordDTO extends BaseValidate{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 6334537703073511453L;
	/**
	 * 用户ID
	 */
	@NotNull(message = "[用户ID]不能为空")
	private Long userId;
	/**
	 * 用户类型
	 */
	@NotNull(message = "[用户类型]不能为空")
	private Byte userType;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Byte getUserType() {
		return userType;
	}
	public void setUserType(Byte userType) {
		this.userType = userType;
	}
}
