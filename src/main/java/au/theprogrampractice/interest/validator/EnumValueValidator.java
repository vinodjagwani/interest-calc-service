package au.theprogrampractice.interest.validator;

import au.theprogrampractice.interest.validator.annotation.Enum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;


public class EnumValueValidator implements ConstraintValidator<Enum, String> {

    private Enum annotation;

    @Override
    public void initialize(final Enum annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext context) {
        if (Objects.isNull(object)) {
            return true;
        }
        boolean result = false;
        final Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        if (Objects.nonNull(enumValues)) {
            for (Object enumValue : enumValues) {
                final String enumValueAsString = enumValue.toString();
                if (object.equals(enumValueAsString) || (this.annotation.ignoreCase() && object.equalsIgnoreCase(enumValueAsString))) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
