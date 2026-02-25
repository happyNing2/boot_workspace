package com.example.server_basic.dto;

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

