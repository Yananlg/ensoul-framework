package club.ensoul.framework.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class RangeValidator {
    
    public class RangeArrayValidator implements ConstraintValidator<Range, Integer[]> {
        
        private Range range;
        
        @Override
        public void initialize(Range range) {
            this.range = range;
        }
        
        @Override
        public boolean isValid(Integer[] _values, ConstraintValidatorContext validatorContext) {
            if(_values == null || _values.length == 0) {
                return true;
            }
            int[] values = range.value();
            for(int val : values) {
                for(Integer _val : _values) {
                    if(!_val.equals(val)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
    
    public class RangeSingleValidator implements ConstraintValidator<Range, Integer> {
        
        private Range range;
        
        @Override
        public void initialize(Range range) {
            this.range = range;
        }
        
        @Override
        public boolean isValid(Integer value, ConstraintValidatorContext validatorContext) {
            if(value == null) {
                return true;
            }
            int[] values = range.value();
            for(int val : values) {
                if(value.equals(val)) {
                    return true;
                }
            }
            return false;
        }
    }
    
}

    
