package com.example.react.dto;

import lombok.*;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDto {
    private int id;
    private String username;
    private String password;
    private String role;
}
