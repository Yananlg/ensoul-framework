package club.ensoul.framework.core.copier.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreCopier {

    /**
     * 属性copy时，是否忽略该属性，默认为忽略
     */
    boolean value() default true;

}
