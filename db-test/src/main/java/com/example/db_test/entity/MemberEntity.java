package com.example.db_test.entity;

import com.example.db_test.entity.post.PostEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true) // 한 명은 여러 개의 글을 작성할 수 있다.
    // orphanRemoval = true : 자식도 같이 삭제 됨
    // casecade = CasecadeType.ALL : 자식도 같이 삭제 됨,
    private List<PostEntity> posts = new ArrayList<>();

}
