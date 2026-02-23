package com.ex.basic.dto.post;

import com.ex.basic.entity.post.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostModifyDto {
    private Long number;
    private String title;
    private String content;
    public PostModifyDto(PostEntity postEntity) {
        BeanUtils.copyProperties(postEntity, this);
    }
}
