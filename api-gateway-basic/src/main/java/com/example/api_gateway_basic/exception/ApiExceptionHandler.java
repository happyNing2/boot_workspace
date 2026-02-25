package com.example.api_gateway_basic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(WebClientResponseException.NotFound.class)
    public ResponseEntity<ProblemDetail> userNotFound(WebClientResponseException e){
        String body = e.getResponseBodyAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(body, Map.class);

        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        if (map.get("detail").equals("USER_NOT_FOUND"))
            detail.setDetail("사용자 없음");

        if (map.get("detail").equals("POST_NOT_FOUND"))
                detail.setDetail("게시글 없음");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detail); // user에게 보여주는 내용
    }

    @ExceptionHandler(WebClientResponseException.Conflict.class)
    public ResponseEntity<ProblemDetail> conflict(WebClientResponseException e){
        String body = e.getResponseBodyAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(body, Map.class);

        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        if (map.get("detail").equals("USER_ALREADY_EXISTS"))
            detail.setDetail("동일한 사용자가 존재합니다");

        if (map.get("detail").equals("POST_ALREADY_EXISTS"))
            detail.setDetail("게시글 존재함");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(detail); // user에게 보여주는 내용
    }
}
