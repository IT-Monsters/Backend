package com.example.itsquad.service;

import com.example.itsquad.controller.request.CommentRequestDto;
import com.example.itsquad.controller.response.CommentResponseDto;
import com.example.itsquad.domain.Comment;
import com.example.itsquad.domain.Member;
import com.example.itsquad.domain.Quest;
import com.example.itsquad.repository.CommentRepository;
import com.example.itsquad.repository.QuestRepository;
import com.example.itsquad.security.UserDetailsImpl;
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

    @Transactional
    public ResponseEntity<CommentResponseDto> createComment(CommentRequestDto commentRequestDto, Long questId, UserDetailsImpl userDetails) {
        Quest quest = questRepository.findById(questId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

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

        public ResponseEntity <?> getComments(Long questId) {

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
                        .build());
            }
            return new ResponseEntity<>(commentResponseDtos, HttpStatus.OK);
        }




}
