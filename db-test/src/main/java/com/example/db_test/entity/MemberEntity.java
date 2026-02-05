package com.example.db_test.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="member_test") // table 이름을 다음과 같이 지정하겠다.
public class MemberEntity { //primary key 필수
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long number;

    @Column(unique = true, name="user_id", nullable = false, length = 30)
    private String userId;

    private String userName;
    private int age;
}
