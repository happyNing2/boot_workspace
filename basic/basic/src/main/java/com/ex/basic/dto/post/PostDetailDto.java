package com.ex.basic.dto.post;

import com.ex.basic.entity.post.PostEntity;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDetailDto {
    private Long number;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateTime;

    private String memberUserRole;
    private String memberUserName;

    private Long postCount;

    public PostDetailDto(PostEntity postEntity) {
        BeanUtils.copyProperties(postEntity, this);
        this.memberUserName = postEntity.getMemberEntity().getUsername();
        this.memberUserRole = postEntity.getMemberEntity().getRole();
    }
}
