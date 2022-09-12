package com.example.itmonster.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.itmonster.domain.RoleEnum;
import com.example.itmonster.security.UserDetailsImpl;

import java.util.Date;
import org.springframework.security.oauth2.core.user.OAuth2User;

public final class JwtTokenUtils {

    private static final String AUTHORITIES_KEY = "auth";
    private static final int SEC = 1;
    private static final int MINUTE = 60 * SEC;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;

    private static final int JWT_TOKEN_VALID_SEC = 3 * DAY;

    private static final int JWT_TOKEN_VALID_MILLI_SEC = JWT_TOKEN_VALID_SEC * 1000;

    public static final String CLAIM_EXPIRED_DATE = "EXPIRED_DATE";
    public static final String CLAIM_USER_NAME = "USER_NAME";
    public static final String JWT_SECRET = "jwt_secret_!@#$%";

    public static String generateJwtToken(UserDetailsImpl userDetails) {
        String token = null;
        try {
            token = JWT.create()
                .withIssuer("ITmon")
                .withClaim(CLAIM_USER_NAME, userDetails.getUsername())
                .withClaim(AUTHORITIES_KEY, RoleEnum.USER.toString())
                // 토큰 만료 일시 = 현재 시간 + 토큰 유효기간)
                .withClaim(CLAIM_EXPIRED_DATE,
                    new Date(System.currentTimeMillis() + JWT_TOKEN_VALID_MILLI_SEC))
                .sign(generateAlgorithm());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return token;
    }

    public static String generateJwtTokenByOAuth2( OAuth2User oAuth2User ) {
        String email = oAuth2User.getAttribute("email") ;
        String token = null;
        try {
            token = JWT.create()
                .withIssuer("ITmon")
                .withClaim(CLAIM_USER_NAME, email )
                .withClaim(AUTHORITIES_KEY, RoleEnum.USER.toString())
                // 토큰 만료 일시 = 현재 시간 + 토큰 유효기간)
                .withClaim(CLAIM_EXPIRED_DATE,
                    new Date(System.currentTimeMillis() + JWT_TOKEN_VALID_MILLI_SEC))
                .sign(generateAlgorithm());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return token;
    }

    private static Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(JWT_SECRET);
    }
}
