package com.ph.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;


/**
 * @项目：com.ph.config.shiro
 * @描述：
 * @作者： Mr.Shu
 * @创建时间：2017/6/15 10:19
 * @Copyright @2017 by Mr.Shu
 */
public class MySessionListener extends SessionListenerAdapter {
    @Override
    public void onExpiration(Session session) {//会话过期时触发
        System.out.println("会话过期：" + session.getId() + "回到登录页面！");
    }
}
