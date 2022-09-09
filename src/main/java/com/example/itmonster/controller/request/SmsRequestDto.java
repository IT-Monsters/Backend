package com.example.itmonster.controller.request;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Getter
@Setter
public class SmsRequestDto {
    private String type = "SMS";
    private String contentType = "COMM";
    private String countryCode = "82";
    private String content = "회원가입 휴대폰 인증 코드입니다.";
    private List<SmsMessage> messages;
    private String phoneNumber;
    @Value("${spring.naver.from}")
    private String from;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class SmsMessage {
        private String to;
        private String content;
    }
}