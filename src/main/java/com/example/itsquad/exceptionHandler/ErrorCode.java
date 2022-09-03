package com.example.itsquad.exceptionHandler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    /*
    400 Bad Request
     */
    EMPTY_EMAIL(HttpStatus.BAD_REQUEST, "이메일을 입력해주세요."),
    EMPTY_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호를 입력해주세요."),
    EMPTY_NICKNAME(HttpStatus.BAD_REQUEST, "닉네임을 입력해주세요."),

    EMPTY_PHONENUMBER(HttpStatus.BAD_REQUEST,"휴대폰 번호를 입력해주세요"),

    EMAIL_WRONG(HttpStatus.BAD_REQUEST, "아이디는 이메일 형식으로 입력해주세요"),
    NICKNAME_WRONG(HttpStatus.BAD_REQUEST, "닉네임은 영문, 한글, 특수문자 다 가능합니다"),

    PHONENUMBER_WRONG(HttpStatus.BAD_REQUEST, "휴대폰 번호는 숫자여야 합니다."),

    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일이 존재합니다"),

    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "중복된 닉네임이 존재합니다"),
    PASSWORD_LEGNTH(HttpStatus.BAD_REQUEST, "비밀번호는 8자 이상 20자 이하여야 합니다"),
    NICKNAME_LEGNTH(HttpStatus.BAD_REQUEST, "닉네임은 2자 이상 10자 이하여야 합니다"),

    PHONENUMBER_LENGTH(HttpStatus.BAD_REQUEST, "휴대폰번호는 11자여야 합니다"),

    PASSWORD_WRONG(HttpStatus.BAD_REQUEST, "비밀번호는 영문, 숫자를 포함해야합니다"),

    INVALID_CATEGORY_AND_BRAND(HttpStatus.BAD_REQUEST, "브랜드와 카테고리가 전부 비어있습니다."),

    INVALID_CATEGORY(HttpStatus.BAD_REQUEST, "유효하지 않은 카테고리입니다."),




    INVALID_OFFER_REQUEST( HttpStatus.BAD_REQUEST , "자신의 게시글에는 합류요청이 불가능합니다."),
    /*
    401 UNAUTHORIZED : 인증되지 않은 사용자
    */
    AUTH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다"),
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "만료되었거나 유효하지 않은 토큰입니다"),
    INVALID_LOGIN_ATTEMPT(HttpStatus.UNAUTHORIZED, "로그인에 실패하였습니다"),
    INVALID_KAKAO_LOGIN_ATTEMPT(HttpStatus.UNAUTHORIZED, "카카오 로그인에 실패하였습니다"),
    //    INVALID_NAVER_LOGIN_ATTEMPT(HttpStatus.UNAUTHORIZED, "네이버 로그인에 실패하였습니다"),
//    INVALID_GOOGLE_LOGIN_ATTEMPT(HttpStatus.UNAUTHORIZED, "구글 로그인에 실패하였습니다"),
    /*
    403 FORBIDDEN : 권한이 없는 사용자
    */
    INVALID_AUTHORITY(HttpStatus.FORBIDDEN, "권한이 없는 사용자 입니다"),
    INVALID_AUTHORITY_WRONG(HttpStatus.FORBIDDEN, "관리자 암호가 틀려 등록이 불가능합니다"),

    /*
    404 not found
     */

    API_NOT_FOUND(HttpStatus.NOT_FOUND, "잘못된 주소입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    QUEST_NOT_FOUND( HttpStatus.NOT_FOUND , "존재하지 않는 퀘스트 입니다." ),
    SQUAD_MEMBER_NOT_FOUND( HttpStatus.NOT_FOUND , "스쿼드에 소속되지 않은 회원입니다." ),
    OFFER_NOT_FOUND( HttpStatus.NOT_FOUND , "존재하지 않는 합류요청(Offer) 입니다."),


     /*
    409 conflict
    */

    SQUAD_CONFLICT( HttpStatus.CONFLICT , "이미 스쿼드에 가입되어있습니다."),
    OFFER_CONFLICT( HttpStatus.CONFLICT , "이미 합류요청을 신청하셨습니다." );





    private final HttpStatus httpStatus;
    private final String errorMessage;

    ErrorCode(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
