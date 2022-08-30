package com.example.itsquad.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String subject;

    private String content;

    @Enumerated(value = EnumType.STRING)
    private TypeEnum typeEnum;

    @Enumerated(value = EnumType.STRING)
    private ClassEnum classEnum;

    private Long minPrice;

    private Long maxPrice;


    //진행 유무만 확인
    private Boolean status;


    @OneToMany(mappedBy = "post")
    private List<Comment> comments;


    public enum ClassEnum {

        FRONTEND,

        BACKEND,

        DESIGNER,

        FULLSTACK
    }


    public enum TypeEnum {

        request,

        accept
    }

    public void updatePost(String subject, String content, TypeEnum typeEnum, ClassEnum classEnum,
        Long minPrice, Long maxPrice){
        this.subject = subject;
        this.content = content;
        this.typeEnum = typeEnum;
        this.classEnum = classEnum;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
}
