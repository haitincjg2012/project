/**
 * Project Name:alqsoft-easyui
 * File Name:TestUtils.java
 * Package Name:com.alqsoft.testutil
 * Date:2016年5月26日下午5:08:16
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.alqsoft.testutil;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.alqframework.orm.filter.SearchFilter;
import org.junit.Test;







import com.google.common.collect.Maps;

import sun.net.www.protocol.mailto.MailToURLConnection;

/**
 * ClassName:TestUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年5月26日 下午5:08:16 <br/>
 * @author   张灿
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class TestUtils {
	
	@Test
	public void testMap(){
		Map<String, Object> map =  new TreeMap<String, Object>();
		map.put("1_like_name", 2);
		map.put("3_in_phone", 1);
		map.put("2_equal_address", 1);
		Iterator i = map.entrySet().iterator();
		while(i.hasNext()){
			System.out.println(i.next());
		}
		String a = "2_search_";
		System.out.println(a.startsWith("2_search"));
		
	}
	@Test
	public void sumMoney(){
		Double dd = 0d;
		Double dsrc = 100d;
		for(int i=0;i<=730;i++){
			Double temp = dsrc*0.0005;
			dsrc = dsrc - temp;
			dd = dd+temp;
		}
		System.out.println("一年后得到积分："+dd);
	}
	@Test
	public void testRandom(){
		Random r1 = new Random();
		Random r2 = new Random();
		System.out.println(r1.nextDouble());
		System.out.println(r2.nextDouble());
	}
}
