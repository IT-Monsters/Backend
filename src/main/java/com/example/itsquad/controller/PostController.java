package com.example.itsquad.controller;

import com.example.itsquad.controller.request.PostRequestDto;
import com.example.itsquad.controller.response.PostResponseDto;
import com.example.itsquad.security.UserDetailsImpl;
import com.example.itsquad.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("")
    public void createPost(@RequestBody PostRequestDto postRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.createPost(postRequestDto, userDetails);
    }

    @GetMapping("")
    public List<PostResponseDto> readAllPost(){
        return postService.readAllPost();
    }

    @GetMapping("/{postId}")
    public PostResponseDto readPost(@PathVariable Long postId){
        return postService.readPost(postId);
    }

    @PutMapping("/{postId}")
    public void updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails){
        postService.updatePost(postId, postRequestDto, userDetails);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        postService.deletePost(postId, userDetails);
    }
}
