package club.ensoul.framework.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class LimitStringValidator implements ConstraintValidator<LimitString, String> {
    
    private LimitString limitString;
    
    @Override
    public void initialize(LimitString limitString) {
        this.limitString = limitString;
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext validatorContext) {
        String[] values = limitString.value();
        for(String val : values) {
            if(value.equals(val)) {
                return true;
            }
        }
        return false;
    }
}

    
