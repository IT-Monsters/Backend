package com.example.itmonster.controller.response;

import com.example.itmonster.domain.Offer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OfferResponseDto {

    private Long offerId;

    private Long questId;

    private String quest_title;

    private Long offeredMemberId;

    private String profileImg;


    public OfferResponseDto ( Offer offer ){
        offerId = offer.getId();
        offeredMemberId = offer.getOfferedMember().getId();
        questId = offer.getQuest().getId();
        profileImg = offer.getOfferedMember().getProfileImg();
        quest_title = offer.getQuest().getTitle();
    }

}
