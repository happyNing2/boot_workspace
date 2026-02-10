package com.example.jwt_test.exception;

public class MemberAccessDeniedException extends RuntimeException{
    public MemberAccessDeniedException(){}

    public MemberAccessDeniedException(String msg) {
        super(msg);
    }
}
