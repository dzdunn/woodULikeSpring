package com.dunn.validation.registration;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = WoodulikePasswordValidator.class)
public @interface WoodulikePasswordValid {

    String message() default "";
    Class<?>[] groups() default{};
    Class<?>[] payload() default {};
}
