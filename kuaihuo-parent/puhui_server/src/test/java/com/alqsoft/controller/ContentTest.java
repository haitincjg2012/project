package com.alqsoft.controller;

import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alqsoft.entity.sms.ClientResponse;
import com.alqsoft.entity.sms.SmsContent;


/**
 * 
 * @Title: ContentTest.java
 * @Description: TODO
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月19日 上午11:28:57
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public class ContentTest {
//	@Test
	public void test1(){
		String url = "http://localhost:8080/alqsoft-easyui/view/webchat/sendChatMsg";
		SmsContent sms = new SmsContent();
		sms.setContent("你好吗？");
		sms.setChatGroupId(2L);
		sms.setHeadImgPath("");
		sms.setMsgType(1);
		sms.setSmsReceiveId(1002L);
		sms.setSmsSenderId(1001L);
		sms.setSmsSenderName("zhangsan");
		sms.setSmsReceiveName("lisi");
		sms.setUniqueKey("sdf897464sdfsdfsd132");
		MultiValueMap<String,Object> param=new LinkedMultiValueMap<String, Object>();
		param.add("content",sms);
		
		System.out.println(getHttpsToPost(url, param));
		
	}
//	@Test
	public void test2(){
		String url = "http://localhost:8080/alqsoft-easyui/view/webchat/sendChatWebResponse";
		ClientResponse res = new ClientResponse();
		res.setUniqueKey("123s1d23fsd56f465");
		res.setAccountId("1001");
		res.setMessageKey("clientResponse");
		MultiValueMap<String,Object> param=new LinkedMultiValueMap<String, Object>();
		param.add("content",res);
		
		System.out.println(getHttpsToPost(url, param));
		
	}
	@Test
	public void test3(){
		String url = "http://localhost:8080/alqsoft-easyui/view/webchat/sendChatGroupWebResponse";
		ClientResponse res = new ClientResponse();
		res.setUniqueKey("123s1d23fsd56f465");
		res.setAccountId("1001");
		res.setMessageKey("clientResponse");
		MultiValueMap<String,Object> param=new LinkedMultiValueMap<String, Object>();
		param.add("content",res);
		param.add("accountId", "1001");
		
		System.out.println(getHttpsToPost(url, param));
		
	}
	
	public static String getHttpsToPost(String url,MultiValueMap<String, Object> map){
        RestTemplate restTemplate=new RestTemplate();
        String result=restTemplate.postForObject(url, map, String.class);
        return result;
        
    }
	
}
