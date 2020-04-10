package club.ensoul.framework.shiro.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.SessionListener;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;

import javax.servlet.Filter;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Validated
@NoArgsConstructor
@ConfigurationProperties(prefix = "shiro")
public class ShiroProperties  {


    /** 启用shiro自动配置 */
    private boolean enabled = false;
    private Web web = new Web();

    /** 登录地址 */
    private String loginUrl = "login";
    /** 登录成功后跳转地址 */
    private String successUrl = "index";
    /** 未授权跳转地址 */
    private String unauthorizedUrl = "403";
    /** 退出登陆后跳转地址 */
    private String logoutUrl = "index";
    /** shiro 缓存时长，默认值 1800 秒 */
    private Duration cacheTimeout = Duration.ofSeconds(1800);
    /** 登录重试次数限制，默认为5 */
    private int loginRetryLimit = 5;

    /** 缓存设置 */
    private CacheManager cacheManager = new CacheManager();
    /** session参数设置 */
    private SessionManager sessionManager = new SessionManager();
    /** jwt参数设置 */
    private JwtManager jwtManager = new JwtManager();
    /** 记住我参数设置 */
    private RememberMe rememberMe = new RememberMe();
    /** Realm参数设置 */
    //@NotNull
    //private RealmManager realmManager = new RealmManager();

    /**
     * 多Realms验证方式
     * AtLeastOneSuccessfulStrategy	只要一个或者多个Realm认证通过，则整体身份认证就会视为成功。
     * FirstSuccessfulStrategy	只有第一个验证通过，才会视为整体认证通过。其他的会被忽略。
     * AllSuccessfulStrategy	只有所有的Realm认证成功，才会被视为认证通过。
     */
    private AuthenticationStrategy authenticationStrategy = new AtLeastOneSuccessfulStrategy();

    /** Session监听集合，监听实现接口{@link SessionListener} */
    private List<SessionListener> sessionListeners = new ArrayList<>();

    /** Shiro过滤链 */
    private Map<String, String> filterChains = new LinkedHashMap<>();

    /** Shiro 过滤器 {@link Filter} */
    private Map<String, Filter> filters = new LinkedHashMap<>();

    public Map<String, List<Realm>> applyRealmMap() {
        return ShiroBeanDefinitionRegistry.realmBeanMap;
    }

    public List<Realm> applyRealms() {
        return ShiroBeanDefinitionRegistry.realmBeanMap.values()
                .stream().flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /** Web支持设置 */
    @Data
    @NoArgsConstructor
    public static class Web {
        /** 启用Web环境自动配置 */
        private boolean enabled = true;
        /** 认证会话维护方式 */
        private String authentication = "session";
    }


    /** CacheManager设置 */
    @Data
    @NoArgsConstructor
    public static class CacheManager {
        /** session 缓存类型 */
        private String cache = "redis";
    }

    /** SessionManager设置 */
    @Data
    @NoArgsConstructor
    public static class SessionManager {
        /** session 超时时间，默认30分钟 */
        private Duration sessionTimeout = Duration.ofMinutes(30);
        /** session 验证时间，默认30分钟 */
        private Duration sessionValidationInterval = Duration.ofMinutes(30);
        /** 删除失效的session */
        private boolean deleteInvalidSessions = true;
        /** 启用cookie session */
        private boolean sessionIdCookieEnabled = true;
        /** url重写 */
        private boolean sessionIdUrlRewritingEnabled = true;
    }

    /** Jwt token设置 */
    @Data
    @NoArgsConstructor
    public static class JwtManager {

    }

    /** RememberMe设置 */
    @Data
    @NoArgsConstructor
    public static class RememberMe {
        /** RememberMe 有效时长，默认为 30 天 */
        private Duration maxAge = Duration.ofDays(30);
        /** Cookie名称 */
        private String cookieName = "rememberMe";
        /** 通过js脚本将无法读取到cookie信息 */
        private boolean httpOnly = true;
    }

//    /** RealmManager */
//    @Data
//    @Validated
//    @NoArgsConstructor
//    public static class RealmManager {
//
//        /** 默认鉴权加密方式 */
//        private CredentialsDefinition credentials;
//
//        /** Realms集合 **/
//        @NotEmpty
//        private Map<String, List<RealmDefinition>> realms = new LinkedHashMap<>();
//
//        public Map<String, List<RealmDefinition>> getRealms() {
//            for (List<RealmDefinition> realmDefinitions : realms.values()) {
//                for (RealmDefinition realmDefinition : realmDefinitions) {
//                    if(realmDefinition.getCredentials() == null) {
//                        realmDefinition.setCredentials(credentials);
//                    }
//                }
//            }
//            return realms;
//        }
//
//    }
//
//    /** RealmDefinition */
//    @Data
//    @Validated
//    @NoArgsConstructor
//    public static class RealmDefinition {
//
//        /** Realm 名称 */
//        private String name;
//        /** Realm Class */
//        @NotNull
//        private Realm target;
//        /** authentication 缓存名称 */
//        private String authenticationCacheName;
//        /** 是否开启 authentication 缓存 */
//        private Boolean authenticationCachingEnabled = true;
//        /** authentication Token Class Type */
//        private Class<? extends AuthenticationToken> authenticationTokenClass;
//        /** 当前Realm鉴权加密方式，不配置则使用默认 */
//        private CredentialsDefinition credentials;
//
//        public void setTarget(Realm realm) {
//            if (realm instanceof AuthenticatingRealm) {
//                AuthenticatingRealm authenticatingRealm = (AuthenticatingRealm) realm;
//                if (name != null) {
//                    authenticatingRealm.setName(name);
//                }
//                if (authenticationCacheName != null) {
//                    authenticatingRealm.setAuthenticationCacheName(authenticationCacheName);
//                }
//                authenticatingRealm.setAuthenticationCachingEnabled(authenticationCachingEnabled);
//                if (authenticationTokenClass != null) {
//                    authenticatingRealm.setAuthenticationTokenClass(authenticationTokenClass);
//                }
//                this.target = authenticatingRealm;
//            } else {
//                this.target = realm;
//            }
//        }
//
//    }
//
//    /** CredentialsDefinition */
//    @Data
//    @Validated
//    @NoArgsConstructor
//    public static class CredentialsDefinition {
//
//        /** Realm加密认证Class */
//        @NotNull
//        private CredentialsMatcher target;
//        /** 加密类型 */
//        private String algorithmName = "MD5";
//        /** 加密次数 */
//        private int iterations = 2;
//        /** 是否使用Hex字符串 */
//        private boolean hex = true;
//
//        public void setCredentialsTarget(CredentialsMatcher credentialsMatcher) {
//            if (credentialsMatcher instanceof HashedCredentialsMatcher) {
//                HashedCredentialsMatcher hashedCredentialsMatcher = (HashedCredentialsMatcher) target;
//                if (algorithmName == null || "".equals(algorithmName.trim())) {
//                    hashedCredentialsMatcher.setHashAlgorithmName(algorithmName);
//                } else {
//                    hashedCredentialsMatcher.setHashAlgorithmName("MD5");
//                }
//                if (iterations > 0) {
//                    hashedCredentialsMatcher.setHashIterations(iterations);
//                } else {
//                    hashedCredentialsMatcher.setHashIterations(2);
//                }
//
//                hashedCredentialsMatcher.setStoredCredentialsHexEncoded(hex);
//                this.target = hashedCredentialsMatcher;
//            } else {
//                this.target = credentialsMatcher;
//            }
//        }
//
//    }

}
