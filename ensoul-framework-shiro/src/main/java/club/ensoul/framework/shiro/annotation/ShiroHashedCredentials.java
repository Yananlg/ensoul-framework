package club.ensoul.framework.shiro.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ShiroHashedCredentials {

    /** Hash加密类型 */
    HashAlgorithmName value() default HashAlgorithmName.SHA512;

    /** 登录失败重试次数限制 */
    int retryLimit() default 5;

    /** 重复加密次数 */
    int iterations() default 2;

    /** 是否启用hex转换 */
    boolean hex() default true;

    /** Hash加密类型枚举值 */
    enum HashAlgorithmName{
        MD2,
        MD5,
        SHA1,
        SHA256,
        SHA384,
        SHA512
    }

}
