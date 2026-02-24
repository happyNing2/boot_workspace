package com.ex.basic.entity.post;

import com.ex.basic.entity.MemberEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="basic_post")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number; // post_id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = true)
    @JsonIgnore
    private MemberEntity memberEntity;

    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "postEntity", orphanRemoval = true,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostCountEntity> postCounts = new ArrayList<>();

    @OneToMany(mappedBy = "postEntity", orphanRemoval = true,
    cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostLikeEntity> postLikes = new ArrayList<>();

    @PrePersist
    public void onCreate(){
        this.createAt = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updateTime = LocalDateTime.now();
    }
}
