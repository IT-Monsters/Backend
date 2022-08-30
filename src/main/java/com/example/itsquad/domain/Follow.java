package com.example.itsquad.domain;


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
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //나
    @ManyToOne
    @JoinColumn(nullable = false)
    private Member me;


    // 내가 팔로우한 사람.
    @ManyToOne
    @JoinColumn(nullable = false)
    private Member follwing;


}
