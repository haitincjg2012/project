package com.ph.shopping.facade.permission.service.check;

import org.apache.commons.lang3.StringUtils;

import com.ph.shopping.facade.permission.dto.UserRoleDTO;

/**
 * 参数校验类
 * @author zhengpeng
 *
 */
public class ParamsCheck {
	
	/**
	 * 新增用户参数校验
	 * @param dto
	 * @return
	 */
	public static boolean checkAddUser(UserRoleDTO dto) {
		if (null == dto) return false;
		if (null == dto.getRoleCode()) return false;
		if (StringUtils.isBlank(dto.getTelphone())) return false;
		return true;
	}

}
