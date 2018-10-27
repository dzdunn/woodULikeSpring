package com.dunn.validation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = WoodulikeUserValidator.class)
public @interface WoodulikeUserConstraint {
    String message() default "Not a valid woodulike user.";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
