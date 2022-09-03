package com.example.itsquad.service;

import com.example.itsquad.controller.request.SignupRequestDto;
import com.example.itsquad.controller.response.SocialLoginResponseDto;
import com.example.itsquad.domain.Member;
import com.example.itsquad.domain.RoleEnum;
import com.example.itsquad.exceptionHandler.CustomException;
import com.example.itsquad.exceptionHandler.ErrorCode;
import com.example.itsquad.repository.MemberRepository;
import com.example.itsquad.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AwsS3Service s3Service;

    DefaultMessageService messageService;


    String emailPattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"; //이메일 정규식 패턴
    String nicknamePattern = "^[a-zA-Z0-9ㄱ-ㅎ|ㅏ-ㅣ|가-힣~!@#$%^&*]{2,8}"; // 영어대소문자 , 한글 , 특수문자포함 2~8자까지
    String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$";


    String phoneNumPattern = "^(\\d{11})$";

    @Value("${coolsms.api_key}")
    String api_key;

    @Value("${coolsms.api_secret}")
    String api_secret;

    @Value("${coolsms.send_number}")
    String send_number;



//    @Value("${spring.admin.token}") // 어드민 가입용
//    String ADMIN_TOKEN;

    public ResponseEntity signupUser(SignupRequestDto requestDto) throws IOException {


        String profileUrl = s3Service.getSavedS3ImageUrl(requestDto.getProfileImage());

        checkEmailPattern(requestDto.getEmail());//username 정규식 맞지 않는 경우 오류메시지 전달
        checkNicknamePatter(requestDto.getNickname());//nickname 정규식 맞지 않는 경우 오류메시지 전달
        checkPasswordPattern(requestDto.getPassword());//password 정규식 맞지 않는 경우 오류메시지 전달

        String password = passwordEncoder.encode(requestDto.getPassword()); // 패스워드 암호화

        Member member = Member.builder()
                .email(requestDto.getEmail())
                .nickname(requestDto.getNickname())
                .password(password)
                .profileImg(profileUrl)
                .phoneNum(null)
                .role(RoleEnum.USER)
                .build();
        memberRepository.save(member);

        return new ResponseEntity("회원가입을 축하합니다", HttpStatus.OK);
    }

    public ResponseEntity sendMessage (String phoneNum,UserDetailsImpl userDetails){
        checkPhoneNumb(phoneNum); //번호유효성

        this.messageService = NurigoApp.INSTANCE.initialize(api_key,api_secret,"http://localhost:8080");

        Message message = new Message();
        Random rand  = new Random();

        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }

        message.setFrom(send_number);    // 발신번호
        message.setTo(phoneNum);    // 수신번호
        message.setText("인증번호는 [" + numStr + "] 입니다.");

        Member member = userDetails.getMember();
        member.updatePhoneNumber(numStr);
        memberRepository.save(member);


        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        return new ResponseEntity(response,HttpStatus.OK);
    }



    //username 중복체크
    public ResponseEntity checkUsername(SignupRequestDto requestDto){
        checkEmailPattern(requestDto.getEmail());
        return new ResponseEntity("사용 가능한 이메일입니다.", HttpStatus.OK);
    }

    public ResponseEntity checkNickname(SignupRequestDto requestDto){
        checkNicknamePatter(requestDto.getNickname());
        return new ResponseEntity("사용 가능한 닉네임입니다.", HttpStatus.OK);
    }


    //소셜로그인 사용자 정보 조회
    public ResponseEntity socialUserInfo(UserDetailsImpl userDetails) {
        //로그인 한 user 정보 검색
        Member member = memberRepository.findBySocialId(userDetails.getMember().getSocialId())
                .orElseThrow(
                        () -> new CustomException(ErrorCode.USER_NOT_FOUND)
                );

        //찾은 user엔티티를 dto로 변환해서 반환하기
        SocialLoginResponseDto socialLoginResponseDto = new SocialLoginResponseDto(member, true);
        return ResponseEntity.ok().body(socialLoginResponseDto);
    }


    public Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Member) authentication.getPrincipal();
    }


    public void checkEmailPattern(String email) {
        if (email == null) throw new CustomException(ErrorCode.EMPTY_EMAIL);
        if (email.equals("")) throw new CustomException(ErrorCode.EMPTY_EMAIL);
        if (!Pattern.matches(emailPattern, email)) throw new CustomException(ErrorCode.EMAIL_WRONG);
        if (memberRepository.findByEmail(email).isPresent()) throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
    }


    public void checkPasswordPattern(String password) {
        if (password == null) throw new CustomException(ErrorCode.EMPTY_PASSWORD);
        if (password.equals("")) throw new CustomException(ErrorCode.EMPTY_PASSWORD);
        if (8 > password.length() || 20 < password.length()) throw new CustomException(ErrorCode.PASSWORD_LEGNTH);
        if (!Pattern.matches(passwordPattern, password)) throw new CustomException(ErrorCode.PASSWORD_WRONG);
    }


    public void checkNicknamePatter(String nickname) {
        if (nickname == null) throw new CustomException(ErrorCode.EMPTY_NICKNAME);
        if (nickname.equals("")) throw new CustomException(ErrorCode.EMPTY_NICKNAME);
        if (2 > nickname.length() || 8 < nickname.length()) throw new CustomException(ErrorCode.NICKNAME_LEGNTH);
        if (!Pattern.matches(nicknamePattern, nickname)) throw new CustomException(ErrorCode.NICKNAME_WRONG);
    }

    public void checkPhoneNumb(String phoneNum){
        if (phoneNum == null) throw new CustomException(ErrorCode.EMPTY_PHONENUMBER);
        if (phoneNum.equals("")) throw new CustomException(ErrorCode.EMPTY_PHONENUMBER);
        if (phoneNum.length() != 11) throw new CustomException(ErrorCode.PHONENUMBER_LENGTH);
        if (!Pattern.matches(phoneNumPattern,phoneNum)) throw new CustomException(ErrorCode.PHONENUMBER_WRONG);

    }



}


//로그인 후 관리자 권한 얻을 수 있는 API 관리자 접근 가능 페이지 없슴
//    public ResponseEntity adminAuthorization(AdminRequestDto requestDto, UserDetailsImpl userDetails) {
//        // 사용자 ROLE 확인
//        UserRoleEnum role = UserRoleEnum.USER;
//        if (requestDto.isAdmin()) {
//            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
//                throw new CustomException(ErrorCode.INVALID_AUTHORITY_WRONG); // 토큰값이 틀림
//            }
//            role = UserRoleEnum.ADMIN;
//        }
//
//        //역할 변경
//        userDetails.getUser().setRole(role);
//        //변경된 역할 저장
//        userRepository.save(userDetails.getUser());
//        return new ResponseEntity("관리자 권한으로 변경되었습니다", HttpStatus.OK);
//    }




