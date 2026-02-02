package com.ex.basic.exception;

public class MemberDuplicateException extends RuntimeException{
    public MemberDuplicateException(String msg) {
        super(msg);
    }
}
