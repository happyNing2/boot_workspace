package com.ex.basic.controller.post;

import com.ex.basic.config.security.CustomUserDetails;
import com.ex.basic.dto.post.PostAllDto;
import com.ex.basic.dto.post.PostDetailDto;
import com.ex.basic.dto.post.PostDto;
import com.ex.basic.dto.post.PostModifyDto;
import com.ex.basic.service.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        /*
        Map<String, Object> map = new HashMap<>();
        map.put("status", "SUCCESS");
        map.put("message", "데이터 추가 성공");

        return ResponseEntity.ok(map);
         */
        return ResponseEntity.ok("데이터 추가");
    }

    @GetMapping
    @SecurityRequirement(name = "JWT") // permit all도 받고, auth도 받을 수 있음
    public ResponseEntity<List<PostAllDto>> getPost(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        long userId = 0;
        if (customUserDetails != null)
            userId = customUserDetails.getId();
        return ResponseEntity.ok(postService.getPost(userId));
    }

    @GetMapping("{number}")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<PostDetailDto> getPostOne(
            @PathVariable("number") Long number,
            Authentication authentication
//            @RequestParam("username") String username
    ){
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        return ResponseEntity.ok(postService.getPostOne(userDetails.getId(), number));

//        return ResponseEntity.ok(postService.getPostOne(username, number));
    }

    @DeleteMapping("{number}")
    @SecurityRequirement(name="JWT")
    public ResponseEntity<Void> postDelete(
            @PathVariable("number") Long number,
            Authentication authentication
    ){
        postService.postDelete(number, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @PutMapping("{number}")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> postUpdate(
            @PathVariable("number") Long number,
            @ParameterObject @ModelAttribute PostModifyDto postModifyDto,
            Authentication authentication
            ){
        postService.postUpdate(number, postModifyDto, authentication.getName());
        return ResponseEntity.ok().build();
    }
}
