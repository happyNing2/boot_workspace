package com.ex.basic.dto;

import com.ex.basic.entity.MemberEntity;
import lombok.*;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberRegDto {
    private String username;
    private String password;
    private String role;
}
