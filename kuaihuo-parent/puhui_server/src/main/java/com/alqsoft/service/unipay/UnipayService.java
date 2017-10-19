/**
 * Project Name:alqsoft-easyui
 * File Name:UnipayService.java
 * Package Name:com.alqsoft.service.unipay
 * Date:2015年10月31日下午5:27:22
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.alqsoft.service.unipay;
/**
 * ClassName:UnipayService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年10月31日 下午5:27:22 <br/>
 * @author   zc
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public interface UnipayService {
	
	/**
	 * 返回银联处理过的html内容
	 * @param paynum
	 * @return
	 */
	String getHtml(Long paynum,String orderNum);
	
}
