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
public class Quest extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String title;

    private String content;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    @Enumerated(value = EnumType.STRING)
    private Position position;

    private Long minPrice;

    private Long maxPrice;

    private String expiredDate;


    //진행 유무만 확인
    private Boolean status;


    @OneToMany(mappedBy = "quest")
    private List<Comment> comments;


    public enum Position {

        FRONTEND,

        BACKEND,

        DESIGNER,

        FULLSTACK
    }


    public enum Type {

        request,

        accept
    }

    public void updateQuest(String title, String content, Type type, Position position,
        Long minPrice, Long maxPrice, String expiredDate){
        this.title = title;
        this.content = content;
        this.type = type;
        this.position = position;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.expiredDate = expiredDate;
    }
}
