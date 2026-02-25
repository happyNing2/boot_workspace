package com.example.server_basic.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(){}

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
