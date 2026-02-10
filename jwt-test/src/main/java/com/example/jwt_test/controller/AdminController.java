package com.example.jwt_test.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@SecurityRequirement(name = "JWT") // 이 아래 모든 값 토큰 있어야 한다. + 토믄만 있어도 접근 가능
public class AdminController {
    @GetMapping
    public String index() {
        return "admin get만 접속 가능";
    }

    @PostMapping
    public String post() {
        return "admin post만 접속 가능";
    }
}
