package com.example.server_basic.controller;

import com.example.server_basic.dto.MemberDto;
import com.example.server_basic.dto.MemberRegDto;
import com.example.server_basic.service.ServerService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerController {
    private final ServerService serverService;

    @GetMapping
    public String getData(){
        return "server 데이터 전송";
    }

    @GetMapping("members")
    public List<MemberDto> getList(){
        return serverService.getList();
    }

    @GetMapping("members/{username}")
    public MemberDto getOne(@PathVariable("username") String username){
        return serverService.getOne(username);
    }

    @PostMapping("members")
    public void register(@RequestBody MemberRegDto memberRegDto){
        serverService.register(memberRegDto);
    }

    @DeleteMapping("members/{id}")
    public void delete(@PathVariable("id") long id){
        serverService.delete(id);
    }

    @PutMapping("members/{id}")
    public MemberDto modify(@PathVariable("id") long id, @RequestBody MemberDto memberDto){
        return serverService.modify(id, memberDto);
    }
}
