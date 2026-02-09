package com.ex.basic.repository;

import com.ex.basic.dto.LoginDto;
import com.ex.basic.dto.MemberDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository // database 처리
public class MemberRepository {
    private List<MemberDto> DB;
    public MemberRepository() {
        DB = new ArrayList<>();
        DB.add(new MemberDto(1, "aaa", "aaa", "USER", "nan"));
        DB.add(new MemberDto(2, "bbb", "bbb", "USER", "nan"));
        DB.add(new MemberDto(3, "ccc", "ccc", "USER", "nan"));
    }

    public List<MemberDto> findAll() {
        return DB;
    }

    public MemberDto findById(long id) {
        MemberDto memDto = DB.stream()
                .filter(mem -> mem.getId() == id)
                .findFirst()
                .orElse(null);
        return memDto;
    }

    public boolean save(int id, MemberDto modDto) {
        boolean bool = DB.stream()
                .filter(mem -> mem.getId() == id)
                .findFirst()
                .map(mem -> {
                    mem.setUsername(modDto.getUsername());
                    mem.setPassword(modDto.getPassword());
                    mem.setRole(modDto.getRole());
                    return true;
                })
                .orElse(false);

        return bool;
    }

    public boolean deleteById(int id) {
        boolean bool = DB.removeIf(
                mem -> mem.getId() == id
        );
        return bool;
    }

    public boolean save(MemberDto memberDto) {
        boolean bool = false;
        MemberDto existMem = findById(memberDto.getId());
        if (existMem == null) {
            bool = DB.add(memberDto);
            return bool;
        }
        return bool;
    }

    //login
    public boolean login(LoginDto loginDto) {
        boolean bool = false;
        MemberDto existMem = DB.stream()
                .filter(mem -> mem.getUsername().equals(loginDto.getUsername()))
                .findFirst()
                .orElse(null);

        if (existMem != null) {
            if (existMem.getPassword().equals(loginDto.getPassword())){
                bool = true;
            }
        }
        return bool;
    }
}
