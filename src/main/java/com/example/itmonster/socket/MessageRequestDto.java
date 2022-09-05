package com.example.itmonster.socket;

import lombok.Getter;

@Getter
public class MessageRequestDto {
    private String content;
    private String token;  // 백엔드 테스트용
}
