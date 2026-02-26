package com.example.cd_test;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CdController {
    @GetMapping("/")
    public ResponseEntity<String> index(HttpServletRequest req) {
        return ResponseEntity.ok(req.getLocalAddr());
    }
}
