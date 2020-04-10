package club.ensoul.framework.core.copier.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;

@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FormatCopier {

    /**
     * 属性copy时，是否忽略该属性，默认为忽略
     */
    boolean value() default true;
    
    Class<? extends Format> format();
    
    /**
     * 格式化字符串，日期类型使用{@link SimpleDateFormat}
     * 数值使用{@link DecimalFormat}
     */
    String pattern() default "";

}
