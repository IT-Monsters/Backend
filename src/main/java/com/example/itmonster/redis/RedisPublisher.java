package com.example.itmonster.redis;

import com.example.itmonster.domain.Message;
import com.example.itmonster.socket.MessageResponseDto;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisPublisher {

    private static final String MESSAGE = "MESSAGE";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, List<MessageResponseDto>> opsHashMessage;

    @PostConstruct
    private void init(){
        opsHashMessage = redisTemplate.opsForHash();
    }

    public void publishSave(MessageResponseDto messageResponseDto){

        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(MessageResponseDto.class));
        String channelId = String.valueOf(messageResponseDto.getChannelId());

        List<MessageResponseDto> messageResponseDtos = opsHashMessage.get(MESSAGE, channelId);

        if(messageResponseDtos == null){
            messageResponseDtos = new ArrayList<>();
        }

        messageResponseDtos.add(0, messageResponseDto);

        opsHashMessage.put(MESSAGE, channelId, messageResponseDtos);
//        redisTemplate.expire(MESSAGE, 10, TimeUnit.SECONDS);
    }
}
