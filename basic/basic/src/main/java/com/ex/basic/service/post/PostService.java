package com.ex.basic.service.post;

import com.ex.basic.dto.post.PostAllDto;
import com.ex.basic.dto.post.PostDetailDto;
import com.ex.basic.dto.post.PostDto;
import com.ex.basic.dto.post.PostModifyDto;
import com.ex.basic.entity.MemberEntity;
import com.ex.basic.entity.post.PostCountEntity;
import com.ex.basic.entity.post.PostEntity;
import com.ex.basic.exception.post.MemberNotFoundException;
import com.ex.basic.exception.post.PostMemberAccessDeniedException;
import com.ex.basic.exception.post.PostNotFoundException;
import com.ex.basic.repository.BasicMemberRepository;
import com.ex.basic.repository.post.PostCountRepository;
import com.ex.basic.repository.post.PostLikeRepository;
import com.ex.basic.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final BasicMemberRepository memberRepository;
    private final PostCountRepository postCountRepository;
    private final PostLikeRepository postLikeRepository;

    // 게시글 추가
    public void insert(PostDto postDto, String username) {
        MemberEntity memberEntity = memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException("회원 없음"));
        postDto.setNumber(memberEntity.getId());
        PostEntity postEntity = new PostEntity();
        BeanUtils.copyProperties(postDto, postEntity);
        postEntity.setNumber(null);

        postEntity.setMemberEntity(memberEntity);

        postRepository.save(postEntity);
    }

    public List<PostAllDto> getPost(Long userId) {
        return postRepository.findAll().stream()
                .map(postEntity -> {
                    long count = postCountRepository.countByPostEntity_Number(postEntity.getNumber());
                    boolean liked = false;
                    if (userId != 0)
                        liked = postLikeRepository.existsByMemberEntity_IdAndPostEntity_Number(userId, postEntity.getNumber());

                    long likedCount = postLikeRepository.countByPostEntity_Number(postEntity.getNumber());
                    return new PostAllDto(postEntity, count, liked, likedCount);
                })
                .toList(); // member 정보까지 join
    }

    public PostDetailDto getPostOne(Long id /*String username*/, Long number) {
        PostDetailDto postDetailDto = postRepository.findById(number)
                .map(PostDetailDto::new)
                .orElseThrow(
                        () -> new PostNotFoundException("포스트 없음")
                );
        increaseView(id, number);
        postDetailDto.setPostCount(postCountRepository.countByPostEntity_Number(number));
        return postDetailDto;
    }

    private void increaseView(Long id /*String username*/, Long number){ //id : member_id, number : post_id
//        if (!postCountRepository.existsByMemberEntity_UsernameAndPostEntity_Number(username, number)){
        if (!postCountRepository.existsByMemberEntity_IdAndPostEntity_Number(id, number)){
            MemberEntity memberEntity = memberRepository.getReferenceById(id);
//            MemberEntity memberEntity = memberRepository.getReferenceByUsername(username).orElse(null);
            PostEntity postEntity = postRepository.getReferenceById(number);

            PostCountEntity postCountEntity = new PostCountEntity(memberEntity, postEntity);
            postCountRepository.save(postCountEntity);
        }
    }

    public void postDelete(Long number,String username) {
        PostEntity postEntity = postRepository.findById(number)
                        .orElseThrow(() -> new PostNotFoundException());
        if (!postEntity.getMemberEntity().getUsername().equals(username))
            throw new PostMemberAccessDeniedException("삭제 권한이 없습니다");
        postEntity.getPostCounts().clear();
        postEntity.getPostLikes().clear();
        postRepository.deleteById(number);
    }

    public void postUpdate(Long number, PostModifyDto postModifyDto, String username){
        PostEntity postEntity = postRepository.findById(number)
                .orElseThrow(() -> new PostNotFoundException("게시글 없음"));
        if (!postEntity.getMemberEntity().getUsername().equals(username))
            throw new PostMemberAccessDeniedException("수정 권한이 없습니다");

        BeanUtils.copyProperties(postModifyDto, postEntity);
        postRepository.save(postEntity);
    }
}
