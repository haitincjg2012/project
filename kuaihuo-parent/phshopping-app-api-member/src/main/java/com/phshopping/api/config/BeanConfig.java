/**  
 * @Title:  BeanConfig.java   
 * @Package com.phshopping.api.config   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月13日 下午4:55:54   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.phshopping.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**   
 * @ClassName:  BeanConfig   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年6月13日 下午4:55:54     
 * @Copyright: 2017
 */
@ComponentScan(basePackages = { "com.ph" })
@Configuration
public class BeanConfig {

}
