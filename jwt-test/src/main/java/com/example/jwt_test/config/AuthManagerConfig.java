package com.example.jwt_test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.stereotype.Component;

@Component // Bean을 만들 때 사용, 서버 구동 시 내용 자동 처리
public class AuthManagerConfig {
    @Bean
    public AuthenticationManager authenticationManager( // 인증 해주는 객체
            AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager(); // 인증된 객체를 얻어와서 사용하고자 함
    }
}
