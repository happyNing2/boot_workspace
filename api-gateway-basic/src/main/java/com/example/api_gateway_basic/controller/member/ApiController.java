package com.example.api_gateway_basic.controller.member;

import com.example.api_gateway_basic.dto.member.MemberAllDto;
import com.example.api_gateway_basic.dto.member.MemberDto;
import com.example.api_gateway_basic.dto.member.MemberRegDto;
import com.example.api_gateway_basic.service.member.ApiService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ApiController {
    private final ApiService apiService;

    @GetMapping("basic")
    public ResponseEntity<String> basic(){
        String serverURL = "http://localhost:20000/server";
        WebClient webClient = WebClient.create(); // react의 fetch인데, 서버끼리 통신함

        String response = webClient.get().uri(serverURL)
                .retrieve()
                .bodyToMono(String.class) // 서버 return값을 받아와줌, 어떤 타입으로 받을지
                .block(); // Mono(여러 개 데이터)나 Flux(하나의 데이터) 방식(비동기)을 동기 방식으로 받겠다(블럭 처리 하겠다)
        return ResponseEntity.ok("받아온 데이터 : " + response);
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberDto>> getList(){
        return ResponseEntity.ok(apiService.getList());
    }

    @GetMapping("/members/{username}")
    public ResponseEntity<MemberDto> getOne(@PathVariable("username") String username){
        return ResponseEntity.ok(apiService.getOne(username));
    }

    @PostMapping("/members")
    public ResponseEntity<String> register(@ParameterObject @ModelAttribute MemberRegDto memberRegDto){
        apiService.register(memberRegDto);
        return ResponseEntity.ok("가입 성공");
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        apiService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<MemberAllDto> modify(@PathVariable("id") Long id,
                                       @ParameterObject @ModelAttribute MemberAllDto memberAllDto){
        return ResponseEntity.ok(apiService.modify(id, memberAllDto));
    }
}
