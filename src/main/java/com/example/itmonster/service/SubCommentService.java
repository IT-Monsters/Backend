package com.example.itmonster.service;


import com.example.itmonster.controller.request.SubCommentRequestDto;
import com.example.itmonster.controller.response.SubCommentResponseDto;
import com.example.itmonster.domain.Comment;
import com.example.itmonster.domain.Member;
import com.example.itmonster.domain.SubComment;
import com.example.itmonster.exceptionHandler.CustomException;
import com.example.itmonster.exceptionHandler.ErrorCode;
import com.example.itmonster.repository.CommentRepository;
import com.example.itmonster.repository.SubCommentRepository;
import com.example.itmonster.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCommentService {

    private final SubCommentRepository subCommentRepository;

    private final CommentRepository commentRepository;

    @Transactional
    public ResponseEntity<SubCommentResponseDto> createSubComment(SubCommentRequestDto subCommentRequestDto, Long commentId, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        Member member = userDetails.getMember();

        SubComment subComment = SubComment.builder()
                .content(subCommentRequestDto.getContent())
                .comment(comment)
                .member(member)
                .build();
        subCommentRepository.save(subComment);

        SubCommentResponseDto subCommentResponseDto = SubCommentResponseDto.builder()
                .commentId(subComment.getId())
                .subCommentId(subComment.getId())
                .nickname(member.getNickname())
                .content(subComment.getContent())
                .createdAt(subComment.getCreatedAt())
                .modifiedAt(subComment.getModifiedAt())
                .profileImage(member.getProfileImg())
                .build();

        return new ResponseEntity<>(subCommentResponseDto, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity getSubComments(Long subCommentId) {
        Comment comment = commentRepository.findById(subCommentId).orElseThrow(()
                -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        List<SubComment> subCommentList = subCommentRepository.findByCommentId(comment.getId());
        List<SubCommentResponseDto> subCommentResponseDtoList = new ArrayList<>();
        for (SubComment subComment : subCommentList) {
            subCommentResponseDtoList.add(SubCommentResponseDto.builder()
                    .nickname(subComment.getMember().getNickname())
                    .content(subComment.getContent())
                    .createdAt(subComment.getCreatedAt())
                    .modifiedAt(subComment.getModifiedAt())
                    .profileImage(subComment.getMember().getProfileImg())
                    .build());
        }

        return new ResponseEntity<>(subCommentResponseDtoList, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity updateSubComment(Long subCommentId, SubCommentRequestDto subCommentRequestDto) {
        SubComment subComment = subCommentRepository.findById(subCommentId).orElseThrow(()
                -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        subComment.updateSubComment(subCommentRequestDto);
        // 저장넣기
        subCommentRepository.save(subComment);
        return new ResponseEntity("수정이 완료되었습니다.", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity deleteSubComment(Long subCommentId) {
        SubComment subComment = subCommentRepository.findById(subCommentId).orElseThrow(()
                -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        subCommentRepository.deleteById(subCommentId);
        return new ResponseEntity("삭제 완료되었습니다.", HttpStatus.OK);
    }
}









/*
 public ResponseEntity <?> getSubComments(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            return new ResponseEntity<>("존재하지 않는 댓글입니다.", HttpStatus.OK);
        }

        List<SubComment> subCommentList = comment.get().getSubComments();
        List<SubCommentResponseDto> subCommentResponseDtos = new ArrayList<>();

        for (SubComment subComment : subCommentList) {
            subCommentResponseDtos.add(SubCommentResponseDto.builder()
                    .commentId(subComment.getId())
                    .subCommentId(subComment.getId())
                    .nickname(subComment.getMember().getNickname())
                    .content(subComment.getContent())
                    .createdAt(subComment.getCreatedAt())
                    .modifiedAt(subComment.getModifiedAt())
                    .profileImage(subComment.getMember().getProfileImg())
                    .build());
        }
        return new ResponseEntity<>(subCommentResponseDtos, HttpStatus.OK);
    }
    public ResponseEntity updateSubComment(SubCommentRequestDto subCommentRequestDto, Long subCommentId) {
        SubComment subComment = subCommentRepository.findById(subCommentId).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        subComment.updateSubComment(subCommentRequestDto);
        subCommentRepository.save(subComment);

        return new ResponseEntity<>("수정이 완료되었습니다.", HttpStatus.OK);
    }

*/



