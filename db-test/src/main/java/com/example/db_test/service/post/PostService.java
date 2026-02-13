package com.example.db_test.service.post;

import com.example.db_test.dto.post.PostAllDto;
import com.example.db_test.dto.post.PostDetailDto;
import com.example.db_test.dto.post.PostDto;
import com.example.db_test.entity.MemberEntity;
import com.example.db_test.entity.PostEntity;
import com.example.db_test.exception.post.MemberNotFoundException;
import com.example.db_test.repository.MemberRepository;
import com.example.db_test.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    public void insert(PostDto postDto) {
        MemberEntity memberEntity = memberRepository.findById(postDto.getNumber())
                .orElseThrow( () -> new MemberNotFoundException("회원 없음"));
        PostEntity postEntity = new PostEntity();
        BeanUtils.copyProperties(postDto, postEntity);
        postEntity.setMemberEntity(memberEntity);
        postRepository.save(postEntity);
    }

    public List<PostAllDto> getPost(){
        /*
        List<PostAllDto> testList = postRepository.findAll().stream()
                .map(PostAllDto :: new).toList();
        // Hibernate : DB 처리하는 기능
        System.out.println(Hibernate.isInitialized((testList.get(0).getMemberEntity()))); // true -> memberEntity가 존재한다
        System.out.println(testList.get(0).getMemberEntity());
        System.out.println(Hibernate.isInitialized((testList.get(0).getMemberEntity()))); // true -> memberEntity가 존재한다

         */
        return postRepository.findAll().stream()
                .map(PostAllDto :: new).toList(); // member 정보까지 join돼서 나옴
    }

    public PostDetailDto getPostOne(Long id) {
        return postRepository.findById(id)
                .map(PostDetailDto::new)
                .orElseThrow(
                        () -> new MemberNotFoundException("포스트 없음") // PostNotFoundException 으로 나중에 바꿔주기 아무튼 관련 익셉션
                );
    }
}
