package com.example.itsquad.service;


import com.example.itsquad.controller.request.SubCommentRequestDto;
import com.example.itsquad.controller.response.SubCommentResponseDto;
import com.example.itsquad.domain.Comment;
import com.example.itsquad.domain.Member;
import com.example.itsquad.domain.SubComment;
import com.example.itsquad.repository.CommentRepository;
import com.example.itsquad.repository.SubCommentRepository;
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
public class SubCommentService {

    private final SubCommentRepository subCommentRepository;

    private final CommentRepository commentRepository;

    @Transactional
    public ResponseEntity<SubCommentResponseDto> createSubComment(SubCommentRequestDto subCommentRequestDto, Long commentId, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

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
