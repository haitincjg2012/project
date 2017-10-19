/**
 * Project Name:alqsoft-easyui
 * File Name:RSAUtils.java
 * Package Name:com.alqsoft.RSAUtils
 * Date:2016年10月17日下午5:59:49
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.alqsoft.RSAUtils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import java.util.Set;

import org.alqframework.security.RSAUtils;
import org.junit.Test;

/**
 * ClassName:RSAUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年10月17日 下午5:59:49 <br/>
 * @author   张灿
 * @version  测试秘钥加密 和 解密
 * @since    JDK 1.8
 * @see 	 
 */
public class RSAUtilsTest {
	
	@Test
	public void testTwo() throws Exception{
		Map<String,Object> maps = RSAUtils.genKeyPair();
		Set<String> sets = maps.keySet();
		RSAPublicKey pk = (RSAPublicKey) maps.get("RSAPublicKey");
		RSAPrivateKey prk = (RSAPrivateKey) maps.get("RSAPrivateKey");
		
		System.out.println(RSAUtils.getPrivateKey(maps));
		System.out.println(RSAUtils.getPublicKey(maps));
		
		
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		System.out.println(keyPair.getPrivate());
		System.out.println(keyPair.getPrivate());
	}
}
