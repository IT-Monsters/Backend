package com.example.itsquad.controller.request;

import lombok.Getter;

@Getter
public class QuestRequestDto {

    private String title;
    private String content;
    private Long frontend;
    private Long backend;
    private Long fullstack;
    private Long designer;
    private Long duration; // 주단위로 기간 설정
    // 기술스택 추가 해야됨 !!
    private String stacks;
}
