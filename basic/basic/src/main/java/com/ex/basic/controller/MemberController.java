package com.ex.basic.controller;

import com.ex.basic.dto.LoginDto;
import com.ex.basic.dto.MemberDto;
import com.ex.basic.dto.MemberRegDto;
import com.ex.basic.dto.PageDto;
import com.ex.basic.exception.InvalidLoginException;
import com.ex.basic.exception.MemberDuplicateException;
import com.ex.basic.exception.MemberNotFoundException;
import com.ex.basic.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "MemberAPI", description = "회원 도메인 API")
@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping()
    @Operation(
            summary = "전체 회원 조회",
            description = "전체 회원 조회"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "전체 회원 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = MemberDto.class)
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "전체 회원 조회 실패",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "[]")
                    )
            )
    })
//    public ResponseEntity<List<MemberDto>> getList(
    public ResponseEntity<PageDto> getList(
            @RequestParam(name="start", defaultValue = "0") int start
    ){

//        List<MemberDto> list = null;
//        try {
//        list = memberService.getList(start);
//        } catch (MemberNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(list);
//        }
        return ResponseEntity.ok(memberService.getList(start));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "특정 회원 조회",
            description = "특정 회원 조회"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "특정 회원 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = """
                                    [
                                        {
                                            "id" : 1,
                                            "username" : "aaa",
                                            "password" : "111",
                                            "role" : "USER"
                                        }
                                    ]
                                    """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "특정 회원 조회 실패 : 없는 id",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "없는 id입니다")
                    )
            )
    })
    public ResponseEntity<MemberDto> getInfoId(
            @PathVariable("id") int id
    ){
        MemberDto mem = null;
//        try {
        mem = memberService.getOne(id);
//        }
//        catch (MemberNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mem);
//        }
        return ResponseEntity.ok(mem);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "회원 수정",
            description = "회원 수정"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "회원 삭제 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "true")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "회원 삭제 실패",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "false")
                    )
            )
    })
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
    @Operation(
            summary = "회원 삭제",
            description = "회원 삭제"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "회원 삭제 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "true")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "회원 삭제 실패",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "false")
                    )
            )
    })
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
    @Operation(
            summary = "회원 추가",
            description = "회원 추가"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 가입 성공",
                    content = @Content(
                            mediaType = "application/json", // 폼
                            schema = @Schema(implementation = String.class, example = """
                                    [
                                        {
                                            "id" : 1,
                                            "username" : "aaa",
                                            "password" : "111",
                                            "role" : "USER"
                                        }
                                    ]
                                    """)
                    )
            ),
            @ApiResponse(responseCode = "409", description = "회원 가입 실패 : 중복 id",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "중복된 id입니다")
                    )
            )
    })
    public ResponseEntity<Void> addMember(
            @ModelAttribute MemberRegDto memberRegDto // form
    ){
//        boolean bool = false;
//        try {
        memberService.insert(memberRegDto);
//        } catch(MemberDuplicateException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 로그인
    @PostMapping("login")
    @Operation(
            summary = "로그인 기능",
            description = "로그인 기능"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "true")
                    )
            ),
            @ApiResponse(responseCode = "401", description = "아이디 또는 비밀번호가 올바르지 않음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "false")
                    )
            )
    })
    public ResponseEntity<Boolean> login(
            @RequestBody LoginDto loginDto
    ){
        boolean isLogin = false;
//        try {
        isLogin = memberService.login(loginDto);
//        } catch (InvalidLoginException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(isLogin);
//        }
        return ResponseEntity.ok(isLogin);

    }
}
