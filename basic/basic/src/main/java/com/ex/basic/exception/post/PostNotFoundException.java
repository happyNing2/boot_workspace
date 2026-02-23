package com.ex.basic.exception.post;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(){}

    public PostNotFoundException(String msg) {
        super(msg);
    }
}
