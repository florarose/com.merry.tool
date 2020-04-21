package com.shiro.demo.shiro;

import com.shiro.demo.filter.JwtAuthFilter;
import com.shiro.demo.filter.AnyRolesAuthorizationFilter;
import com.shiro.demo.filter.ShiroPermissionsFilter;
import com.shiro.demo.handler.MyExceptionHandler;
import com.shiro.demo.service.impl.UserServiceImpl;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.HandlerExceptionResolver;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;



/**
 * shiro配置类
 */
/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/21 14:48
 */
@Configuration
public class ShiroConfig {

    private static final String CACHE_KEY = "shiro:cache:";
    private static final String SESSION_KEY = "shiro:session:";
    private static final String NAME = "custom.name";
    private static final String VALUE = "/";

    //todo
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, UserServiceImpl userService) throws Exception {

        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        Map<String, Filter> filterMap = new LinkedHashMap<>(3);
        shiroFilter.setLoginUrl("/api/user/login");
       filterMap.put("authc", createAuthFilter());
//        filterMap.put("anon", createRolesFilter());
//        filterMap.put("admin", createAdminJwtAuthFilter(userService));
        shiroFilter.setFilters(filterMap);
        /*定义shiro过滤链  Map结构
         * Map中key(xml中是指value值)的第一个'/'代表的路径是相对于HttpServletRequest.getContextPath()的值来的
         * anon：它对应的过滤器里面是空的,什么都没做,这里.do和.jsp后面的*表示参数,比方说login.jsp?main这种
         * authc：该过滤器下的页面必须验证后才能访问,它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
         */
        //     shiroFilter.setFilterChainDefinitionMap(userService.loadFilterChainDefinitions());
        shiroFilter.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());


        return shiroFilter;
    }

    /**
     * Redis集群使用RedisClusterManager，单个Redis使用RedisManager
     *
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(8);
        poolConfig.setMaxIdle(8);
        poolConfig.setMinIdle(1);
        // 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：
        // Could not get a resource from the pool
        poolConfig.setMaxWaitMillis(10000);
        //创建redis连接池，把配置对象给她
        JedisPool jedisPool = new JedisPool(poolConfig, "47.111.160.211", 6366, 3600, "123456");
        RedisManager redisManager = new RedisManager();
        redisManager.setJedisPool(jedisPool);
        redisManager.setDatabase(2);
        return redisManager;
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setKeyPrefix(CACHE_KEY);
        redisCacheManager.setRedisManager(redisManager);
        return redisCacheManager;
    }

    @Bean
    public RedisSessionDAO redisSessionDAO(RedisManager redisManager) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setKeyPrefix(SESSION_KEY);
        redisSessionDAO.setRedisManager(redisManager);
        return redisSessionDAO;
    }

    @Bean("securityManager")
    public SessionsSecurityManager securityManager(RedisCacheManager redisCacheManager) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setCacheManager(redisCacheManager);
        manager.setRealms(Arrays.asList(jwtShiroRealm(redisCacheManager)));
        return manager;
    }

    /**
     * 初始化Authenticator
     */
    @Bean
    public Authenticator authenticator(RedisCacheManager redisCacheManager) {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        //设置两个Realm，一个用于用户登录验证和访问权限获取；一个用于jwt token的认证
        authenticator.setRealms(Arrays.asList(jwtShiroRealm(redisCacheManager)));
        //设置多个realm认证策略，一个成功即跳过其它的
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return authenticator;
    }


    @Bean("defaultAdvisorAutoProxyCreator")
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        //指定强制使用cglib为action创建代理对象
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 生命周期
     *
     * @return
     */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        LifecycleBeanPostProcessor lifecycleBeanPostProcessor = new LifecycleBeanPostProcessor();
        return new LifecycleBeanPostProcessor();
    }

    @Bean("delegatingFilterProxy")
    public FilterRegistrationBean delegatingFilterProxy() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    /**
     * 用于JWT token认证的realm
     */
    @Bean("jwtRealm")
    public Realm jwtShiroRealm(RedisCacheManager redisCacheManager) {
        JWTShiroRealm myShiroRealm = new JWTShiroRealm();
        myShiroRealm.setCacheManager(redisCacheManager);
        myShiroRealm.setName("222222222");
        myShiroRealm.setAuthorizationCachingEnabled(false);
        myShiroRealm.setAuthorizationCachingEnabled(false);
        return myShiroRealm;
    }

    @Bean
    protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        chainDefinition.addPathDefinition("/swagger-ui.html", "anon");
        chainDefinition.addPathDefinition("/swagger-resources/**", "anon");
        chainDefinition.addPathDefinition("/v2/api-docs", "anon");
        chainDefinition.addPathDefinition("/webjars/springfox-swagger-ui/**", "anon");
        chainDefinition.addPathDefinition("/static/**", "anon");

        //加上shior后需要添加
        chainDefinition.addPathDefinition("/configuration/security", "anon");
        chainDefinition.addPathDefinition("/configuration/ui", "anon");
        chainDefinition.addPathDefinition("/docs/**", "anon");
        chainDefinition.addPathDefinition("/images/**", "anon");


        chainDefinition.addPathDefinition("/statics/**", "anon");
        //login不做认证，noSessionCreation的作用是用户在操作session时会抛异常
        chainDefinition.addPathDefinition("/api/user/login", "anon");
        chainDefinition.addPathDefinition("/api/user/updatePassword", "authc");
        chainDefinition.addPathDefinition("/api/user/info", "noSessionCreation,authc");

        chainDefinition.addPathDefinition("/websocket", "anon");

        return chainDefinition;
    }
//    ????

    /**
     * 禁用session, 不保存用户登录状态。保证每次请求都重新认证。
     * 需要注意的是，如果用户代码里调用Subject.getSession()还是可以用session，如果要完全禁用，要配合下面的noSessionCreation的Filter来实现
     */
    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;

    }

    //todo
    protected JwtAuthFilter createAuthFilter() {
        return new JwtAuthFilter();
    }


    protected AnyRolesAuthorizationFilter createRolesFilter() {
        return new AnyRolesAuthorizationFilter();
    }

    protected ShiroPermissionsFilter createPermission() {
        return new ShiroPermissionsFilter();
    }
//

    /**
     * 注册全局异常处理
     *
     * @return
     */
    @Bean(name = "exceptionHandler")
    public HandlerExceptionResolver handlerExceptionResolver() {

        return new MyExceptionHandler();
    }

    /**
     * 使用session
     */


}
