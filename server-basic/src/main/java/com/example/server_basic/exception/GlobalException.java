package com.example.server_basic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ProblemDetail> userNotFound(UserNotFoundException e){
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        detail.setDetail(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detail);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ProblemDetail> duplicate(DuplicateUserException e){
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setDetail(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(detail);
    }
}
