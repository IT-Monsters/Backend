package com.example.itmonster.service;

import com.example.itmonster.controller.request.CommentRequestDto;
import com.example.itmonster.controller.response.CommentResponseDto;
import com.example.itmonster.controller.response.SubCommentResponseDto;
import com.example.itmonster.domain.Comment;
import com.example.itmonster.domain.Member;
import com.example.itmonster.domain.Quest;
import com.example.itmonster.domain.SubComment;
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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다.")); // 댓글 달 게시글 조회

        Member member = userDetails.getMember(); // 현재 로그인 된 회원정보

        Comment comment = Comment.builder() // 댓글 저정할 형태로
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
                    .subCommentId(subComment.getId())
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

        Optional<Quest> quest = questRepository.findById(questId);
        if (quest.isEmpty()) {

            return new ResponseEntity<>("존재하지 않는 게시글입니다.", HttpStatus.OK);
        }

        List<Comment> comments = quest.get().getComments();
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

    public ResponseEntity<String> updateComment(CommentRequestDto commentRequestDto, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        comment.updateComment(commentRequestDto);
        commentRepository.save(comment);

        return new ResponseEntity<>("수정이 완료되었습니다.", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);

        return new ResponseEntity<>("삭제가 완료되었습니다.", HttpStatus.OK);
    }


}
