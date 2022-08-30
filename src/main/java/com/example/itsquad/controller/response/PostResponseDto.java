package com.example.itsquad.controller.response;

import com.example.itsquad.domain.Post;
import com.example.itsquad.domain.Post.ClassEnum;
import com.example.itsquad.domain.Post.TypeEnum;
import lombok.Getter;

@Getter
public class PostResponseDto { // 댓글 조회, 기술스택 추가해야됨 !!
    private final Long postId;
    private final String subject;
    private final String content;
    private final TypeEnum typeEnum;
    private final ClassEnum classEnum;
    private final Long minPrice;
    private final Long maxPrice;
    private final boolean status;

    public PostResponseDto(Post post){
        this.postId = post.getId();
        this.subject = post.getSubject();
        this.content = post.getContent();
        this.typeEnum = post.getTypeEnum();
        this.classEnum = post.getClassEnum();
        this.minPrice = post.getMinPrice();
        this.maxPrice = post.getMaxPrice();
        this.status = post.getStatus();
    }
}
