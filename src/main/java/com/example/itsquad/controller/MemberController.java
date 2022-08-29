package com.example.itsquad.controller;


import com.example.itsquad.controller.request.SignupImgRequestDto;
import com.example.itsquad.controller.request.SignupRequestDto;
import com.example.itsquad.security.UserDetailsImpl;
import com.example.itsquad.service.AwsS3Service;
import com.example.itsquad.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;
  private final AwsS3Service s3Service;

  //회원가입
  @PostMapping("api/member/signup")
  public ResponseEntity signupUser(@RequestBody SignupImgRequestDto requestDto) throws Exception{
//          @RequestPart("signup") SignupRequestDto requestDto,@RequestPart("profileImage") MultipartFile profileImages) throws IOException {
    String defaultImg = "https://buckitforimg.s3.ap-northeast-2.amazonaws.com/default_profile.png"; // 기본이미지
    String image = "";
    // 이미지를 안 넣으면 기본이미지 주기
    if (requestDto.getProfileImage() == null) { // 이미지가 안들어오면 true
      image = defaultImg;
    } else {  // profileImages에 유저가 등록한 이미지가 들어올 때


      String stringImage = requestDto.getProfileImage();// 이미지 등록용으로 따로 안만들고 코드 재사용 꿀
      image = s3Service.getSavedS3ImageUrl(stringImage);

    }

    return memberService.signupUser(SignupRequestDto.builder()
            .email(requestDto.getEmail())
            .nickname(requestDto.getNickname())
            .password(requestDto.getPassword())
            .phoneNum(requestDto.getPhoneNum())
            .build(), image);
  }

//  //회원가입에 이미지가 null이 들어올 때
//  @PostMapping("/api/member/signup")
//  public ResponseEntity signupNullUser(@RequestBody SignupImgRequestDto requestDto) {
//    return memberService.signupNullUser(requestDto);
//  }

  //username 중복체크
  @PostMapping("/api/member/signup/checkID")
  public ResponseEntity checkUsername(@RequestBody SignupRequestDto requestDto) {
    return memberService.checkUsername(requestDto);
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
  public String healthy(){

    return "healthy";
  }


}
