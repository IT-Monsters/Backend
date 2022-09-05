package com.example.itmonster.controller.response;

import com.example.itmonster.domain.Channel;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChannelResponseDto {
    private Long id;
    private String channelName;

    public ChannelResponseDto(Channel channel){
        this.id = channel.getId();
        this.channelName = channel.getChannelName();
    }
}
