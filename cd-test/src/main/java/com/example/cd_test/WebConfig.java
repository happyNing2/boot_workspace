package com.example.cd_test;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 요청에 대해 CORS 설정
        registry.addMapping("/**") // 모든 엔드포인트에 대해
                .allowedOriginPatterns("*") //http://localhost:3000
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 허용할 HTTP 메소드
                .allowedHeaders("*")  // 모든 헤더 허용
                .allowCredentials(true);  // 쿠키 전달 허용
    }
}
