package com.example.jwt_test.config;

import com.example.jwt_test.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthService authService;

    @Override // request : 토큰 정보 , response : 응답 ,
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        System.out.println("filter 실행 확인");
//        System.out.println("header : " +
//                request.getHeader(HttpHeaders.AUTHORIZATION));
//        String testName = "testname";

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer")){
            token = authHeader.substring(7);

            // 유효한지 판별
//            System.out.println(jwtUtil.validateToken(token));
            if (jwtUtil.validateToken(token)) {
                String uName = jwtUtil.getUsernameFromToken(token);
                if (uName != null) {
                    UserDetails userDetails = authService.loadUserByUsername(uName);
                    System.out.println("role : " + userDetails.getAuthorities());

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities() // role 설정으로 일반 유저, 관리자 role 별로 페이지 접근 여부를 제한할 수 있다.
                            );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
