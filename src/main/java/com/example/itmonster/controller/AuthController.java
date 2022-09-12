package com.example.itmonster.controller;

import com.example.itmonster.service.AuthService;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
class AuthController{

    private final AuthService authService;

    /**
     * token 생성해서 보내주기
     */
    @GetMapping("/login")
    public ResponseEntity<String> login ( @AuthenticationPrincipal OAuth2User oAuth2User , HttpServletResponse response) {
        return ResponseEntity.ok( authService.login( oAuth2User,response ) );
    }
}
