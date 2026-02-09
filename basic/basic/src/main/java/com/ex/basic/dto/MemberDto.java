package com.ex.basic.dto;

import com.ex.basic.entity.MemberEntity;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDto {
    private long id;
    private String username;
    private String password;
    private String role;

    private String fileName;

    public MemberDto(MemberEntity memberEntity) {
        BeanUtils.copyProperties(memberEntity, this);
    }
}
