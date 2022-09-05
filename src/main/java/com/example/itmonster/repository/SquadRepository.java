package com.example.itmonster.repository;

import com.example.itmonster.domain.Member;
import com.example.itmonster.domain.Quest;
import com.example.itmonster.domain.Squad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SquadRepository extends JpaRepository<Squad, Long> {

    List<Squad> findAllByQuest(Quest quest);

    List<Squad> findAllByMember(Member me);

    Optional<Squad> findAllByMemberAndQuest(Member member, Quest quest);
}
