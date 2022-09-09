package com.example.itmonster.redis;

import com.example.itmonster.exceptionHandler.CustomException;
import com.example.itmonster.exceptionHandler.ErrorCode;
import com.example.itmonster.socket.MessageResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriber {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;

    //convertAndSend 로 데이터를 보내면 여기서 잡아서 보낸다.
    // Redis 에서 메시지가 발행(publish)되면 대기하고 있던 Redis Subscriber 가 해당 메시지를 받아 처리한다.
    public void sendMessage(String publishMessage) {
        log.info("데이터 확인 publishMessage={}", publishMessage);
        try {
            // ChatMessage 객채로 맵핑
            MessageResponseDto message = objectMapper.readValue(publishMessage, MessageResponseDto.class);
            // 채팅방을 구독한 클라이언트에게 메시지 발송x`
            messagingTemplate.convertAndSend("/sub/channels/" + message.getChannelId(), message);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FAILED_MESSAGE);
        }
    }
}
