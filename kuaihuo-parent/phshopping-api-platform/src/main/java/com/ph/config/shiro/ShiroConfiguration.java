package com.ph.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.service.IBtnService;
import com.ph.shopping.facade.permission.service.IMenuService;
import com.ph.shopping.facade.permission.vo.BtnVO;
import com.ph.shopping.facade.permission.vo.MenuVO;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/**
 * @项目：Shiro配置
 * @描述：Shiro权限模块
 * @作者： Mr.Shu, liuy
 * @创建时间：2017年5月9日
 * @Copyright @2017 by Mr.Shu,liuy
 */
@Configuration
public class ShiroConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean
    public EhCacheManager getEhCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return em;
    }

    @Bean(name = "myShiroRealm")
    public MyShiroRealm myShiroRealm(EhCacheManager cacheManager) {
        MyShiroRealm realm = new MyShiroRealm();
        realm.setCacheManager(cacheManager);
        return realm;
    }

    @Bean(name = "sessionIdCookie")
    public SimpleCookie getSessionIdCookie() {
        SimpleCookie cookie = new SimpleCookie("sid");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(-1);
        return cookie;
    }

    @Bean(name = "sessionValidationScheduler")
    public ExecutorServiceSessionValidationScheduler getExecutorServiceSessionValidationScheduler() {
        ExecutorServiceSessionValidationScheduler scheduler = new ExecutorServiceSessionValidationScheduler();
        scheduler.setInterval(900000);
        return scheduler;
    }

    /**
     * session共享管理
     *
     * @param cacheManager
     * @return
     */
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager defaultWebSessionManager(EhCacheManager cacheManager) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setCacheManager(cacheManager);
        sessionManager.setGlobalSessionTimeout(1800000);
//        sessionManager.setGlobalSessionTimeout(30000);
        sessionManager.setSessionValidationScheduler(getExecutorServiceSessionValidationScheduler());
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setDeleteInvalidSessions(true);
//        sessionManager.setSessionIdCookie(getSessionIdCookie());
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionListeners(getSessionListeners());
        return sessionManager;
    }

    /**
     * cookie对象
     *
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        System.out.println("ShiroConfiguration.rememberMeCookie()");
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    /**
     * @描述：Session监听器
     * @param: null
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/6/15 11:58
     */
    @Bean
    public Collection<SessionListener> getSessionListeners() {
        Collection<SessionListener> sessionListeners = new ArrayList<>();
        MySessionListener mySessionListener = new MySessionListener();
        sessionListeners.add(mySessionListener);
        return sessionListeners;
    }

    /**
     * cookie管理对象;
     *
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        System.out.println("ShiroConfiguration.rememberMeManager()");
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }


    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(MyShiroRealm myShiroRealm, DefaultWebSessionManager sessionManager) {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealm(myShiroRealm);
        //用户授权/认证信息Cache, 采用EhCache
        dwsm.setCacheManager(getEhCacheManager());
        //注入记住我管理器
        dwsm.setRememberMeManager(rememberMeManager());
        //session共享管理器
        dwsm.setSessionManager(sessionManager);
        return dwsm;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    /**
     * @描述：加载shiroFilter权限控制规则（从数据库读取然后配置）
     * @param: shiroFilterFactoryBean
     * @param: menuService
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/9 14:33
     */
    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean, IMenuService menuService, IBtnService btnService) {
        logger.info("##################从数据库读取权限规则，加载到shiroFilter中##################");
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        //filterChainDefinitionMap.put("/logout", "logout");

        filterChainDefinitionMap.put("/index", "user");

        Result result = menuService.getMenuList();
        //获取所有菜单（后面要改为获取所有权限）
        @SuppressWarnings("unchecked")
		List<MenuVO> MenuVOs = (List<MenuVO>) result.getData();
        for (MenuVO mv : MenuVOs) {
            logger.info(mv.getMenuName() + "-" + mv.getMenuUrl());
            String url = mv.getMenuUrl();
            if (url != null && !"".equals(url.trim())) {
                filterChainDefinitionMap.put(url, "user,perms[" + url + "]");
            }
        }

        //获取所有按钮（后面要改为获取所有权限）
        List<BtnVO> btnVOS = btnService.getBtnList();
        for (BtnVO b : btnVOS) {
            logger.info(b.getBtnName() + "-" + b.getBtnUrl());
            String url = b.getBtnUrl();
            String code = b.getBtnCode();
            if (url != null && !"".equals(url.trim())) {
                filterChainDefinitionMap.put(url, "user,perms[" + code + "]");
            }
        }

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    /**
     * @描述：shiroFilter权限过滤器
     *
     * @param: securityManager
     * @param: myShiroRealm
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/15 14:00
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager, MyShiroRealm myShiroRealm) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new MShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的连接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        IMenuService menuService = myShiroRealm.getMenuService();
        IBtnService btnService = myShiroRealm.getBtnService();

        loadShiroFilterChain(shiroFilterFactoryBean, menuService, btnService);
        return shiroFilterFactoryBean;
    }

}
