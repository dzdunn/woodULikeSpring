package com.dunn.validation.registration;

import com.dunn.dao.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {

    @Autowired
    private UserService userService;

    boolean isEmailExpectedToBeRegistered;

    private final static String emailRegex =
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";


    @Override
    public void initialize(EmailConstraint constraint) {
        isEmailExpectedToBeRegistered = constraint.isEmailExpectedToBeRegistered();
    }


    @Override
    @Transactional
    public boolean isValid(String value, ConstraintValidatorContext context) {


        //Check empty or null
        if (value == null || value.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.woodulikeuser.emailAddress.notEmpty}").addConstraintViolation();
            return false;
        }

        //Check is valid email
        if (!value.matches(emailRegex)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.woodulikeUser.emailAddress.invalid}").addConstraintViolation();
            return false;
        }

        //check if email is Registered
        boolean isEmailRegistered = userService.isEmailRegistered(value);

        if (isEmailRegistered && !isEmailExpectedToBeRegistered) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.woodulikeuser.emailAddress.notUnique}").addConstraintViolation();
            return false;
        }

        if (!isEmailRegistered && isEmailExpectedToBeRegistered) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.woodulikeuser.emailAddress.notRegistered}").addConstraintViolation();
            return false;
        }


        return true;
    }
}
