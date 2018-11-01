package com.dunn.validation.registration;


import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = EmailUniqueValidator.class)
public @interface EmailUniqueConstraint {

    String message() default "";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
