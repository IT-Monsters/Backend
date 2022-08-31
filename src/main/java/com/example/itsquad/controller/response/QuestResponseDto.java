package com.example.itsquad.controller.response;

import com.example.itsquad.domain.Quest;
import com.example.itsquad.domain.Quest.Position;
import com.example.itsquad.domain.Quest.Type;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class QuestResponseDto { // 댓글 조회, 기술스택 추가해야됨 !!
    private final Long questId;
    private final String title;
    private final String content;
    private final Type type;
    private final Position position;
    private final Long minPrice;
    private final Long maxPrice;
    private final boolean status;
    private final String expiredDate;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public QuestResponseDto(Quest quest){
        this.questId = quest.getId();
        this.title = quest.getTitle();
        this.content = quest.getContent();
        this.type = quest.getType();
        this.position = quest.getPosition();
        this.minPrice = quest.getMinPrice();
        this.maxPrice = quest.getMaxPrice();
        this.status = quest.getStatus();
        this.expiredDate = quest.getExpiredDate();
        this.createdAt = quest.getCreatedAt();
        this.modifiedAt = quest.getModifiedAt();
    }
}
