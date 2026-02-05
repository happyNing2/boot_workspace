package com.example.db_test.exception;

public class MemberDuplicateException extends RuntimeException {
    public MemberDuplicateException(){}
    public MemberDuplicateException(String msg) {
        super(msg);
    }
}
