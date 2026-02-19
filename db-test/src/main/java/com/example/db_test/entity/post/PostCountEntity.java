package com.example.db_test.entity.post;

import com.example.db_test.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="post_count",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"member_id", "post_id"})}
) // member_id와 post_id를 unique하게 처리하겠다
@Getter @Setter
@NoArgsConstructor
public class PostCountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable = true,
            foreignKey = @ForeignKey(
                    foreignKeyDefinition = "FOREIGN KEY(member_id) REFERENCES member_test(number) ON DELETE SET NULL"
            )
    )
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", nullable = false,
            foreignKey = @ForeignKey(
                    foreignKeyDefinition = "FOREIGN KEY(post_id) REFERENCES post(id) ON DELETE SET CASCADE"
            )
    )
    private PostEntity postEntity;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt;

    @PrePersist
    public void onCreate(){
        this.createAt = LocalDateTime.now();
    }

    public PostCountEntity(MemberEntity memberEntity, PostEntity postEntity) {
        this.memberEntity = memberEntity;
        this.postEntity = postEntity;
    }

}
