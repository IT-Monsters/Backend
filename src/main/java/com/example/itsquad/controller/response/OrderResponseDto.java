package com.example.itsquad.controller.response;

import com.example.itsquad.domain.Offer;
import com.example.itsquad.domain.Quest.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private Long orderId;

    private Long postId;       // postman 확인용

    private Long postOwnerId;      // postman 확인용

    private Long fromMemberId;      //postman 확인용

    private String profileImage;

    private String nickname;

    private String title;

    private Type type;

    private boolean IsMyPost;

    public OrderResponseDto(Offer order , boolean isMyPost ){
        orderId = order.getId();
        postId = order.getQuest().getId();
        postOwnerId = order.getToMember().getId();
        fromMemberId = order.getFromMember().getId();
        profileImage = order.getQuest().getMember().getProfileImg();
        nickname = order.getQuest().getMember().getNickname();
        title = order.getQuest().getTitle();
        type = order.getQuest().getType();
        this.IsMyPost = isMyPost;
    }

}
