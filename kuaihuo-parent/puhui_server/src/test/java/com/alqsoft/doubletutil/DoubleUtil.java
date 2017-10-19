/**
 * Project Name:alqsoft-easyui
 * File Name:DoubleUtil.java
 * Package Name:com.alqsoft.doubletutil
 * Date:2016年10月12日下午5:13:46
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.alqsoft.doubletutil;

import org.alqframework.utils.DoubleUtils;
import org.junit.Test;

/**
 * ClassName:DoubleUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年10月12日 下午5:13:46 <br/>
 * @author   张灿
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class DoubleUtil {
	
	@Test
	public void test(){
		Double d =10103*0.0003;
		System.out.println(DoubleUtils.scaleDouble(d,5));
		System.out.println(DoubleUtils.formatRound(d, 5));
	}
	
	@Test
	public void testA(){
		String s ="10.12";
		Double d = DoubleUtils.multiply(new Double(s), 10000d);
		System.out.println(d.longValue());
	}
}
