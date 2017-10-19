package com.alqsoft.utils;
 
/**
 * 角色枚举
 * 
 * @author sunhuijie
 *
 * @date 2016年10月13日
 *
 */
public enum UploadFileName {
	HUNTER_HEADIMG {//批发商头像上传
		@Override
		public String getName() {
			return "hunterheadimg";
		}
	},
	HUNTER_RULE{//批发商法则上传
		@Override
		public String getName() {
			return "hunterrule";
		}
	},
	PRODUCT_TRACE {//商品跟踪
		@Override
		public String getName() {
			return "productTrace";
		}
	};
	public abstract String getName();
}
