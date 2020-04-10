package club.ensoul.framework.core.copier.annotation;

import org.springframework.cglib.core.Converter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Repeatable(EntityCopiers.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityCopier {
    
    /**
     * 支持转换的类型
     */
    Class<?> value();
    
    /**
     * 当前bean要使用的类型转换器
     */
    Class<? extends Converter> converter();

}
