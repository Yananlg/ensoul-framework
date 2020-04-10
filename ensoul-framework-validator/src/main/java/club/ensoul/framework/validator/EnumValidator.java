package club.ensoul.framework.validator;

import club.ensoul.framework.validator.extend.ValidatorEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<Enum, Object> {
    
    private Enum validatorEnum;
    
    @Override
    public void initialize(Enum validatorEnum) {
        this.validatorEnum = validatorEnum;
    }
    
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value == null) {
            return true;
        }
        ValidatorEnum[] enumConstants = validatorEnum.value().getEnumConstants();
        for(ValidatorEnum enumConstant : enumConstants) {
            if(enumConstant.hasValue(value)){
                return true;
            }
        }
        return false;
    }
}
