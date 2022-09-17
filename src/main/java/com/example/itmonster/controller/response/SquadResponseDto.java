package com.example.itmonster.controller.response;

import com.example.itmonster.domain.Offer.ClassType;
import com.example.itmonster.domain.Squad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SquadResponseDto {

    private Long squadId;

    private Long questId;

    private String questTitle;

    private Long memberId;

    private String nickname;

    private String profileImg;

    public SquadResponseDto( Squad squad ){
        squadId = squad.getId();
        questId = squad.getQuest().getId();
        questTitle = squad.getQuest().getTitle();
        memberId = squad.getMember().getId();
        nickname = squad.getMember().getNickname();
        profileImg = squad.getMember().getProfileImg();
    }


}
