package com.ex.basic.exception.post;

import com.ex.basic.exception.MemberDuplicateException;
import com.ex.basic.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // exception 발생하면 Advice 있는 곳으로 연결시켜 줌
public class GlobalPostExceptionHandler {
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ProblemDetail> notFoundHandler(
            MemberNotFoundException memberNotFoundException
    ){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("사용자 없음");
        problemDetail.setDetail(memberNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                problemDetail // front에서 변수.json()으로 받아올 수 있음
        );
    }

    @ExceptionHandler(MemberDuplicateException.class)
    public ResponseEntity<ProblemDetail> duplicateHandler(
            MemberDuplicateException memberDuplicateException
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("중복된 사용자");
        problemDetail.setDetail(memberDuplicateException.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                problemDetail // front에서 변수.json()으로 받아올 수 있음
        );
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ProblemDetail> postNotFoundHandler(
            PostNotFoundException postNotFoundException
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("포스트를 찾을 수 없습니다");
        problemDetail.setDetail(postNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                problemDetail // front에서 변수.json()으로 받아올 수 있음
        );
    }

    @ExceptionHandler(PostMemberAccessDeniedException.class)
    public ResponseEntity<ProblemDetail> postMemberAccessDeniedHandler(
            PostMemberAccessDeniedException postMemberAccessDeniedException
    ){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problemDetail.setTitle("권한이 없습니다");
        problemDetail.setDetail(postMemberAccessDeniedException.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                problemDetail
        );
    }
}

