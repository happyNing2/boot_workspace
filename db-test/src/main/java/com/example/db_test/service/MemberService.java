package com.example.db_test.service;

import com.example.db_test.dto.MemberAllDto;
import com.example.db_test.dto.MemberModifyDto;
import com.example.db_test.dto.MemberRegDto;
import com.example.db_test.entity.MemberEntity;
import com.example.db_test.exception.MemberDuplicateException;
import com.example.db_test.exception.MemberNotFoundException;
import com.example.db_test.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository; // 자동 생성 @RequiredArgsConstructor
    @Transactional(readOnly = true)
    public List<MemberAllDto> getList(int start) {
        int size = 3; // 한 페이지에 3명만
        Pageable pageable = PageRequest.of(
                start,
                size,
                Sort.by(Sort.Order.desc("number"))
        ); //spring framework data domain
        List<MemberAllDto> list = memberRepository.findAll(pageable)
                .stream() // 배열 iterable하게 하나씩 처리
                .map(MemberAllDto::new)
                .toList();
        if (list.isEmpty())
            throw new MemberNotFoundException("저장 데이터 없음");
        return list;
        /*
        List<MemberEntity> list = memberRepository.findAll();
//        System.out.println("service getList : " + list);

        if (list.isEmpty())
            throw new RuntimeException("저장 데이터 없음");
        List<MemberAllDto> listDto = new ArrayList<>();
        for(MemberEntity mem : list) {
            listDto.add(new MemberAllDto(mem));
        }
        return listDto;

         */
    }

    public void insert(MemberRegDto memberRegDto) {
        boolean bool = memberRepository.existsByUserId(memberRegDto.getUserId());
        if (bool)
            throw new MemberDuplicateException("존재하는 id입니다");
        MemberEntity memberEntity = new MemberEntity();

        // 모든 내용 대입 A -> B // 동일한 이름 변수 있으면 채워줌
        BeanUtils.copyProperties(memberRegDto, memberEntity);
        memberRepository.save(memberEntity);
    }

//    public MemberEntity getTestMember(long number) {
//        return memberRepository.findById(number)
//                .orElse(null);
//    }

    @Transactional(readOnly = true) // 읽어오기만 한다, 처리속도 빨라짐
    public MemberAllDto getMember(String userId) {
        return memberRepository.findByUserId(userId)
                .map( dto ->
                    new MemberAllDto(dto)
                )
                .orElseThrow(() -> new MemberNotFoundException("해당 사용자 없음"));
        /*
        return memberRepository.findByUserId(userId)
                .map( dto -> {
                    MemberAllDto memberAllDto = new MemberAllDto();
                    BeanUtils.copyProperties(dto, memberAllDto);
                    return memberAllDto;
                })
                .orElseThrow(RuntimeException::new);

         */
        /*
        MemberEntity memberEntity =  memberRepository.findByUserId(userId)
                .orElseThrow(RuntimeException::new);
        MemberAllDto memberAllDto = new MemberAllDto();
        BeanUtils.copyProperties(memberEntity, memberAllDto);
        return memberAllDto;

         */
    }

    @Transactional // 데이터 변화 감지
    public void delete(long id) {
//        if (!memberRepository.existsById(id))
//            throw new MemberNotFoundException("삭제할 사용자 없음");
        MemberEntity memberEntity = memberRepository.findById(id)
                        .orElseThrow(
                                ()->new MemberNotFoundException("삭제할 사용자가 없습니다")
                        );
//        memberEntity.getPosts().forEach(
//                post -> post.setMemberEntity(null)); // memberEntity null로 설정
        memberEntity.getPosts().clear(); // 자식도 같이 삭제됨
        memberRepository.deleteById(id);
    }

    @Transactional //org.spring, save 없이도 변경 가능
    public void modify(long id, MemberModifyDto memberModifyDto){
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(
                        () -> new MemberNotFoundException("변경할 사용자 없음")
                );
        // memberModifyDto 내용 -> memberEntity 덮어쓰기
        BeanUtils.copyProperties(memberModifyDto, memberEntity);
//        memberRepository.save(memberEntity);  // save로 변경됨
    }
}
