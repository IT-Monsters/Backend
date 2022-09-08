package com.example.itmonster.socket;

import com.example.itmonster.exceptionHandler.CustomException;
import com.example.itmonster.exceptionHandler.ErrorCode;
import com.example.itmonster.security.jwt.JwtDecoder;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtDecoder jwtDecoder;

    @Override
    public Message<?> preSend(@NotNull Message<?> message, @NotNull MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if(StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = Objects.requireNonNull(accessor.getFirstNativeHeader("Authorization")).substring(7);
            if(jwtDecoder.decodeUsername(token)==null){
                throw new CustomException(ErrorCode.USER_NOT_FOUND);
            }
            System.out.println(jwtDecoder.decodeUsername(token));
        }
        return message;
    }
}

