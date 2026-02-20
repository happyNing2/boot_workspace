package com.ex.basic.dto.post;

import com.ex.basic.entity.post.PostEntity;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostAllDto {
    private Long number;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateTime;

    public PostAllDto(PostEntity postEntity) {
        BeanUtils.copyProperties(postEntity, this);
    }
}
