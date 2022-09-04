package com.example.itmonster.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class SubCommentResponseDto {

    @JsonIgnore
    private Long commentId;

    @JsonIgnore
    private Long subCommentId;

    private String nickname;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String profileImage;
}
