package com.dunn.validation.registration;

import com.dunn.dao.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailUniqueValidator implements ConstraintValidator<EmailUniqueConstraint, String> {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(userService.isEmailRegistered(value)){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.woodulikeuser.emailaddress.notUnique}").addConstraintViolation();
            return false;
        }
        return true;
    }
}
