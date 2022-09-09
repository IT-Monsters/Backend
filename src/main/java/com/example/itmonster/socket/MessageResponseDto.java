package com.example.itmonster.socket;

import com.example.itmonster.domain.Message;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MessageResponseDto implements Serializable {

    private Long channelId;
    private String content;
    private String createdAt;
    private Long  memberId;
    private String nickname;
    private String profileImg;

    public MessageResponseDto(Message message){
        this.channelId = message.getChannel().getId();
        this.content = message.getContent();
        this.createdAt = message.getCreatedAt();
        this.memberId = message.getMember().getId();
        this.nickname = message.getMember().getNickname();
        this.profileImg = message.getMember().getProfileImg();
    }
}
