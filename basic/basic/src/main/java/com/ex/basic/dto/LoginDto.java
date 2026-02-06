package com.ex.basic.dto;

import com.ex.basic.entity.MemberEntity;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginDto {
    private String username;
    private String password;

    public LoginDto(MemberEntity memberEntity) {
        BeanUtils.copyProperties(memberEntity, this);
    }
}
