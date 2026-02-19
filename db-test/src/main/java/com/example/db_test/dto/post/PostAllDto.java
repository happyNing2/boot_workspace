package com.example.db_test.dto.post;

import com.example.db_test.entity.post.PostEntity;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostAllDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateTime;
//    private MemberEntity memberEntity; //주석 처리
    public PostAllDto(PostEntity postEntity) {
        BeanUtils.copyProperties(postEntity, this); // " memberEntity" : 지연로딩 시키겠다
        // postEntity.getMemberEntity().getUserName(); // 이 시점에 memberEntity 정보 가져옴
    }
}
