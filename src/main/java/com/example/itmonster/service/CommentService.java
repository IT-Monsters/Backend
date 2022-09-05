package com.example.itmonster.service;

import com.example.itmonster.controller.request.CommentRequestDto;
import com.example.itmonster.controller.response.CommentResponseDto;
import com.example.itmonster.controller.response.SubCommentResponseDto;
import com.example.itmonster.domain.Comment;
import com.example.itmonster.domain.Member;
import com.example.itmonster.domain.Quest;
import com.example.itmonster.domain.SubComment;
import com.example.itmonster.exceptionHandler.CustomException;
import com.example.itmonster.exceptionHandler.ErrorCode;
import com.example.itmonster.repository.CommentRepository;
import com.example.itmonster.repository.QuestRepository;
import com.example.itmonster.repository.SubCommentRepository;
import com.example.itmonster.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final QuestRepository questRepository;

    private final SubCommentRepository subCommentRepository;

    @Transactional
    public ResponseEntity<CommentResponseDto> createComment(CommentRequestDto commentRequestDto, Long questId, UserDetailsImpl userDetails) {
        Quest quest = questRepository.findById(questId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        Member member = userDetails.getMember();

        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .quest(quest)
                .member(member)
                .build();
        commentRepository.save(comment);

        CommentResponseDto commentResponseDto = CommentResponseDto.builder()
                .commentId(comment.getId())
                .nickname(member.getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .profileImage(member.getProfileImg())
                .build();

        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }

    private List<SubCommentResponseDto> subCommentResponseDtoList(Comment comment){
        List<SubComment> subCommentList = subCommentRepository.findByCommentId(comment.getId());
        List<SubCommentResponseDto> subCommentResponseDtoList = new ArrayList<>();
        for (SubComment subComment:subCommentList){
            subCommentResponseDtoList.add(SubCommentResponseDto.builder()
                    .content(subComment.getContent())
                    .nickname(subComment.getMember().getNickname())
                    .profileImage(subComment.getMember().getProfileImg())
                    .createdAt(subComment.getCreatedAt())
                    .modifiedAt(subComment.getModifiedAt())
                    .build());
        }
        return subCommentResponseDtoList;
    }

    public ResponseEntity<?> getComments(Long questId) {

        Quest quest = questRepository.findById(questId).orElseThrow(()
                -> new CustomException(ErrorCode.QUEST_NOT_FOUND));

        List<Comment> comments = quest.getComments();
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();

        for (Comment comment : comments) {
            commentResponseDtos.add(CommentResponseDto.builder()
                    .commentId(comment.getId())
                    .nickname(comment.getMember().getNickname())
                    .content(comment.getContent())
                    .createdAt(comment.getCreatedAt())
                    .modifiedAt(comment.getModifiedAt())
                    .profileImage(comment.getMember().getProfileImg())
                    .subCommentList(subCommentResponseDtoList(comment))
                    .build());
        }
        return new ResponseEntity<>(commentResponseDtos, HttpStatus.OK);
    }

    public ResponseEntity updateComment(CommentRequestDto commentRequestDto, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        comment.updateComment(commentRequestDto);
        commentRepository.save(comment);

        return new ResponseEntity("수정이 완료되었습니다.", HttpStatus.OK);
    }

    public ResponseEntity deleteComment(Long commentId) {
        commentRepository.findById(commentId).orElseThrow(()
        -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        commentRepository.deleteById(commentId);

        return new ResponseEntity("삭제가 완료되었습니다.", HttpStatus.OK);
    }
}
