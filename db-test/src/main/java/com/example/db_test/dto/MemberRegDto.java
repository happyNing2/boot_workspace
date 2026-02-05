package com.example.db_test.dto;

import lombok.*;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberRegDto {
    private String userId;
    private String userName;
    private int age;
}
