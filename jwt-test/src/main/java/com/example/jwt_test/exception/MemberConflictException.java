package com.example.jwt_test.exception;

public class MemberConflictException extends RuntimeException{
    public MemberConflictException(){}
    public MemberConflictException(String msg) {
        super(msg);
    }
}
