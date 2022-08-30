package com.example.itsquad.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {

    private Long id;
    private String nickname;
    private String email;
    private String phoneNum;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
