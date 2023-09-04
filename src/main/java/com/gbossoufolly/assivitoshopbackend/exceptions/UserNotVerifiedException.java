package com.gbossoufolly.assivitoshopbackend.exceptions;

public class UserNotVerifiedException extends Exception{

    private boolean newEmailSent;

    public UserNotVerifiedException(boolean newEmailSent) {
        this.newEmailSent = newEmailSent;
    }

    public UserNotVerifiedException(String message, boolean newEmailSent) {
        super(message);
        this.newEmailSent = newEmailSent;
    }

    public boolean isNewEmailSent() {
        return newEmailSent;
    }
}
