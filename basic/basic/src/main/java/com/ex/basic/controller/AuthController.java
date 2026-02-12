package com.ex.basic.controller;

import com.ex.basic.config.JwtUtil;
import com.ex.basic.dto.LoginDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    // 로그인
    @PostMapping("login")
    @Operation(
            summary = "로그인 기능",
            description = "로그인 기능"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "true")
                    )
            ),
            @ApiResponse(responseCode = "401", description = "아이디 또는 비밀번호가 올바르지 않음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "false")
                    )
            )
    })
    public ResponseEntity<Map<String, String>> login(
            @RequestBody LoginDto loginDto
            ){
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                ); // 토큰화

        // 자동 인증
        Authentication authentication = authenticationManager.authenticate(token);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        System.out.println(userDetails);
        // 인증 성공 시 토큰 발급
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        String resultToken = jwtUtil.generateToken(userDetails.getUsername(), role);

        return ResponseEntity.ok(Collections.singletonMap("token", resultToken));
    }
}
