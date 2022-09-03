package com.example.itsquad.controller.response;

import com.example.itsquad.domain.Offer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDto {

    private Long memberId;

    private Long postId;

//    private Long chatId;

    private String profileImage;

    private String title;

    private Long minPrice;

    private Long maxPrice;

    public OrderDetailsDto( Offer order ){

        memberId = order.getToMember().getId();
        postId = order.getQuest().getId();
        profileImage = order.getToMember().getProfileImg();
        title = order.getQuest().getTitle();
        minPrice = order.getQuest().getMinPrice();
        maxPrice = order.getQuest().getMaxPrice();

    }

}
