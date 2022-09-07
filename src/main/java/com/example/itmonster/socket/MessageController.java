package com.example.itmonster.socket;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations simpleMessageSendingOperations;
    private final MessageService messageService;

    @ResponseBody
    @GetMapping("/api/channels/{channelId}")
    public List<MessageResponseDto> readMessages(@PathVariable Long channelId){
        return messageService.readMessages(channelId);
    }

    @MessageMapping(value = {"/channels/{channelId}"})
//    public void addMessage(@RequestBody MessageRequestDto messageRequestDto, @DestinationVariable Long roomId,
//        @Header("Authorization") String token)  // 백엔드 테스트용
    public void sendMessage(@RequestBody MessageRequestDto messageRequestDto, @DestinationVariable Long channelId){
        String token = messageRequestDto.getToken().substring(7);
        System.out.println(token);
//        token = token.substring(7);   // 백엔드 테스트용
        MessageResponseDto messageResponseDto = messageService.sendMessage(messageRequestDto, channelId, token);
        simpleMessageSendingOperations.convertAndSend("/sub/channels/" + channelId, messageResponseDto);
    }
}
