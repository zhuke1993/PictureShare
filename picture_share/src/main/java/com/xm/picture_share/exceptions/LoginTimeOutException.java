package com.xm.picture_share.exceptions;

public class LoginTimeOutException extends RuntimeException {
    public LoginTimeOutException(String message) {
        super(message);
    }
}
