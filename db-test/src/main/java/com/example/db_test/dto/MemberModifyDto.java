package com.example.db_test.dto;

import lombok.*;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberModifyDto {
    private String userName;
    private int age;
}
