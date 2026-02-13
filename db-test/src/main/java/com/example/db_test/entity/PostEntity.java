package com.example.db_test.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="post")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // FetchType.LAZY : 지연로딩 ( 연결된 Entity 값 사용할 때 그 때 로딩하겠다는 뜻)
    @JoinColumn(name = "number", nullable = true)
    private MemberEntity memberEntity; // memberEntity의 number를 연결하겠다

    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateTime;
    @PrePersist
    public void onCreate(){
        this.createAt = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
    @PreUpdate
    public void onUpdate(){
        this.updateTime = LocalDateTime.now();
    }
}
