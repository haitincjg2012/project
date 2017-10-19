package com.alqsoft.rpc.impl;

import org.springframework.stereotype.Service;

import com.alqsoft.rpc.RpcHelloService;

/**
 * 测试RPC接口
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2014-5-25 下午6:36:23
 * 
 */
@Service
public class RpcHelloServiceImpl implements RpcHelloService{
	
	@Override
	public String sayHi(String text) {
		System.out.println("调用成功");
		return text;
	}
	
}
