package com.dunn.model.genericmodelwrappers;

import com.dunn.validation.login.IForgotPasswordEmailValidationGroup;
import com.dunn.validation.registration.EmailConstraint;

public class GenericEmailWrapper {

    @EmailConstraint(groups= IForgotPasswordEmailValidationGroup.class, isEmailExpectedToBeRegistered = true)
    private String emailAddress;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

}
