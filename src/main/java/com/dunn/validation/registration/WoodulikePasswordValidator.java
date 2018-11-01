package com.dunn.validation.registration;

import com.dunn.model.user.WoodulikeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class WoodulikePasswordValidator implements ConstraintValidator<WoodulikePasswordValid, WoodulikeUser> {

    @Autowired
    private MessageSource messageSource;

    private static final int MIN_PASSWORD_LENGTH = 8;

    private static final int MAX_PASSWORD_LENGTH = 16;

    private static final String atLeastOneLetterPattern = "(?=.*[A-Za-z])";

    private static final String atLeastOneDigitPattern = "(?=.*\\d)";

    private static final String atLeastOneSpecialSymbolPattern = "(?=.*[^A-Za-z0-9])";

    private static final String noWhitespacePattern = "(?=[\\S]+$)";

    private static final String passwordLengthPattern = "{" + MIN_PASSWORD_LENGTH + "," + MAX_PASSWORD_LENGTH + "}";

    private String passwordValidationPatternBuilder(){
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        builder.append(atLeastOneLetterPattern);
        builder.append(atLeastOneDigitPattern);
        builder.append(atLeastOneSpecialSymbolPattern);
        builder.append(noWhitespacePattern);
        builder.append(".");
        builder.append(passwordLengthPattern);
        builder.append(")");
        return builder.toString();
    }

    @Override
    public boolean isValid(WoodulikeUser value, ConstraintValidatorContext context) {

        if(!value.getPassword().equals(value.getConfirmPassword())){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.woodulikeuser.password.match}").addConstraintViolation();
            return false;
        }

        if(value.getPassword().length() < MIN_PASSWORD_LENGTH || value.getPassword().length() > MAX_PASSWORD_LENGTH){
            context.disableDefaultConstraintViolation();

            String lengthValidationMessage = messageSource.getMessage("validation.woodulikeuser.password.length", new Object[]{MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH}, LocaleContextHolder.getLocale());
            context.buildConstraintViolationWithTemplate(lengthValidationMessage).addConstraintViolation();
            return false;
        }

        if(!value.getPassword().matches(passwordValidationPatternBuilder())){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.woodulikeuser.password.mustinclude}").addConstraintViolation();
            return false;
        }

        return true;
    }
}
