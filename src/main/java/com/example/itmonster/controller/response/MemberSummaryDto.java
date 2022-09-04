package com.example.itmonster.controller.response;

import com.example.itmonster.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberSummaryDto {
    private Long id;
    private String email;
    private String nickname;
    private String profileImg;

    private String phoneNum;

    public MemberSummaryDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.phoneNum = member.getPhoneNum();
        this.profileImg = member.getProfileImg();
    }
}
