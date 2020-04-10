package club.ensoul.framework.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= LimitStringValidator.class)
public @interface LimitString {
    
    String[] value();
    
    String message() default "限定字符串必须在{value}中";

    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
 
}