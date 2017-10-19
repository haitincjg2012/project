//package com.boot.shiro;
//
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.shiro.cache.ehcache.EhCacheManager;
//import org.apache.shiro.cas.CasSubjectFactory;
//import org.apache.shiro.codec.Base64;
//import org.apache.shiro.spring.LifecycleBeanPostProcessor;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.CookieRememberMeManager;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.apache.shiro.web.servlet.SimpleCookie;
//import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
//import org.crazycake.shiro.RedisCacheManager;
//import org.crazycake.shiro.RedisManager;
//import org.crazycake.shiro.RedisSessionDAO;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.boot.entity.Permission;
//import com.boot.service.PermissionService;
//
//
///**
// * Shiro 配置
// * Apache Shiro 核心通过 Filter 来实现，就好像SpringMvc 通过DispachServlet 来主控制一样。
// * 既然是使用 Filter 一般也就能猜到，是通过URL规则来进行过滤和权限校验，所以我们需要定义一系列关于URL的规则和访问权限。
// * @author ly
// *
// */
//@Configuration
//public class ShiroConfiguration {
//
//    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);
//
//	@Value("${spring.redis.host}")
//	private String host;
//
//	@Value("${spring.redis.port}")
//	private int port;
//	
//	@Value("${spring.redis.timeout}")
//    private int timeout;
//	
//    /**
//     * 自定义shiro Realm
//     * @param cacheManager
//     * @return
//     */
//    @Bean(name = "myShiroRealm")
//    public MyShiroRealm myShiroRealm(EhCacheManager cacheManager) {  
//        MyShiroRealm realm = new MyShiroRealm(); 
//        realm.setCacheManager(cacheManager);
//        return realm;
//    }  
//    
//    
//    /**
//     * 注入权限service
//     * @return
//     */
//    @Bean(name = "permissionService")
//    public PermissionService getPermissionService() {  
//    	logger.info("##################注入权限service##################");
//    	PermissionService permissionService = new PermissionService();
//        return permissionService;
//    } 
//    
//    /**
//     * 缓存管理
//     * @return
//     */
//    @Bean
//    public EhCacheManager getEhCacheManager() {  
//        EhCacheManager em = new EhCacheManager();  
//        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");  
//        return em;  
//    }  
//
//	 /**
//	  * 保证实现Shiro内部lifecycle函数的bean执行
//	  * @return
//	  */
//    @Bean(name = "lifecycleBeanPostProcessor")
//    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
//    	return new LifecycleBeanPostProcessor();
//    }
//
//    @Bean
//    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
//        daap.setProxyTargetClass(true);
//        return daap;
//    }
//
//    /**
//     * 开启Shiro Spring AOP权限注解的支持
//     * @param securityManager
//     * @return
//     */
//    @Bean
//    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
//        aasa.setSecurityManager(securityManager);
//        return aasa;
//    }
//	
//	/**
//     * cookie对象;
//     * @return
//     */
//    public SimpleCookie rememberMeCookie(){
//       //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
//       SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//       //<!-- 记住我cookie生效时间30天 ,单位秒;-->
//       simpleCookie.setMaxAge(2592000);
//       return simpleCookie;
//    }
//    
//    /**
//     * cookie管理对象;记住我功能
//     * @return
//     */
//    public CookieRememberMeManager rememberMeManager(){
//       CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//       cookieRememberMeManager.setCookie(rememberMeCookie());
//       //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
//       cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
//       return cookieRememberMeManager;
//    }
//    
//    /**
//     * shiro安全管理器
//     * @param myShiroRealm
//     * @return
//     */
//    @Bean(name = "securityManager")
//    public DefaultWebSecurityManager getDefaultWebSecurityManager(MyShiroRealm myShiroRealm) {
//        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
//        //设置自定义Realm
//        dwsm.setRealm(myShiroRealm);
//        //用户授权/认证信息Cache, 采用EhCache 缓存
//        dwsm.setCacheManager(getEhCacheManager());
//		// 指定 SubjectFactory
//		dwsm.setSubjectFactory(new CasSubjectFactory());
//		//注入记住我管理器
//		dwsm.setRememberMeManager(rememberMeManager());
//        return dwsm;
//    }
//
//    /**
//     * 加载shiroFilter权限控制规则（从数据库读取然后配置）
//     * Filter Chain定义说明
//       1、一个URL可以配置多个Filter，使用逗号分隔
//       2、当设置多个过滤器时，全部验证通过，才视为通过
//       3、部分过滤器可指定参数，如perms，roles
//     * @create  2016年1月14日
//     */
//    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean){
//        //---------------- 下面这些规则配置最好配置到配置文件中 ------------------------
//    	
//    	//拦截器.
//        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
//        //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
//       	filterChainDefinitionMap.put("/logout", "logout");
//       	
//        //配置记住我或认证通过可以访问的地址
//        filterChainDefinitionMap.put("/user", "user");
//        filterChainDefinitionMap.put("/", "user");
//        
//        // authc：该过滤器下的页面必须验证后才能访问，它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
//    	filterChainDefinitionMap.put("/user", "authc");// 这里为了测试，只限制/user，实际开发中请修改为具体拦截的请求规则
//    	
//    	Permission permission = new Permission();
//    	
//    	logger.info("##################从数据库获取url权限开始##################");
//    	//从数据库获取
//    	List<Permission> list = getPermissionService().getPermissionList(permission);
//    	for (Permission sysPermissionInit : list) {
//    		String permissionStr = "authc,perms["+sysPermissionInit.getUrl()+"]";
//    		filterChainDefinitionMap.put(sysPermissionInit.getUrl(),permissionStr);
//    	}
//    	logger.info("##################从数据库获取url权限结束##################");
////		filterChainDefinitionMap.put("/user/**", "authc,perms[user:edit]");// 这里为了测试，固定写死的值，也可以从数据库或其他配置中读取
////		filterChainDefinitionMap.put("/api/supplier/**", "authc,perms[/api/supplier/listPage]");
////		filterChainDefinitionMap.put("/api/supplier/addPage", "authc,perms[/api/supplier/addPage]");
////		filterChainDefinitionMap.put("/api/supplier/updatePage", "authc,perms[/api/supplier/updatePage]");
//		
//		// anon：所有url都都可以匿名访问
//		filterChainDefinitionMap.put("/login", "anon");
//
//		filterChainDefinitionMap.put("/css/**", "anon");
//		filterChainDefinitionMap.put("/js/**", "anon");
//		
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//    }
//
//    /**
//     * ShiroFilterFactoryBean 处理拦截资源文件问题。
//     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，
//     * 因为在初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
//     *
//     */
//    @Bean(name = "shiroFilter")
//    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
//
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new MShiroFilterFactoryBean();
//        
//        // 必须设置 SecurityManager  
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        
//        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
//        shiroFilterFactoryBean.setLoginUrl("/login");
//        
//        // 登录成功后要跳转的连接
//        shiroFilterFactoryBean.setSuccessUrl("/user");
//        
//        //未授权界面;
//        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
//
//        //加载shiroFilter权限控制规则
//        loadShiroFilterChain(shiroFilterFactoryBean);
//        
//        return shiroFilterFactoryBean;
//    }
//
//	/**
//	 * 配置shiro redisManager
//	 * 使用的是shiro-redis开源插件
//	 * @return
//	 */
//	public RedisManager redisManager() {
//		RedisManager redisManager = new RedisManager();
//		redisManager.setHost(host);
//		redisManager.setPort(port);
//		redisManager.setExpire(1800);// 配置缓存过期时间
//		redisManager.setTimeout(timeout);
//		// redisManager.setPassword(password);
//		return redisManager;
//	}
//
//	/**
//	 * cacheManager 缓存 redis实现
//	 * 使用的是shiro-redis开源插件
//	 * @return
//	 */
//	public RedisCacheManager cacheManager() {
//		RedisCacheManager redisCacheManager = new RedisCacheManager();
//		redisCacheManager.setRedisManager(redisManager());
//		return redisCacheManager;
//	}
//
//	/**
//	 * RedisSessionDAO shiro sessionDao层的实现 通过redis
//	 * 使用的是shiro-redis开源插件
//	 */
//	@Bean
//	public RedisSessionDAO redisSessionDAO() {
//		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//		redisSessionDAO.setRedisManager(redisManager());
//		return redisSessionDAO;
//	}
//
//	/**
//	 * Session Manager
//	 * 使用的是shiro-redis开源插件
//	 */
//	@Bean
//	public DefaultWebSessionManager sessionManager() {
//		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//		sessionManager.setSessionDAO(redisSessionDAO());
//		return sessionManager;
//	}
//}
