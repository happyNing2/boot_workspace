package com.example.jwt_test.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, // 사용자 정보
                         HttpServletResponse response, // 응답
                         AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // header 설정
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write("{\"error\": \"토큰이 없거나 인증되지 않았습니다.\"}"); // 내용 전달
    }
}
