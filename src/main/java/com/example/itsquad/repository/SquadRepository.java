package com.example.itsquad.repository;

import com.example.itsquad.domain.Member;
import com.example.itsquad.domain.Quest;
import com.example.itsquad.domain.Squad;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SquadRepository extends JpaRepository<Squad, Long> {

    List<Squad> findAllByQuest(Quest quest);

    List<Squad> findAllByMember(Member me);

    Optional<Squad> findAllByMemberAndQuest(Member member, Quest quest);
}
