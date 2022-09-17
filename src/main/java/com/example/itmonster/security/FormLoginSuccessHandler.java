package com.example.itmonster.security;

import com.example.itmonster.controller.response.LoginResponseDto;
import com.example.itmonster.controller.response.MemberResponseDto;
import com.example.itmonster.controller.response.StackDto;
import com.example.itmonster.domain.Member;
import com.example.itmonster.domain.StackOfMember;
import com.example.itmonster.repository.FolioRepository;
import com.example.itmonster.repository.StackOfMemberRepository;
import com.example.itmonster.security.jwt.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;


public class FormLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "BEARER";
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private StackOfMemberRepository stackOfMemberRepository;

    @Autowired
    private FolioRepository folioRepository;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {
        final UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        // Token 생성
        final String token = JwtTokenUtils.generateJwtToken(userDetails);
        System.out.println(userDetails.getUsername() + "'s token : " + TOKEN_TYPE + " " + token);
        response.addHeader(AUTH_HEADER, TOKEN_TYPE + " " + token);
        System.out.println("LOGIN SUCCESS!");

        //Member 정보 프론트 전달
        response.setContentType("application/json; charset=utf-8");
        Member member = userDetails.getMember();
        LoginResponseDto loginResponseDto = new LoginResponseDto(member.getId(), member.getNickname(), true, token,
                                                                    member.getProfileImg(),getStackList(member),
                                                                folioRepository.findByMemberId(member.getId()).getTitle(), member.getFollowCounter());

        String result = mapper.writeValueAsString(loginResponseDto);
        response.getWriter().write(result);
    }

    public List<StackDto> getStackList(Member member){
        List<StackDto> stacks = new ArrayList<>();
        List<StackOfMember> stackOfMemberList = stackOfMemberRepository.findByMemberId(member.getId());
        if(stackOfMemberList.size() == 0L) return stacks;

        for(StackOfMember stack : stackOfMemberList){
            stacks.add(new StackDto(stack.getStackName()));
        }

        return stacks;
    }
}
