package com.ex.basic.exception.post;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(){}

    public MemberNotFoundException(String msg) {
        super(msg);
    }
}
