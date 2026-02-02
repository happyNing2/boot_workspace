package com.ex.basic.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(){}
    public MemberNotFoundException(String msg) {
        super(msg);
    }
}
