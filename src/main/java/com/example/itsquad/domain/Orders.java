package com.example.itsquad.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JoinColumn
    @ManyToOne
    private Member fromMember;

    @JoinColumn
    @ManyToOne
    private Member toMember;

    @JoinColumn
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    //fromMember 수락상태
    @Column(nullable = false)
    private Boolean orderStatusFromMember;

    //fromMember 수락상태
    @Column(nullable = false)
    private Boolean orderStatusToMember;

    // 계약현황
    @Column(nullable = false)
    private StatusEnum statusEnum;

    public enum StatusEnum{

        PROCESSING,

        COMPLETE,

        CANCEL


    }



}
