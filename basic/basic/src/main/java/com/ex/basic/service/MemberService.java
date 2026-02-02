package com.ex.basic.service;

import com.ex.basic.dto.MemberDto;
import com.ex.basic.exception.MemberDuplicateException;
import com.ex.basic.exception.MemberNotFoundException;
import com.ex.basic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 연산, repository로 연결
public class MemberService { // 예외처리도 함
    @Autowired
    private MemberRepository memberRepository;

    public MemberService() {
        System.out.println("MemberService 생성자");
    }

    public List<MemberDto> getList(){
        List<MemberDto> list = memberRepository.findAll();
        if (list.isEmpty())
            throw new MemberNotFoundException("Data Not Found");
        return list;
    }

    public MemberDto getOne(int id) {
        MemberDto result = memberRepository.findById(id);
        if (result == null)
            throw new MemberNotFoundException("getOne Failed : Id Not Found");
        return result;
    }

    public boolean modify(int id, MemberDto modDto) {
        boolean result = memberRepository.save(id, modDto);
        if (!result)
            throw new MemberNotFoundException("modify Failed : Id Not Found");
        return result;
    }

    public boolean delMember(int id) {
        boolean result = memberRepository.deleteById(id);
        if (!result)
            throw new MemberNotFoundException("delete Failed : Id Not Found");
        return result;
    }

    public boolean insert(MemberDto memberDto) {
        boolean result = memberRepository.save(memberDto);
        if (!result)
            throw new MemberDuplicateException("insert Faild : Id Already Exist");
        return result;
    }
}
