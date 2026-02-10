package com.example.jwt_test.dto;

import com.example.jwt_test.entity.MemberEntity;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDto {
    private Long id;
    private String username;
    private String password;
    private String role;
    public MemberDto(MemberEntity memberEntity) {
        BeanUtils.copyProperties(memberEntity, this);
    }
}
