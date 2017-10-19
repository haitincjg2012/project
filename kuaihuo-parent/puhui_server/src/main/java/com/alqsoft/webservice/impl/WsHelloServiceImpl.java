package com.alqsoft.webservice.impl;

import javax.jws.WebService;

import org.springframework.stereotype.Service;

import com.alqsoft.webservice.WsHelloService;

/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2014-5-25 下午6:36:23
 * 
 */
@WebService(endpointInterface="com.alqsoft.webservice.WsHelloService")
@Service
public class WsHelloServiceImpl implements WsHelloService{
	
	@Override
	public String sayHi(String text) {
		System.out.println("调用成功");
		return text;
	}
	
}
