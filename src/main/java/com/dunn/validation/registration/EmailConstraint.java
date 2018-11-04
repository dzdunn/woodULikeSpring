package com.dunn.validation.registration;


import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = EmailValidator.class)
public @interface EmailConstraint {

    String message() default "";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
