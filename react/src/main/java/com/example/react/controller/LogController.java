package com.example.react.controller;

import com.example.react.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j // 로그 도와주는 어노테이션
@RequestMapping("/log")
public class LogController {
    @Autowired(required = false) // 해당하는 객체 있으면 객체로, 없으면 null로
    private MemberDto memberDto;

    @GetMapping("print")
    public ResponseEntity<Void> logPrint(){
        System.out.println("dto : " + memberDto);
        log.debug("debug message");
        log.info("debug message");
        log.warn("debug message");
        log.error("debug message");
        return ResponseEntity.ok().build();
    }
}
