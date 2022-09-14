package com.example.itmonster.controller.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto implements Serializable {
  private String nickname;
  private String profileImage;
  private List<StackDto> stacks;
  private Long followCnt;
  private String folioTitle;
  //추후 포트폴리오 이름
}
