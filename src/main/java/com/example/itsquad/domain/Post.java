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
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;


    private String subject;

    private String content;

    private TypeEnum typeEnum;

    private ClassEnum classEnum;

    private Long minPrice;

    private Long maxPrice;

    private StatusEnum statusEnum;



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

    public enum StatusEnum{

        PROCESSING,

        COMPLETE,

        CANCEL


    }

}


