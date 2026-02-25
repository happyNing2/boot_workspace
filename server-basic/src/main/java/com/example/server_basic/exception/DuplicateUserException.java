package com.example.server_basic.exception;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(){}
    public DuplicateUserException(String msg){
        super(msg);
    }
}
