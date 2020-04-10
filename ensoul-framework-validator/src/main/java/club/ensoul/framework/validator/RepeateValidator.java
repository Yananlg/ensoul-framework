package club.ensoul.framework.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Stream;

public class RepeateValidator implements ConstraintValidator<Repeate, Object[]> {
    
    @Override
    public void initialize(Repeate constraintAnnotation) {
    
    }
    
    @Override
    public boolean isValid(Object[] value, ConstraintValidatorContext context) {
        if(value == null) {
            return true;
        }
        return hasRepeate(value);
    }
    
    private <T> boolean hasRepeate(T[] arrays) {
        return arrays.length <= Stream.of(arrays).distinct().count();
    }
    
}
