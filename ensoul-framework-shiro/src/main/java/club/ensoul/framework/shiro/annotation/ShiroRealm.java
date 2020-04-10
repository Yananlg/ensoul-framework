package club.ensoul.framework.shiro.annotation;

import club.ensoul.framework.shiro.credential.RetryLimitHashedCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ShiroRealm {

    String value();

    Class<? extends CredentialsMatcher> credentials() default RetryLimitHashedCredentialsMatcher.class;

}
