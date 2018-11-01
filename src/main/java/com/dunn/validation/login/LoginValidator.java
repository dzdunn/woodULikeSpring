package com.dunn.validation.login;

import com.dunn.dao.user.UserService;
import com.dunn.model.user.WoodulikeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class LoginValidator implements ConstraintValidator<LoginConstraint, WoodulikeUser> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(WoodulikeUser value, ConstraintValidatorContext context) {
        if(!userService.isUsernameRegistered(value.getUsername())){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.login.woodulikeuser.username.notfound}").addConstraintViolation();
            return false;
        }

        if(!userService.isUsernameAndPasswordValid(value.getUsername(), value.getPassword())){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.login.woodulikeuser.password.incorrect}").addConstraintViolation();
            return false;
        }

        return false;
    }
}
