package com.ex.basic.repository.post;

import com.ex.basic.entity.post.PostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {
//    boolean existsByMemberEntity_IdAndPostEntity_Number(Long memberId, Long postId);
    boolean existsByMemberEntity_UsernameAndPostEntity_Number(String username, Long postId);
    boolean existsByMemberEntity_IdAndPostEntity_Number(Long id, Long postId);
    long countByPostEntity_Number(Long postId);
    void deleteByMemberEntity_IdAndPostEntity_Number(Long id, Long postId);
}
