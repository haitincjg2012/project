package com.alqsoft.utils;
/**
 * 
 * @Description: TODO
 * @author wudi
 * @version v1.0
 * @create-time 2017年6月5日 下午4:26:39
 * 
 */
public enum  HunterLevelEnums {
	
	LT_LEVEL_COMMON("批发"),
	LT_LEVEL_TOP("批发"),
	LT_LEVEL_PROFESSOR("批发"),
	LT_LEVEL_ADVANCED("批发");
	
	private String data;
	HunterLevelEnums(String data){
		this.data=data;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	

}
