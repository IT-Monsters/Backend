package com.example.itmonster.controller.response;

import com.example.itmonster.domain.Channel;
import lombok.Getter;

@Getter
public class ChannelResponseDto {
    private final Long id;
    private final String channelName;

    public ChannelResponseDto(Channel channel){
        this.id = channel.getId();
        this.channelName = channel.getChannelName();
    }
}
