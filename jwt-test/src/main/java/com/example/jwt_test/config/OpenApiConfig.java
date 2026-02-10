package com.example.jwt_test.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openApi(){
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer"); // jwt token 사용할 때 bearer 관례적으로 붙음

        return new OpenAPI()
                .info(new Info()
                        .title("jwt-test OpenAPI")
                        .description("jwt 실습")
                        .version("v1.0.0")
                )
                .servers(
                        List.of(
                                new Server().url("http://localhost:10000")
                                        .description("개발용 서버 주소")
                        )
                )
                .components( // addSecuritySchems(key, ?) : 사용자가 지정한(접근하는) 이름
                        new Components().addSecuritySchemes("JWT", securityScheme)
                );
    }
}
