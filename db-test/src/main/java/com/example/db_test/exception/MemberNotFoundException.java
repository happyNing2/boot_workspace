package com.example.db_test.exception;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(){}

    public MemberNotFoundException(String msg){
        super(msg);
    }
}
