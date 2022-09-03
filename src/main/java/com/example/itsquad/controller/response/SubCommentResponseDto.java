package com.example.itsquad.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class SubCommentResponseDto {

    private Long commentId;

    private Long subCommentId;

    private String nickname;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String profileImage;
}
