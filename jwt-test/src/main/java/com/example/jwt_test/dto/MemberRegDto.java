package com.example.jwt_test.dto;

import lombok.*;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberRegDto {
    private String username;
    private String password;
}
