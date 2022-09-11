package com.example.itmonster.controller;


import com.example.itmonster.controller.request.SmsRequestDto;
import com.example.itmonster.security.UserDetailsImpl;
import com.example.itmonster.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;


    @PostMapping("api/members/sendSms")
    public ResponseEntity sendSmsAuth (@RequestBody SmsRequestDto smsRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws NoSuchAlgorithmException, InvalidKeyException {
        return smsService.sendSms(smsRequestDto.getPhoneNo(),userDetails.getMember().getId());
    }


    @PostMapping("api/members/updatePhoneNo")
    public ResponseEntity updatePhoneNumber(@RequestBody SmsRequestDto smsRequestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return smsService.updatePhoneNo(smsRequestDto.getPhoneNo(),smsRequestDto.getAuthNo(),userDetails.getMember());
    }
}
