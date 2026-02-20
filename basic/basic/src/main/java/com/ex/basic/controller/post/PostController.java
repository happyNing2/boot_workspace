package com.ex.basic.controller.post;

import com.ex.basic.dto.post.PostAllDto;
import com.ex.basic.dto.post.PostDetailDto;
import com.ex.basic.dto.post.PostDto;
import com.ex.basic.service.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "게시글 작성(추가)",
            description = "게시글 작성(추가)"
    )
    public ResponseEntity<String> insert(
            @ParameterObject @ModelAttribute PostDto postDto,
            Authentication authentication
            ){
        postService.insert(postDto, authentication.getName());
        return ResponseEntity.ok("데이터 추가");
    }

    @GetMapping
    public ResponseEntity<List<PostAllDto>> getPost(){
        return ResponseEntity.ok(postService.getPost());
    }

    @GetMapping("{number}")
    public ResponseEntity<PostDetailDto> getPostOne(
            @PathVariable("number") Long number,
            @RequestParam("username") String username
    ){
        return ResponseEntity.ok(postService.getPostOne(username, number));
    }
}
