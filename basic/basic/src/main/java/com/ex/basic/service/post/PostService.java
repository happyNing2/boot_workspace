package com.ex.basic.service.post;

import com.ex.basic.dto.post.PostAllDto;
import com.ex.basic.dto.post.PostDetailDto;
import com.ex.basic.dto.post.PostDto;
import com.ex.basic.entity.MemberEntity;
import com.ex.basic.entity.post.PostCountEntity;
import com.ex.basic.entity.post.PostEntity;
import com.ex.basic.exception.post.MemberNotFoundException;
import com.ex.basic.repository.BasicMemberRepository;
import com.ex.basic.repository.post.PostCountRepository;
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

    public List<PostAllDto> getPost() {
        return postRepository.findAll().stream()
                .map(PostAllDto::new).toList(); // member 정보까지 join
    }

    public PostDetailDto getPostOne(/*Long id*/ String username, Long number) {
        PostDetailDto postDetailDto = postRepository.findById(number)
                .map(PostDetailDto::new)
                .orElseThrow(
                        () -> new MemberNotFoundException("포스트 없음")
                );
        increaseView(username, number);
        postDetailDto.setPostCount(postCountRepository.countByPostEntity_Number(number));
        return postDetailDto;
    }

    private void increaseView(/*Long id*/ String username, Long number){ //id : member_id, number : post_id
        if (!postCountRepository.existsByMemberEntity_UsernameAndPostEntity_Number(username, number)){

            MemberEntity memberEntity = memberRepository.getReferenceByUsername(username).orElse(null);
            PostEntity postEntity = postRepository.getReferenceById(number);

            PostCountEntity postCountEntity = new PostCountEntity(memberEntity, postEntity);
            postCountRepository.save(postCountEntity);
        }
    }
}
