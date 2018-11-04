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

    //For registration, email should not be registered. At other times, email may be expected to be registered.
    boolean isEmailExpectedToBeRegistered() default false;
}
