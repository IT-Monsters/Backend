package com.example.itmonster.socket;

import com.example.itmonster.domain.Channel;
import com.example.itmonster.domain.Member;
import com.example.itmonster.domain.Message;
import com.example.itmonster.exceptionHandler.CustomException;
import com.example.itmonster.exceptionHandler.ErrorCode;
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
    public List<MessageResponseDto> readMessages(Long channelId) {   // 메시지 불러오기

        HashOperations<String, String, List<MessageResponseDto>> opsHashChatMessage = redisTemplate.opsForHash();

        List<MessageResponseDto> messageResponseDtos = new ArrayList<>();
        List<MessageResponseDto> temp1 = (opsHashChatMessage.get(MESSAGE,
            String.valueOf(channelId)));
        List<MessageResponseDto> temp2 = messageRepository.findTop100ByChannelIdOrderByCreatedAtDesc(
                channelId).stream()
            .map(MessageResponseDto::new).collect(Collectors.toList());
        if (temp1 != null) {
            messageResponseDtos.addAll(temp1);   // redis에 저장된 메시지
        }
        messageResponseDtos.addAll(temp2);       // RDS db에 저장된 메시지 최신순 100개
        messageResponseDtos.sort((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()));
        return messageResponseDtos;
    }

    @Transactional
    public void sendMessage(MessageRequestDto messageRequestDto, Long channelId, String token) {
        String email = jwtDecoder.decodeUsername(token);
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss.SSS");  // LocalDateTime 직렬화 오류
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String dateResult = sdf.format(date);

        MessageResponseDto messageResponseDto = MessageResponseDto.builder()
            .channelId(channelId)
            .content(messageRequestDto.getContent())
            .createdAt(dateResult)
            .memberId(member.getId())
            .nickname(member.getNickname())
            .profileImg(member.getProfileImg())
            .build();

        redisTemplate.convertAndSend(channelTopic.getTopic(), messageResponseDto);

        redisPublisher.publishSave(messageResponseDto);  // redis에 메시지를 저장
        redisToRds(String.valueOf(channelId));
            // redis에 메시지가 100개 이상 저장되면 RDS DB에 저장하고 Redis 데이터 삭제
    }

    private void redisToRds(String channelId) {
        HashOperations<String, String, List<MessageResponseDto>> opsHashChatMessage = redisTemplate.opsForHash();

        List<MessageResponseDto> messageResponseDtos = opsHashChatMessage.get(MESSAGE, channelId);

        Channel channel = channelRepository.findById(Long.parseLong(channelId))
            .orElseThrow(() -> new CustomException(ErrorCode.CHANNEL_NOT_FOUND));

        if (messageResponseDtos != null) {
            log.info("데이터수={}", messageResponseDtos.size());
        }
        if (messageResponseDtos != null && messageResponseDtos.size() >= 100) {
            Long senderId;
            for (MessageResponseDto messageResponseDto : messageResponseDtos) {
                senderId = messageResponseDto.getMemberId();
                Member sender = memberRepository.findById(senderId)
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
                messageRepository.save(Message.builder()
                    .content(messageResponseDto.getContent())
                    .member(sender)
                    .channel(channel)
                    .createdAt(messageResponseDto.getCreatedAt())
                    .build());
            }
            opsHashChatMessage.delete(MESSAGE, channelId);
        }
    }
}
