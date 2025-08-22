package com.codewithahmed.ecommerce.common.exception;

public class UsersNotFoundException extends RuntimeException {
    public UsersNotFoundException(String message) {
        super(message);
    }
    public UsersNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public UsersNotFoundException(Throwable cause) {
        super(cause);
    }
}
