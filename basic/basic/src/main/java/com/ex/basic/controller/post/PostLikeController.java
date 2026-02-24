package com.ex.basic.controller.post;

import com.ex.basic.config.security.CustomUserDetails;
import com.ex.basic.service.post.PostLikeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostLikeController {
    private final PostLikeService postLikeService;

    @PostMapping("/post/{postId}/like")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> likePost(
            @PathVariable("postId") Long postId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
            ){
        postLikeService.likePost(postId, customUserDetails.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/post/{postId}/like")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> unlikePost(
            @PathVariable("postId") Long postId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        postLikeService.unlikePost(postId, customUserDetails.getId());
        return ResponseEntity.ok().build();
    }

}
