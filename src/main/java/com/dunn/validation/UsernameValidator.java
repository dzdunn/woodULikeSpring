package com.dunn.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {

    private final static int MIN_USERNAME_LENGTH = 6;
    private final static int MAX_USERNAME_LENGTH = 20;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(!value.matches("[a-zA-Z0-9]*")){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Username contains invalid characters").addConstraintViolation();
            return false;
        }

        if(value.length() < MIN_USERNAME_LENGTH  || value.length() > MAX_USERNAME_LENGTH){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Username must be between " + MIN_USERNAME_LENGTH + " and " + MAX_USERNAME_LENGTH + " characters").addConstraintViolation();
            return false;
        }

        return true;
    }

}
