package club.ensoul.framework.shiro.credential;

import club.ensoul.boot.support.SpringContextUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    @Getter
    @Setter
    private int retryLimit;
    private final static String LOGIN_RETRY_LIMIT_KEY = "login:retry:limit:";

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();

        StringRedisTemplate redisTemplate = SpringContextUtils.getBean(StringRedisTemplate.class);
        ValueOperations ops = redisTemplate.opsForValue();
        Long loginRetryLimit = ops.increment(LOGIN_RETRY_LIMIT_KEY + username, 1);
        if (loginRetryLimit == null) {
            ops.set(LOGIN_RETRY_LIMIT_KEY + username, 1L);
        } else {
            ops.increment(LOGIN_RETRY_LIMIT_KEY + username);
            if (loginRetryLimit > 5) {
                throw new ExcessiveAttemptsException();
            }
        }
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            redisTemplate.delete(username);
        }
        return matches;
    }

}
