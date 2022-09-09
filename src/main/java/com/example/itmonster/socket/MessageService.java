package com.example.itmonster.socket;

import com.example.itmonster.domain.Channel;
import com.example.itmonster.domain.Member;
import com.example.itmonster.domain.Message;
import com.example.itmonster.repository.ChannelRepository;
import com.example.itmonster.repository.MemberRepository;
import com.example.itmonster.security.jwt.JwtDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final JwtDecoder jwtDecoder;
    private final MemberRepository memberRepository;
    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;

    @Transactional(readOnly = true)
    public List<MessageResponseDto> readMessages(Long channelId) {
        return messageRepository.findTop100ByChannelIdOrderByCreatedAtDesc(channelId).stream().map(MessageResponseDto::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public void sendMessage(MessageRequestDto messageRequestDto, Long channelId, String token) {
        String email = jwtDecoder.decodeUsername(token);
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        Channel channel = channelRepository.findById(channelId)
            .orElseThrow(() -> new IllegalArgumentException("채널이 존재하지 않습니다."));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String dateResult = sdf.format(date);

        Message message = Message.builder()
            .content(messageRequestDto.getContent())
            .member(member)
            .channel(channel)
            .createdAt(dateResult)
            .build();

        MessageResponseDto messageResponseDto = MessageResponseDto.builder()
            .channelId(channel.getId())
            .content(messageRequestDto.getContent())
            .createdAt(message.getCreatedAt())
            .memberId(member.getId())
            .nickname(member.getNickname())
            .profileImg(member.getProfileImg())
            .build();

        redisTemplate.convertAndSend(channelTopic.getTopic(), messageResponseDto);

        messageRepository.save(message);
    }
}
