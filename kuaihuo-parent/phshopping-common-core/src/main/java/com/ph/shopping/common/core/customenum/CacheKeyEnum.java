/**  
 * @Title:  CacheKeyEnum.java   
 * @Package com.ph.shopping.common.core.customenum   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月12日 下午5:18:02   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.core.customenum;

/**   
 * @ClassName:  CacheKeyEnum   
 * @Description:key 枚举值（针对 集合：hash set list,实现一对多）   
 * @author: 李杰
 * @date:   2017年6月12日 下午5:18:02     
 * @Copyright: 2017
 */
public enum CacheKeyEnum {
	
	/**
	 * app 会员
	 */
	APP_MEMBER,
	/**
	 * 踢掉的会员key
	 */
	WASTE_MEMBER
	;
}
