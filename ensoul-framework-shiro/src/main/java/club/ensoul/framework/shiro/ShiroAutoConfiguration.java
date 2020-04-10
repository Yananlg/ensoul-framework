package club.ensoul.framework.shiro;

import club.ensoul.framework.shiro.authz.ShiroModularRealmAuthenticator;
import club.ensoul.framework.shiro.authz.ShiroModularRealmAuthorizer;
import club.ensoul.framework.shiro.config.ShiroBeanDefinitionRegistry;
import club.ensoul.framework.shiro.config.ShiroGenericConverter;
import club.ensoul.framework.shiro.config.ShiroProperties;
import club.ensoul.framework.shiro.session.ShiroRedisSessionDAO;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;

/**
 * Shiro 配置类
 *
 * @author wy_peng_chen6
 */
@Configuration
@EnableConfigurationProperties(ShiroProperties.class)
@Import({ShiroGenericConverter.class, ShiroBeanDefinitionRegistry.class})
public class ShiroAutoConfiguration {
    
    /**
     * shiro bean生命周期管理
     *
     * @return {@link LifecycleBeanPostProcessor}
     */
    @Bean
    @ConditionalOnMissingBean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    
    /**
     * 开启注解控制权限的方式，AOP式方法级权限检查
     *
     * @return {@link DefaultAdvisorAutoProxyCreator}
     */
    @Bean
    @ConditionalOnMissingBean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 是否适用代理
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }
    
    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager SecurityManager
     */
    @Bean
    @ConditionalOnMissingBean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    
    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean delegatingFilterProxy() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }
    
    @Bean("shiroFilter")
    @ConditionalOnMissingBean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, ShiroProperties shiroProperties) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl(shiroProperties.getLoginUrl());
        shiroFilterFactoryBean.setSuccessUrl(shiroProperties.getSuccessUrl());
        shiroFilterFactoryBean.setUnauthorizedUrl(shiroProperties.getUnauthorizedUrl());
        shiroFilterFactoryBean.setFilters(shiroProperties.getFilters());
        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroProperties.getFilterChains());
        return shiroFilterFactoryBean;
    }
    
    @Bean
    @ConditionalOnMissingBean
    public CacheManager cacheManager(EhCacheManagerFactoryBean ehCacheManagerFactoryBean) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(ehCacheManagerFactoryBean.getObject());
        return ehCacheManager;
    }
    
    @Bean
    public SessionDAO sessionDAO(ShiroProperties shiroProperties, CacheManager cacheManager, RedisTemplate<String, Session> redisTemplate) {
        SessionDAO sessionDAO = null;
        if("ehCache".equals(shiroProperties.getCacheManager().getCache())) {
            CachingSessionDAO cachingSessionDAO = new EnterpriseCacheSessionDAO();
            cachingSessionDAO.setCacheManager(cacheManager);
            sessionDAO = cachingSessionDAO;
        } else if("memcache".equals(shiroProperties.getCacheManager().getCache())) {
            // TODO:
        } else {
            sessionDAO = new ShiroRedisSessionDAO(redisTemplate);
        }
        return sessionDAO;
    }
    
    /**
     * session管理器
     */
    @Bean
    @ConditionalOnMissingBean(WebSessionManager.class)
    public WebSessionManager webSessionManager(SessionDAO sessionDAO, ShiroProperties shiroProperties) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        ShiroProperties.SessionManager propSession = shiroProperties.getSessionManager();
        sessionManager.setGlobalSessionTimeout(propSession.getSessionTimeout().toMillis());
        sessionManager.setSessionValidationInterval(propSession.getSessionValidationInterval().toMillis());
        sessionManager.setDeleteInvalidSessions(propSession.isDeleteInvalidSessions());
        sessionManager.setSessionIdCookieEnabled(propSession.isSessionIdCookieEnabled());
        sessionManager.setSessionIdUrlRewritingEnabled(propSession.isSessionIdUrlRewritingEnabled());
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionListeners(shiroProperties.getSessionListeners());
        return sessionManager;
    }
    
    /**
     * 记住我的配置
     *
     * @return {@link RememberMeManager}
     */
    @Bean
    @ConditionalOnMissingBean
    public RememberMeManager rememberMeManager(ShiroProperties shiroProperties) {
        Cookie cookie = new SimpleCookie(shiroProperties.getRememberMe().getCookieName());
        cookie.setHttpOnly(shiroProperties.getRememberMe().isHttpOnly());
        cookie.setMaxAge((int) shiroProperties.getRememberMe().getMaxAge().toMinutes());
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(cookie);
        return cookieRememberMeManager;
    }
    
    /**
     * 多Realm认证管理
     */
    @Bean
    @ConditionalOnMissingBean
    public ModularRealmAuthenticator modularRealmAuthenticator(ShiroProperties shiroProperties) {
        ShiroModularRealmAuthenticator modularRealmAuthenticator = new ShiroModularRealmAuthenticator(shiroProperties.applyRealmMap());
        modularRealmAuthenticator.setAuthenticationStrategy(shiroProperties.getAuthenticationStrategy());
        return modularRealmAuthenticator;
    }
    
    /**
     * 多Realm鉴权管理
     */
    @Bean
    @ConditionalOnMissingBean
    public ModularRealmAuthorizer modularRealmAuthorizer() {
        return new ShiroModularRealmAuthorizer();
    }
    
    // /**
    //  * 启用shiro方言，这样能在页面上使用shiro标签
    //  *
    //  * @return {@link ShiroDialect}
    //  */
    // @Bean
    // @ConditionalOnMissingBean
    // public ShiroDialect shiroDialect() {
    //     return new ShiroDialect();
    // }
    
    /**
     * subject工厂管理器.
     */
    @Bean
    @ConditionalOnMissingBean
    public SubjectFactory subjectFactory() {
        return new DefaultWebSubjectFactory();
    }
    
    /**
     * shiro安全管理器:AllSuccessfulStrategy
     * 主要是身份认证的管理，缓存管理，cookie管理，
     * 所以在实际开发中我们主要是和SecurityManager进行打交道的
     *
     * @return {@link DefaultWebSecurityManager}
     */
    @Bean
    @ConditionalOnMissingBean
    public SecurityManager securityManager(ModularRealmAuthenticator modularRealmAuthenticator,
                                           RememberMeManager rememberMeManager,
                                           SessionManager sessionManager,
                                           SubjectFactory subjectFactory,
                                           ShiroProperties shiroProperties) {
        
        DefaultSecurityManager securityManager;
        if(shiroProperties.getWeb().isEnabled()) {
            securityManager = new DefaultWebSecurityManager();
        } else {
            securityManager = new DefaultSecurityManager();
        }
        securityManager.setSubjectFactory(subjectFactory);
        securityManager.setSessionManager(sessionManager);
        securityManager.setRememberMeManager(rememberMeManager);
        securityManager.setAuthenticator(modularRealmAuthenticator);
        securityManager.setRealms(shiroProperties.applyRealms());
        return securityManager;
        
    }
    
}
