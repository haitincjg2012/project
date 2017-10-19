/**  
 * @Title:  AppScoreQueryEnum.java   
 * @Package com.phshopping.api.appenum   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月26日 下午5:07:33   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.phshopping.api.appenum;

/**   
 * @ClassName:  AppScoreQueryEnum   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年7月26日 下午5:07:33     
 * @Copyright: 2017
 */
public enum AppScoreQueryEnum {

	QUERY_LIST((byte)1,"查询列表"),
	QUERY_ALL((byte)0,"查询所有");
	
	private Byte code;
	
	private String desc;
	
	AppScoreQueryEnum(Byte code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public Byte getCode() {
		return code;
	}

	public void setCode(Byte code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static boolean isExists(Byte code){
		for(AppScoreQueryEnum query : values()){
			if(query.getCode().equals(code)){
				return true;
			}
		}
		return false;
	}
}
