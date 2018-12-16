package com.dunn.model.genericmodelwrappers;

import com.dunn.validation.registration.EmailConstraint;
import com.dunn.validation.resetpassword.IForgotPasswordEmailValidationGroup;

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
