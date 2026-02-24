package com.ex.basic.service.post;

import com.ex.basic.entity.MemberEntity;
import com.ex.basic.entity.post.PostEntity;
import com.ex.basic.entity.post.PostLikeEntity;
import com.ex.basic.repository.BasicMemberRepository;
import com.ex.basic.repository.post.PostLikeRepository;
import com.ex.basic.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final BasicMemberRepository memberRepository;
    private final PostRepository postRepository;

    public void likePost(Long postId, Long userId) {
        if (postLikeRepository.existsByMemberEntity_IdAndPostEntity_Number(userId, postId)){
            throw new RuntimeException("이미 좋아요를 선택한 사용자");
        }

        MemberEntity memberEntity = memberRepository.getReferenceById(userId);
        PostEntity postEntity = postRepository.getReferenceById(postId);
        PostLikeEntity postLikeEntity = new PostLikeEntity(memberEntity,postEntity);
        postLikeRepository.save(postLikeEntity);
    }

    public void unlikePost(Long postId, Long userId) {
        if (!postLikeRepository.existsByMemberEntity_IdAndPostEntity_Number(userId, postId)){
            throw new RuntimeException("좋아요를 선택하지 않은 사용자");
        }

        postLikeRepository.deleteByMemberEntity_IdAndPostEntity_Number(userId, postId);
    }
}
