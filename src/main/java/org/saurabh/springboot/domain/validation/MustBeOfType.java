package org.saurabh.springboot.domain.validation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {MustBeOfType.GenderValidator.class})
public @interface MustBeOfType {

    Class<? extends Enum> value();

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class GenderValidator implements ConstraintValidator<MustBeOfType, String> {

        private Class<? extends Enum> enumClass;

        @Override
        public void initialize(MustBeOfType constraintAnnotation) {
            enumClass = constraintAnnotation.value();
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            try {
                if (value == null) {
                    return true;
                }
                Enum.valueOf(enumClass, value);
                return true;
            } catch (IllegalArgumentException ex) {
                return false;
            }
        }
    }
}
