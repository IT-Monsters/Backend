package com.example.itsquad.controller.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CommentResponseDto {


    private Long commentId;

    private String nickname;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String profileImage;

}
