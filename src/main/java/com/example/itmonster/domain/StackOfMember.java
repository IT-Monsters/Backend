package com.example.itmonster.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StackOfMember {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @JsonIgnore
    private Long Id;

    @Column( nullable = false )
    private String stackName;

    @JoinColumn
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Member member;

}