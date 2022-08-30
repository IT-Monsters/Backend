package com.example.itsquad.controller.request;

import com.example.itsquad.domain.Post.ClassEnum;
import com.example.itsquad.domain.Post.TypeEnum;
import lombok.Getter;

@Getter
public class PostRequestDto {

    private String subject;
    private String content;
    private TypeEnum typeEnum;
    private ClassEnum classEnum;
    // 기술스택 추가 해야됨 !!
    private Long minPrice;
    private Long maxPrice;
}
