package com.example.server_basic.service;

import com.example.server_basic.dto.MemberDto;
import com.example.server_basic.dto.MemberRegDto;
import com.example.server_basic.exception.DuplicateUserException;
import com.example.server_basic.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServerService {
    List<MemberDto> list;
    Long i = 1l;
    public ServerService(){
        list = new ArrayList<>();
        list.add(new MemberDto(i, "aaa" + i, "aaa" + i, "홍길동" + i)); i++;
        list.add(new MemberDto(i, "aaa" + i, "aaa" + i, "홍길동" + i)); i++;
        list.add(new MemberDto(i, "aaa" + i, "aaa" + i, "홍길동" + i));
    }

    public List<MemberDto> getList(){
        return list;
    }

    public MemberDto getOne(String username){
        return list.stream().filter(dto -> dto.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("USER_NOT_FOUND"));
    }

    public void register(MemberRegDto memberRegDto){
        if (memberRegDto.getUsername().equals("aaa1"))
            throw new DuplicateUserException("USER_ALREADY_EXISTS");

        MemberDto memberDto = new MemberDto(
                ++i,
                memberRegDto.getUsername(),
                memberRegDto.getPassword(),
                memberRegDto.getName()
        );
        list.add(memberDto);
    }

    public void delete(long id){
        boolean bool = list.removeIf(member -> member.getId() == id);
        if (!bool)
            throw new UserNotFoundException("USER_NOT_FOUND");
    }

    public MemberDto modify(long id, MemberDto memberDto){
        MemberDto dto = list.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("USER_NOT_FOUND"));
        dto.setPassword(memberDto.getPassword());
        dto.setName(memberDto.getName());
        return dto;
    }
}
