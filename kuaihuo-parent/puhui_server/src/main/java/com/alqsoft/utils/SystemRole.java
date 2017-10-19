package com.alqsoft.utils;
/**
 * 角色枚举工具
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-11-1 下午3:07:26
 * 
 */
public enum SystemRole {
	SUPERADMIN {
		@Override
		public String getName() {
			return "alqsoft";
		}
	},ADMIN {
		@Override
		public String getName() {
			return "admin";
		}
	}/*,INDUSTRYASSOCIATION {
		@Override
		public String getName() {
			return "industryassociation";
		}
	}*/;
	public abstract String getName();
}
