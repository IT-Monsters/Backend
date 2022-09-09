package com.example.itmonster.socket;

import com.example.itmonster.domain.Channel;
import com.example.itmonster.domain.Member;
import com.example.itmonster.domain.Message;
import com.example.itmonster.redis.RedisPublisher;
import com.example.itmonster.repository.ChannelRepository;
import com.example.itmonster.repository.MemberRepository;
import com.example.itmonster.security.jwt.JwtDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private static final String MESSAGE = "MESSAGE";
    private final RedisPublisher redisPublisher;
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final JwtDecoder jwtDecoder;
    private final MemberRepository memberRepository;
    private final ChannelTopic channelTopic;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional(readOnly = true)
    public List<MessageResponseDto> readMessages(Long channelId) {

        HashOperations<String, String, List<MessageResponseDto>> opsHashChatMessage = redisTemplate.opsForHash();

        List<MessageResponseDto> messageResponseDtos = new ArrayList<>();
        List<MessageResponseDto> temp1 = (opsHashChatMessage.get(MESSAGE, String.valueOf(channelId)));
        List<MessageResponseDto> temp2 =  messageRepository.findTop100ByChannelIdOrderByCreatedAtDesc(channelId).stream()
            .map(MessageResponseDto::new).collect(Collectors.toList());
        if(temp1 != null){
            messageResponseDtos.addAll(temp1);
        }
        messageResponseDtos.addAll(temp2);
        return messageResponseDtos;
    }

    @Transactional
    public void sendMessage(MessageRequestDto messageRequestDto, Long channelId, String token) {
        String email = jwtDecoder.decodeUsername(token);
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

//        Channel channel = channelRepository.findById(channelId)
//            .orElseThrow(() -> new IllegalArgumentException("채널이 존재하지 않습니다."));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String dateResult = sdf.format(date);

//        Message message = Message.builder()
//            .content(messageRequestDto.getContent())
//            .member(member)
//            .channel(channel)
//            .createdAt(dateResult)
//            .build();

        MessageResponseDto messageResponseDto = MessageResponseDto.builder()
            .channelId(channelId)
            .content(messageRequestDto.getContent())
            .createdAt(dateResult)
            .memberId(member.getId())
            .nickname(member.getNickname())
            .profileImg(member.getProfileImg())
            .build();

        redisPublisher.publishSave(messageResponseDto);
        redisToRds(String.valueOf(channelId));
        redisTemplate.convertAndSend(channelTopic.getTopic(), messageResponseDto);

//        messageRepository.save(message);
    }

    public void redisToRds(String channelId) {
        HashOperations<String, String, List<MessageResponseDto>> opsHashChatMessage = redisTemplate.opsForHash();

        List<MessageResponseDto> messageResponseDtos = opsHashChatMessage.get(MESSAGE, channelId);
//        log.info("데이터수={}", messageResponseDtos.size());
        if(messageResponseDtos != null && messageResponseDtos.size() > 100){
            for(MessageResponseDto messageResponseDto : messageResponseDtos){
                messageRepository.save(Message.builder()
                    .content(messageResponseDto.getContent())
                    .member(memberRepository.getById(messageResponseDto.getMemberId()))
                    .channel(channelRepository.getById(messageResponseDto.getChannelId()))
                    .createdAt(messageResponseDto.getCreatedAt())
                    .build());
            }
            opsHashChatMessage.delete(MESSAGE, channelId);
        }
    }
}
