package com.example.jwt_test.config;

import com.example.jwt_test.exception.JwtAccestDeniedHandler;
import com.example.jwt_test.exception.JwtAuthEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;
    @Autowired
    private JwtAccestDeniedHandler jwtAccestDeniedHandler;

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/members/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/members").permitAll()
                        .requestMatchers(HttpMethod.POST, "/members").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated() // 삭제 수정 등... 제일 마지막에 써야함
                )// 지금 접속한 사용자는 해당 경로 사용 허가해주겠다
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // UsernamePassword~ 실행 전에 필터 동작시키겠다는 뜻
                .exceptionHandling( e -> e
                        .authenticationEntryPoint(jwtAuthEntryPoint) // 토큰 없거나 만료된 사용자
                        .accessDeniedHandler(jwtAccestDeniedHandler) // 다른 권한일 때
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){ // 비밀번호 해시값으로 변경
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
}
