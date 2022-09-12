package com.example.itmonster.controller;


import com.example.itmonster.controller.request.SubCommentRequestDto;
import com.example.itmonster.controller.response.SubCommentResponseDto;
import com.example.itmonster.security.UserDetailsImpl;
import com.example.itmonster.service.SubCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SubCommentController {

    public final SubCommentService subCommentService;

    @PostMapping("/api/quests/{questId}/comments/{commentId}/subComments")
    public ResponseEntity<SubCommentResponseDto> createSubComment(@PathVariable Long commentId, @RequestBody SubCommentRequestDto subCommentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return subCommentService.createSubComment(subCommentRequestDto, commentId, userDetails);
    }

    @GetMapping("/api/quests/{questId}/comments/{commentId}/subComments")
    public ResponseEntity <?> getSubComments(@PathVariable("commentId") Long subCommentId) {
        return subCommentService.getSubComments(subCommentId);
    }

    @PutMapping("/api/quests/{questId}/comments/{commentId}/subComments/{subCommentId}")
    public ResponseEntity <?> updateSubComment(@PathVariable Long subCommentId, @RequestBody SubCommentRequestDto subCommentRequestDto) {
        return subCommentService.updateSubComment(subCommentId, subCommentRequestDto);
    }

    @DeleteMapping("/api/quests/{questId}/comments/{commentId}/subComments/{subCommentId}")
    public ResponseEntity <?> deleteSubComment(@PathVariable Long subCommentId) {
        return subCommentService.deleteSubComment(subCommentId);
    }
}