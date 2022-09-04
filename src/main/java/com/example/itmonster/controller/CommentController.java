package com.example.itmonster.controller;

import com.example.itmonster.controller.request.CommentRequestDto;
import com.example.itmonster.controller.response.CommentResponseDto;
import com.example.itmonster.security.UserDetailsImpl;
import com.example.itmonster.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {


    private final CommentService commentService;

    @PostMapping("/api/quests/{questId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long questId,
                                                            @RequestBody CommentRequestDto commentRequestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(commentRequestDto, questId, userDetails);
    }

    @GetMapping("/api/quests/{questId}/comments")
    public ResponseEntity <?> getComments(@PathVariable Long questId) {

        return commentService.getComments(questId);
    }

    @PutMapping("/api/quests/{questId}/comments/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long commentId,
                                        @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.updateComment(commentRequestDto, commentId);
    }

    @DeleteMapping("/api/quests/{questId}/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }

    
}
