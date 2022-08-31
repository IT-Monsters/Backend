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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AwsS3Service s3Service;

    String emailPattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"; //이메일 정규식 패턴
    String nicknamePattern = "^[a-zA-Z0-9ㄱ-ㅎ|ㅏ-ㅣ|가-힣~!@#$%^&*]{2,8}"; // 영어대소문자 , 한글 , 특수문자포함 2~8자까지
    String passwordPattern = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}"; //영어, 숫자 8자이상 20이하

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

    //username 중복체크
    public ResponseEntity checkUsername(SignupRequestDto requestDto) {
        checkEmailPattern(requestDto.getEmail());
        return new ResponseEntity("사용 가능한 이메일입니다.", HttpStatus.OK);
    }

    public ResponseEntity checkNickname(SignupRequestDto requestDto) {
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

        if (email.equals("")) {
            throw new CustomException(ErrorCode.EMPTY_EMAIL);
        } else if (!Pattern.matches(emailPattern, email)) {
            throw new CustomException(ErrorCode.EMAIL_WRONG);
        } else if (memberRepository.findByEmail(email).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    public void checkPasswordPattern(String password) {

        if (password.equals("")) {
            throw new CustomException(ErrorCode.EMPTY_PASSWORD);
        } else if (8 > password.length() || 20 < password.length()) {
            throw new CustomException(ErrorCode.PASSWORD_LEGNTH);
        } else if (!Pattern.matches(passwordPattern, password)) {
            throw new CustomException(ErrorCode.PASSWORD_WRONG);
        }
    }

    public void checkNicknamePatter(String nickname) {

        if (nickname.equals("")) {
            throw new CustomException(ErrorCode.EMPTY_NICKNAME);
        } else if (2 > nickname.length() || 8 < nickname.length()) {
            throw new CustomException(ErrorCode.NICKNAME_LEGNTH);
        } else if (!Pattern.matches(nicknamePattern, nickname)) {
            throw new CustomException(ErrorCode.NICKNAME_WRONG);
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
}



