package com.ex.basic.service;

import com.ex.basic.dto.MemberDto;
import com.ex.basic.dto.MemberRegDto;
import com.ex.basic.dto.PageDto;
import com.ex.basic.entity.MemberEntity;
import com.ex.basic.exception.MemberAccessDeniedException;
import com.ex.basic.exception.MemberDuplicateException;
import com.ex.basic.exception.MemberNotFoundException;
import com.ex.basic.repository.BasicMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service // 연산, repository로 연결
@RequiredArgsConstructor
@Transactional
public class MemberService { // 예외처리도 함

    private final BasicMemberRepository basicMemberRepository;

    @Autowired
    private MemberFileService memberFileService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
//    public List<MemberDto> getList(int start){
    public PageDto getList(int start){
        int size = 3;
        Pageable pageable = PageRequest.of(
                start, size, Sort.by(Sort.Order.asc("id"))
        );

        Page<MemberEntity> page = basicMemberRepository.findAll(pageable);
        PageDto pageDto = new PageDto(page.getTotalPages(), page.getNumber(), page.getContent());
        System.out.println(page);
//        List<MemberDto> list = basicMemberRepository.findAll(pageable)
//                .stream() // 배열 하나씩 iterable하게 처리
//                .map(MemberDto::new)
//                .toList();
        if (pageDto.getList().isEmpty())
            throw new MemberNotFoundException("Data Not Found");
        return pageDto;
    }
    @Transactional(readOnly = true)
    public MemberDto getOne(long id, String username) {
        MemberDto memberDto = basicMemberRepository.findById(id)
                .map(dto -> new MemberDto(dto))
                .orElseThrow(() -> new MemberNotFoundException("해당 사용자 없음"));

        if (!memberDto.getUsername().equals(username))
            throw new MemberAccessDeniedException("조회할 권한이 없습니다.");

        return memberDto;
    }

    public boolean modify(long id, MemberDto modDto, MultipartFile multipartFile, String username) {
        MemberEntity memberEntity = basicMemberRepository.findById(id)
                .orElseThrow(
                        () -> new MemberNotFoundException("변경할 사용자 없음")
                );
        if (!memberEntity.getUsername().equals(username)){
            throw new MemberAccessDeniedException("삭제 권한이 없습니다.");
        }

        // 수정될 파일 이름
        if (multipartFile != null){
            String fileName = memberFileService.modifyFile(modDto.getFileName(), multipartFile);
            modDto.setFileName(fileName);
        }

        BeanUtils.copyProperties(modDto, memberEntity);
        return true;
    }

    public boolean delMember(long id, String username) {
        MemberEntity memberEntity = basicMemberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException());
        if (!memberEntity.getUsername().equals(username)){
            throw new MemberAccessDeniedException("삭제 권한이 없습니다.");
        }
        basicMemberRepository.deleteById(id);
        return true;
    }

    public void insert(MemberRegDto memberRegDto, MultipartFile multipartFile) {
        boolean result = basicMemberRepository.existsByUsername(memberRegDto.getUsername());
        if (result)
            throw new MemberDuplicateException("insert Faild : Id Already Exist");

        String fileName = memberFileService.saveFile(multipartFile);

        // password > Hash 값으로 바꾸기
//        memberRegDto.setPassword(passwordEncoder.encode(memberRegDto.getPassword()));
        MemberEntity memberEntity = new MemberEntity();

        memberRegDto.setFileName(fileName);

        BeanUtils.copyProperties(memberRegDto, memberEntity);
        basicMemberRepository.save(memberEntity);
//        return result;
    }

    // login
    /*
    public boolean login(LoginDto loginDto) {
        LoginDto result =
                basicMemberRepository.findByUsername(loginDto.getUsername())
                        .map(dto -> new LoginDto(dto))
                        .orElseThrow(() -> new InvalidLoginException("Login Failed : username이 없음"));

        if (!loginDto.getPassword().equals(result.getPassword()))
            throw new InvalidLoginException("Login Failed : password 불일치");
        return true;
    }

     */
}
