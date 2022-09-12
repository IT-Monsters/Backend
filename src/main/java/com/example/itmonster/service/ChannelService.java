package com.example.itmonster.service;

import com.example.itmonster.controller.response.ChannelResponseDto;
import com.example.itmonster.domain.Channel;
import com.example.itmonster.domain.Member;
import com.example.itmonster.domain.MemberInChannel;
import com.example.itmonster.domain.Quest;
import com.example.itmonster.domain.Squad;
import com.example.itmonster.repository.ChannelRepository;
import com.example.itmonster.repository.MemberInChannelRepository;
import com.example.itmonster.repository.QuestRepository;
import com.example.itmonster.repository.SquadRepository;
import com.example.itmonster.security.UserDetailsImpl;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final MemberInChannelRepository memberInChannelRepository;

    @Transactional
    public Channel createChannel(Quest quest) {
        Channel channel = Channel.builder()
            .channelName(quest.getTitle())
            .quest(quest)
            .build();
        channelRepository.save(channel);
        return channel;
    }

    @Transactional(readOnly = true)
    public List<ChannelResponseDto> readChannel(UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        List<MemberInChannel> memberInChannels = memberInChannelRepository.findAllByMember(member);
        return memberInChannels.stream().map(MemberInChannel::getChannel)
            .map(ChannelResponseDto::new).collect(Collectors.toList());
    }
}
