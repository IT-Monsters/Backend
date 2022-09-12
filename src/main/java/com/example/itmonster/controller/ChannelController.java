package com.example.itmonster.controller;

import com.example.itmonster.controller.response.ChannelResponseDto;
import com.example.itmonster.domain.Channel;
import com.example.itmonster.security.UserDetailsImpl;
import com.example.itmonster.service.ChannelService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/channels")
public class ChannelController {

    private final ChannelService channelService;

    @GetMapping("")
    public List<ChannelResponseDto> readChannel(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return channelService.readChannel(userDetails);
    }
}
