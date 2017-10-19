package com.alqsoft.session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * session监听
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-10-26 下午8:02:41
 * 
 */
public class MySessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		
		
	}

}
