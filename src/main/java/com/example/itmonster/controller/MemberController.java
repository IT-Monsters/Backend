package com.example.itmonster.controller;

import com.example.itmonster.controller.request.SignupRequestDto;
import com.example.itmonster.security.UserDetailsImpl;
import com.example.itmonster.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  //회원가입
  @PostMapping("api/members/signup")
  public ResponseEntity signupUser (@RequestBody SignupRequestDto requestDto) throws IOException {
    return memberService.signupUser(requestDto);
  }

  //username 중복체크
  @PostMapping("/api/members/checkID")
  public ResponseEntity checkUsername(@RequestBody SignupRequestDto requestDto){
    return memberService.checkUsername(requestDto);
  }

  @PostMapping("/api/members/checkNickname")
  public ResponseEntity checkNickname(@RequestBody SignupRequestDto requestDto){
    return memberService.checkNickname(requestDto);
  }

  @GetMapping("/api/members/main")
  public ResponseEntity showTop3Follows(){
    return memberService.showTop3Follows();
  }
/*
  @PostMapping("/api/members/sendMessage")
  public ResponseEntity checkSms(@RequestBody SmsRequestDto requestDto,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails){

    return memberService.sendMessage(requestDto.getPhoneNum(),userDetails);
  }
 */

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
  public String healthy(){

    return "healthy";
  }


}
