package com.ex.basic.exception.post;

public class PostMemberAccessDeniedException extends RuntimeException{
    public PostMemberAccessDeniedException(){}

    public PostMemberAccessDeniedException(String msg) {
        super(msg);
    }
}
