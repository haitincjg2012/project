/**
 * Project Name:alqsoft-easyui
 * File Name:TestDruid.java
 * Package Name:com.alqsoft.druid
 * Date:2016年9月8日上午11:28:37
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.alqsoft.druid;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.zc.dds.pool.DdsDataSourceProxy;


/**
 * ClassName:TestDruid <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年9月8日 上午11:28:37 <br/>
 * @author   张灿
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */

public class TestDruid {
	
	/**
	 * 测试druid数据库连接
	 * @throws Exception 
	 */
	@Test
	public void testConnection() throws Exception{
/*		Map<String,String> druidMap = new HashMap<String,String>();
		druidMap.put(DruidDataSourceFactory.PROP_URL, "jdbc:mysql://127.0.0.1:3306/alqsoft?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull");
		druidMap.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, "com.mysql.jdbc.Driver");
		druidMap.put(DruidDataSourceFactory.PROP_USERNAME, "root");
		druidMap.put(DruidDataSourceFactory.PROP_PASSWORD, "123456");
		DruidDataSource dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(druidMap);
		DruidPooledConnection conn = dataSource.getConnection();
		DruidPooledStatement st = (DruidPooledStatement) conn.createStatement();
		ResultSet result = st.executeQuery("select * from alq_user where id = 1");
		while(result.next()){
			System.out.println(result.getString("user_name"));
		}
		result.close();
		st.close();
		conn.close();*/
	}
	
	@Test
	public void testDdsConnection() throws SQLException{
		DdsDataSourceProxy proxy = new DdsDataSourceProxy();
		proxy.setUrl("jdbc:mysql://127.0.0.1:3306/puhuishop?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull");
		proxy.setDriverClassName("com.mysql.jdbc.Driver");
		proxy.setUsername("root");
		proxy.setPassword("123456");
		Connection c = proxy.getConnection();
		Statement st = c.createStatement();
		ResultSet result = st.executeQuery("select after_num from alq_return_integral where id = 10");
		while(result.next()){
			System.out.println(result.getString("after_num"));
		}
		result.close();
		st.close();
		c.close();
	}
}
