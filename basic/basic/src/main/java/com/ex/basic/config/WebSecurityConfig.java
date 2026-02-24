package com.ex.basic.config;

import com.ex.basic.exception.JwtAccessDeniedHandler;
import com.ex.basic.exception.JwtAuthEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filter(HttpSecurity httpSecurity, JwtAuthEntryPoint jwtAuthEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler) throws Exception {
        httpSecurity
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/members").permitAll() // 목록 보기
                                .requestMatchers(HttpMethod.POST, "/members").permitAll() // 회원가입
                                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/members/image/**").permitAll() // 이미지
                                .requestMatchers(HttpMethod.GET, "/post").permitAll() // 게시글 조회
                                .requestMatchers(HttpMethod.GET, "/post/*").permitAll() // 특정 회원 글 조회
                                .requestMatchers(HttpMethod.POST, "/post/{postId}/like").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/post/{postId}/like").permitAll()
                                .anyRequest().authenticated()

                )
                .addFilterBefore(jwtFilter,     UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(jwtAuthEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                );
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
}
