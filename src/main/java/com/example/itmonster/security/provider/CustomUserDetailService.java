package com.example.itmonster.security.provider;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser( OAuth2UserRequest userRequest ){
        return super.loadUser( userRequest );
    }
}
