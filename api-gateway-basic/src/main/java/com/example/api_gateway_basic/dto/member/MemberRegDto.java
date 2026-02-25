package com.example.api_gateway_basic.dto.member;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberRegDto {
//    private Long id;
    private String username;
    private String password;
    private String name;
}
