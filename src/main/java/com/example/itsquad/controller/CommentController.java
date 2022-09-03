package com.example.itsquad.controller;

import com.example.itsquad.controller.request.CommentRequestDto;
import com.example.itsquad.controller.response.CommentResponseDto;
import com.example.itsquad.security.UserDetailsImpl;
import com.example.itsquad.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {


    private final CommentService commentService;

    @PostMapping("/api/quests/{questId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long questId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {


        return commentService.createComment(commentRequestDto, questId, userDetails);
    }

    @GetMapping("/api/quests/{questId}/comments")
    public ResponseEntity <?> getComments(@PathVariable Long questId) {
        return commentService.getComments(questId);
    }
}
