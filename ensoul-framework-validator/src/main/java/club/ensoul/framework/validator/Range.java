package club.ensoul.framework.validator;

import club.ensoul.framework.validator.RangeValidator.RangeArrayValidator;
import club.ensoul.framework.validator.RangeValidator.RangeSingleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {RangeArrayValidator.class, RangeSingleValidator.class})
public @interface Range {
    
    int[] value();
    
    String message() default "限定数值必须在{value}中";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
}