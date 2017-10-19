package com.alqsoft.webservice;

import javax.jws.WebService;


/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-1-14 下午4:53:19
 * 
 */
@WebService
public interface WsHelloService {
	public String sayHi(String text);
}
