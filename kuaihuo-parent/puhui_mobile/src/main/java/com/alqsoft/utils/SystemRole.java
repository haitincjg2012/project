package com.alqsoft.utils;
 
/**
 * 角色枚举
 * 
 * @author sunhuijie
 *
 * @date 2016年10月13日
 *
 */
public enum SystemRole {
	USER {//用户
		@Override
		public String getName() {
			return "user";
		}
	},
	SESSIONROLE {//角色标识
		@Override
		public String getName() {
			return "sessionrole";
		}
	};
	public abstract String getName();
}
