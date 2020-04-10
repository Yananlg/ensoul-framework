package club.ensoul.framework.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MonthValidator implements ConstraintValidator<Month, Integer> {
    
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext validatorContext) {
        if(value == null) {
            return true;
        }
        return value >= 0 && value <= 12;
    }
    
}

    
