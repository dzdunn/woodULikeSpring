package com.dunn.validation;

import com.dunn.model.user.WoodulikeUser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class WoodulikeUserValidator implements ConstraintValidator<WoodulikeUserConstraint, WoodulikeUser> {

    //private static final String passwordPattern = "((?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z0-9])(?=[\\S]+$).{8,16})";

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
            context.buildConstraintViolationWithTemplate("Passwords must match").addConstraintViolation();
            return false;
        }
        if(value.getPassword().length() < 8 || value.getPassword().length() > 16){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password must be between 8 - 16 characters in length.").addConstraintViolation();
            return false;
        }
        if(!value.getPassword().matches(passwordValidationPatternBuilder())){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password must contain no spaces, and at least 1 letter, number and special symbol.").addConstraintViolation();
            return false;
        }
        return true;
    }
}
