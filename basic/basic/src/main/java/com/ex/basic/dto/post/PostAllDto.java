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
    private boolean liked;

    private String username;
    private Long count; // 조회수
    private long likedCount;

    public PostAllDto(PostEntity postEntity, long count, boolean liked, long likedCount) {
        BeanUtils.copyProperties(postEntity, this);
        this.username = postEntity.getMemberEntity().getUsername();
        this.count = count;
        this.liked = liked;
        this.likedCount = likedCount;
    }
}
