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
	HUNTER_INDUSTRY {//行业
		@Override
		public String getName() {
			return "industryimg";
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
