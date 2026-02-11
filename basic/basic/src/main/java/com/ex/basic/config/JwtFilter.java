package com.ex.basic.config;

import com.ex.basic.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;
        String username = null;

        String uri = request.getRequestURI();

        // ✅ 로그인 / swagger / 회원가입 등은 JWT 검사 제외
        if (uri.startsWith("/auth/login") ||
                uri.startsWith("/swagger") ||
                uri.startsWith("/v3/api-docs")) {

            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);

            System.out.println("Header = " + authHeader);
            System.out.println("Token = [" + token + "]");

            // 유효 확인
            if (jwtUtil.validateToken(token)) {
                String uName= jwtUtil.getUsernameFromToken(token);

                if (uName != null){
                    UserDetails userDetails = authService.loadUserByUsername(uName);
                    System.out.println("role : " + userDetails.getAuthorities());

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities()
                            );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
