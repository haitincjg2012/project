package com.alqsoft.webservice;

import javax.jws.WebService;

/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2014-5-25 下午6:33:58
 * 
 */
@WebService
public interface WsHelloService {
	public String sayHi(String text);
}