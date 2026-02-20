package com.ex.basic.dto.post;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDto {
    private Long number; // member id?
    private String title;
    private String content;
}
