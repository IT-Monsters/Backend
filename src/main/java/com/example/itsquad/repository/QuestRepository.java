package com.example.itsquad.repository;

import com.example.itsquad.domain.Member;
import com.example.itsquad.domain.Quest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface QuestRepository extends JpaRepository<Quest, Long>,
    QuerydslPredicateExecutor<Quest> {
    List<Quest> findAllByOrderByModifiedAtDesc();
    List<Quest> findTop3ByOrderByModifiedAtDesc();

    List<Quest> findAllByMember(Member questOwner);
}
