package com.example.itmonster.controller.request;

import java.util.List;
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

    private List<String> stacks;
}
