package com.example.db_test.dto.post;

import lombok.*;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDto {
    private Long number;
    private String title;
    private String content;

}
