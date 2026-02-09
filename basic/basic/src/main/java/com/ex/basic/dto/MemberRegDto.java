package com.ex.basic.dto;

import lombok.*;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberRegDto {
    private String username;
    private String password;
    private String role;
    private String fileName;
}
