package com.example.itmonster.domain;


import com.example.itmonster.controller.request.FolioRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Folio { // 회원가입 할때

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

//    private String content;
//
//    private String fileUrl;

    private String notionUrl;
    private String githubUrl;
    private String blogUrl;

//    private Long career;

    @OneToOne
    private Member member;

    public void updateFolio(FolioRequestDto folioRequestDto){
        this.title = folioRequestDto.getTitle();
        this.notionUrl = folioRequestDto.getNotionUrl();
        this.githubUrl = folioRequestDto.getBlogUrl();
        this.blogUrl = folioRequestDto.getBlogUrl();

    }

}
