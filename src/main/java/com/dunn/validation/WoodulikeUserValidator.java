package com.dunn.validation;

import com.dunn.model.user.WoodulikeUser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WoodulikeUserValidator implements ConstraintValidator<WoodulikeUserConstraint, WoodulikeUser> {

    @Override
    public boolean isValid(WoodulikeUser value, ConstraintValidatorContext context) {

        boolean isValid = false;

        if(!value.getPassword().equals(value.getConfirmPassword())){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Passwords must match").addConstraintViolation();
            return false;
        }

        if(value.getPassword().matches())
        return true;
    }
}
