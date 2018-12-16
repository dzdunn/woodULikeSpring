package com.dunn.validation.login;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = LoginValidator.class)
public @interface LoginConstraint {

    String message() default "";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

}
