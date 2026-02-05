package com.example.db_test.controller;

import com.example.db_test.dto.MemberAllDto;
import com.example.db_test.dto.MemberModifyDto;
import com.example.db_test.dto.MemberRegDto;
import com.example.db_test.entity.MemberEntity;
import com.example.db_test.exception.MemberNotFoundException;
import com.example.db_test.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor // Lombok, 생성자 이용해서 추가하는 방식
public class MemberController {
    // @Autowired
    // public MemberController(MemberService memberService) {}
    private final MemberService memberService;

    @GetMapping("/members")
    public ResponseEntity<List<MemberAllDto>> getList(
            @RequestParam(name="start", defaultValue="0") int start // start 지정 안 되면 0으로
    ) {
//        try {
            return ResponseEntity.ok(memberService.getList(start));
//        } catch(MemberNotFoundException e){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
    }

    @PostMapping("/members")
    public ResponseEntity<Void> insert(
            @ParameterObject @ModelAttribute MemberRegDto memberRegDto
            // ^form data
    ) {
//        System.out.println("ctrl memberEntity : " + memberEntity);
//        try {
            memberService.insert(memberRegDto);
//        } catch(RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
        return ResponseEntity.ok().build();
    }

    //    @GetMapping("/member/test/{number}")
//    public ResponseEntity<MemberEntity> getTestMember(
//            @PathVariable("number") long number
//    ){
//        MemberEntity memberEntity = memberService.getTestMember(number);
//        return ResponseEntity.ok(memberEntity);
//    }

    @GetMapping("/members/{userId}")
    public ResponseEntity<MemberAllDto> getMember(
            @PathVariable("userId") String userId
    ){
//        try {
            MemberAllDto memberAllDto = memberService.getMember(userId);
            return ResponseEntity.ok(memberAllDto);
//        } catch (MemberNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") long id
    ) {
//        try {
            memberService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
//        } catch(MemberNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<Void> modify(
            @PathVariable("id") long id,
            @ParameterObject @ModelAttribute MemberModifyDto memberModifyDto
            ) {
//        try {
            memberService.modify(id, memberModifyDto);
            return ResponseEntity.ok().build();
//        } catch(MemberNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
    }
}
