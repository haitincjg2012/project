package com.phshopping.api.constant;

public enum UploadFileName {
	DISH_TRACE {//商品跟踪
		@Override
		public String getName() {
			return "dishTrace";
		}
	};
	public abstract String getName();

}
