package club.ensoul.framework.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy ={RepeateValidator.class})
public @interface Repeate {

    String message() default "不能包含重复数据";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
