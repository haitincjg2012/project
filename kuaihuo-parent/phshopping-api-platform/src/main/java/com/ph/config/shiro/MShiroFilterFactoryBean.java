package com.ph.config.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.BeanInitializationException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @项目：Shiro配置
 * @描述：Shiro权限模块
 * @作者： Mr.Shu, liuy
 * @创建时间：2017年5月9日
 * @Copyright @2017 by Mr.Shu,liuy
 */
public class MShiroFilterFactoryBean extends ShiroFilterFactoryBean {
    // 对ShiroFilter来说，需要直接忽略的请求
    private Set<String> ignoreExt = new HashSet<String>();
    // 信任的资源路径
    private Set<String> trustResource = new HashSet<String>();
    
    public MShiroFilterFactoryBean() {
        super();
        ignoreExt.add(".jpg");
        ignoreExt.add(".png");
        ignoreExt.add(".gif");
        ignoreExt.add(".bmp");
        ignoreExt.add(".js");
        ignoreExt.add(".css");
        
        trustResource.add("/member/usersync/");
    }

    @Override
    protected AbstractShiroFilter createInstance() throws Exception {

        SecurityManager securityManager = getSecurityManager();
        if (securityManager == null) {
            String msg = "SecurityManager property must be set.";
            throw new BeanInitializationException(msg);
        }

        if (!(securityManager instanceof WebSecurityManager)) {
            String msg = "The security manager does not implement the WebSecurityManager interface.";
            throw new BeanInitializationException(msg);
        }

        FilterChainManager manager = createFilterChainManager();

        PathMatchingFilterChainResolver chainResolver = new PathMatchingFilterChainResolver();
        chainResolver.setFilterChainManager(manager);

        return new MSpringShiroFilter((WebSecurityManager) securityManager, chainResolver);
    }

    private final class MSpringShiroFilter extends AbstractShiroFilter {

        protected MSpringShiroFilter(WebSecurityManager webSecurityManager, FilterChainResolver resolver) {
            super();
            if (webSecurityManager == null) {
                throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
            }
            setSecurityManager(webSecurityManager);
            if (resolver != null) {
                setFilterChainResolver(resolver);
            }
        }

        @Override
        protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse,
                                        FilterChain chain) throws ServletException, IOException {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            String str = request.getRequestURI().toLowerCase();
            // 因为ShiroFilter 拦截所有请求（在上面我们配置了urlPattern 为 * ，当然你也可以在那里精确的添加要处理的路径，这样就不需要这个类了），而在每次请求里面都做了session的读取和更新访问时间等操作，这样在集群部署session共享的情况下，数量级的加大了处理量负载。
            // 所以我们这里将一些能忽略的请求忽略掉。
            // 当然如果你的集群系统使用了动静分离处理，静态资料的请求不会到Filter这个层面，便可以忽略。
            boolean flag = true;
            int idx = 0;
            if ((idx = str.indexOf(".")) > 0) {
                str = str.substring(idx);
                if (ignoreExt.contains(str.toLowerCase()))
                    flag = false;
            }
            // 判断是否是信任的url
            if(isTrustResource(str)){
            	flag = false;
            }
            
            //当在sessions里找不到自己的session时需要把JSESSIONID赋值为null  //Mr.Shu 2017.5.18
            DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) this.getSecurityManager();
            DefaultSessionManager sessionManager = (DefaultSessionManager) securityManager.getSessionManager();
            MemorySessionDAO memorySessionDAO = (MemorySessionDAO) sessionManager.getSessionDAO();
            Collection<Session> sessions = memorySessionDAO.getActiveSessions();

            boolean isHave = false;
            //把JSESSIONID设置为null
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length != 0) {
                for (Cookie c : cookies) {
                    if ("JSESSIONID".equals(c.getName())) {
                        for (Session s : sessions) {
                            if (s.getId().equals(c.getValue())) {
                                //判断session是否失效
                                if (s.getLastAccessTime().getTime() + s.getTimeout() <= new Date().getTime()) {
                                    isHave = false;
                                } else {
                                    isHave = true;
                                }
                            }
                        }
                        if (!isHave) {
                            c.setValue(null);
                        }
                    }
                }
            }

            if (flag) {
                try {
                    super.doFilterInternal(servletRequest, servletResponse, chain);
                } catch (Exception e) {
                    System.out.print("===============需重新登录！=============");
                }

            } else {
                try {
                    chain.doFilter(servletRequest, servletResponse);
                } catch (Exception e) {
                    System.out.print("===============需重新登录！=============");
                }
            }

        }

    }
    
	private boolean isTrustResource(String url) {
		if (StringUtils.isNotBlank(url)) {
			for (String str : trustResource) {
				if (url.contains(str)) {
					return true;
				}
			}
		}
		return false;
	}
}
