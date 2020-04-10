package club.ensoul.framework.validator;

import club.ensoul.framework.validator.extend.ValidatorEnum;

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
@Constraint(validatedBy ={EnumValidator.class})
public @interface Enum {
    
    Class<? extends ValidatorEnum> value() ;
    
    String message() default "不是有效的值";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
