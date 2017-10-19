/**  
 * @Title:  Token.java   
 * @Package com.ph.shopping.common.core.other.annotation   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月8日 下午5:39:30   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.core.other.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ph.shopping.common.core.customenum.CacheKeyEnum;
import com.ph.shopping.common.core.customenum.CacheKeyTypeEnum;

/**   
 * @ClassName:  Token   
 * @Description:token   
 * @author: 李杰
 * @date:   2017年6月8日 下午5:39:30     
 * @Copyright: 2017
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {
	/**
	 * 
	 * @Title: key   
	 * @Description: 校验key   
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	String key();
	/**
	 * 
	 * @Title: isCache   
	 * @Description: 是否被缓存  
	 * @param: @return      
	 * @return: boolean
	 * @author：李杰      
	 * @throws
	 */
	boolean isCache() default false;
	/**
	 * 
	 * @Title: cacheKey   
	 * @Description: 指定缓存key（针对 hash 列表的一对多存储时）   
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	CacheKeyEnum cacheKey() default CacheKeyEnum.APP_MEMBER;
	/**
	 * 
	 * @Title: cacheType   
	 * @Description: 缓存key类型   
	 * @param: @return      
	 * @return: CacheKeyTypeEnum
	 * @author：李杰      
	 * @throws
	 */
	CacheKeyTypeEnum cacheType() default CacheKeyTypeEnum.SINGLE;
}
