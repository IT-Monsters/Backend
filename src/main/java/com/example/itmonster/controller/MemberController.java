package com.example.itmonster.controller;

import com.example.itmonster.controller.request.SignupRequestDto;
import com.example.itmonster.controller.request.SmsRequestDto;
import com.example.itmonster.controller.response.StackDto;
import com.example.itmonster.security.UserDetailsImpl;
import com.example.itmonster.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @PostMapping("api/members/signup")
    public ResponseEntity signupUser(@RequestBody SignupRequestDto requestDto) throws IOException {
        return memberService.signupUser(requestDto);
    }

    //username 중복체크
    @PostMapping("/api/members/checkID")
    public ResponseEntity checkUsername(@RequestBody SignupRequestDto requestDto) {
        return memberService.checkUsername(requestDto);
    }

    @PostMapping("/api/members/checkNickname")
    public ResponseEntity checkNickname(@RequestBody SignupRequestDto requestDto) {
        return memberService.checkNickname(requestDto);
    }

    @PostMapping("/api/members/{memberId}/follow")
    public ResponseEntity followMember(@PathVariable Long memberId,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memberService.followMember(memberId, userDetails.getMember());
    }


    @PostMapping("/api/members/addStack")
    public ResponseEntity addStack(@RequestBody StackDto requestDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memberService.addStack(requestDto, userDetails.getMember());
    }

    @GetMapping("/api/monster/month")
    public ResponseEntity showTop3Following() {
        return memberService.showTop3Following();
    }


    @GetMapping("/api/members/status")
    public ResponseEntity memberInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(memberService.memberInfo(userDetails.getMember()));
    }

    @PostMapping("/api/members/sendMessage")
    public ResponseEntity sendMessage(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @RequestBody SmsRequestDto requestDto) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        return ResponseEntity.ok(memberService.sendMessagetoMember(requestDto.getPhoneNumber(), userDetails.getMember()));
    }

    //로그인 후 관리자 권한 얻을 수 있는 API
//  @PutMapping("/api/signup/admin")
//  public ResponseEntity adminAuthorization(@RequestBody AdminRequestDto requestDto,
//                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
//    return memberService.adminAuthorization(requestDto, userDetails);
//  }

    //소셜로그인 사용자 정보 조회
    @GetMapping("/social/member/islogin")
    public ResponseEntity socialUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memberService.socialUserInfo(userDetails);
    }

    @GetMapping("/health")
    public String healthy() {

        return "healthy";
    }


}
