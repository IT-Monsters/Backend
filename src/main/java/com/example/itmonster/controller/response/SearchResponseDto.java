package com.example.itmonster.controller.response;

import com.example.itmonster.domain.Quest;
import com.example.itmonster.domain.StackOfQuest;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponseDto {

    private Long questId;
    private String title;
    private String content;

    private Long memberId;
    private String nickname;

    private Long frontend;
    private Long backend;
    private Long fullstack;
    private Long designer;

    private Long duration;

    private List<StackDto> stacks;

    public SearchResponseDto( Quest quest , List<StackOfQuest> stacks ){
        questId = quest.getId();
        title = quest.getTitle();
        content = quest.getContent();

        memberId = quest.getMember().getId();
        nickname = quest.getMember().getNickname();

        frontend = quest.getFrontend();
        backend = quest.getBackend();
        fullstack = quest.getFullstack();
        designer = quest.getDesigner();

        duration = quest.getDuration();

        List<StackDto> stackDtoList = new ArrayList<>();
        stacks.forEach( stack -> stackDtoList.add( new StackDto( stack )));

        this.stacks = stackDtoList;

    }


}
