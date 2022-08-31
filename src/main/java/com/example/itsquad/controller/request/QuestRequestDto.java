package com.example.itsquad.controller.request;

import com.example.itsquad.domain.Quest.Position;
import com.example.itsquad.domain.Quest.Type;
import lombok.Getter;

@Getter
public class QuestRequestDto {

    private String title;
    private String content;
    private Type type;
    private Position position;
    // 기술스택 추가 해야됨 !!
    private Long minPrice;
    private Long maxPrice;
    private String expiredDate;
}
