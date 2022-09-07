package com.example.itmonster.domain;

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

    private Long frontend;
    private Long backend;
    private Long fullstack;
    private Long designer;
    private Long duration; // 주단위로 기간 설정


    //진행 유무만 확인
    private Boolean status; // 모집중 / 모집완료


    @OneToMany(mappedBy = "quest")
    private List<Comment> comments;
    // 스택 추가하기

    public void updateQuest(String title, String content, Long frontend,
                            Long backend, Long fullstack, Long designer, Long duration){
        this.title = title;
        this.content = content;
        this.frontend = frontend;
        this.backend = backend;
        this.fullstack = fullstack;
        this.designer = designer;
        this.duration = duration;


    }
}
