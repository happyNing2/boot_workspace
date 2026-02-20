package com.ex.basic.repository.post;

import com.ex.basic.entity.post.PostCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCountRepository extends JpaRepository<PostCountEntity, Long> {
//    boolean existsByMemberEntity_IdAndPostEntity_Number(Long memberId, Long postId);
    boolean existsByMemberEntity_UsernameAndPostEntity_Number(String username, Long postId);
    long countByPostEntity_Number(Long postId);
}
