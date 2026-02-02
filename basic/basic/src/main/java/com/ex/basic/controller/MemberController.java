package com.ex.basic.controller;

import com.ex.basic.dto.MemberDto;
import com.ex.basic.exception.MemberDuplicateException;
import com.ex.basic.exception.MemberNotFoundException;
import com.ex.basic.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/members")
public class MemberController {
    private MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping()
    public ResponseEntity<List<MemberDto>> getList(){
        List<MemberDto> list = null;
        try {
            list = memberService.getList();
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(list);
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getInfoId(
            @PathVariable("id") int id
    ){
        MemberDto mem = null;
        try {
            mem = memberService.getOne(id);
        }
        catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mem);
        }
        return ResponseEntity.ok(mem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> modifyMemById(
            @PathVariable("id") int id,
            @ModelAttribute MemberDto memberDto //form
    ) {
        boolean mod_suc = false;
        try {
            mod_suc = memberService.modify(id, memberDto);
        }
        catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemById(
            @PathVariable("id") int id
    ){
        boolean bool = false;
        try {
            bool = memberService.delMember(id);
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PostMapping
    public ResponseEntity<Void> addMember(
            @ModelAttribute MemberDto memberDto // form
    ){
        boolean bool = false;
        try {
            bool = memberService.insert(memberDto);
        } catch(MemberDuplicateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
