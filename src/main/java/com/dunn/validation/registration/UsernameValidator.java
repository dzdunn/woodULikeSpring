package com.dunn.validation.registration;


import com.dunn.dao.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {

    private final static int MIN_USERNAME_LENGTH = 6;
    private final static int MAX_USERNAME_LENGTH = 20;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(!value.matches("[a-zA-Z0-9]*")){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Username contains invalid characters").addConstraintViolation();
            return false;
        }

        if(value.length() < MIN_USERNAME_LENGTH  || value.length() > MAX_USERNAME_LENGTH){
            context.disableDefaultConstraintViolation();
            String lengthValidationMessage = messageSource.getMessage("validation.woodulikeuser.username.length", new Object[]{MIN_USERNAME_LENGTH, MAX_USERNAME_LENGTH}, LocaleContextHolder.getLocale());
            context.buildConstraintViolationWithTemplate(lengthValidationMessage).addConstraintViolation();
            return false;
        }

        if(userService.isUsernameRegistered(value)){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.woodulikeuser.username.notUnique}").addConstraintViolation();
            return false;
        }
        return true;
    }

}
