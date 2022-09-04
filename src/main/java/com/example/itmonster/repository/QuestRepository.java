package com.example.itmonster.repository;

import com.example.itmonster.domain.Member;
import com.example.itmonster.domain.Quest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface QuestRepository extends JpaRepository<Quest, Long>,
    QuerydslPredicateExecutor<Quest> {
    List<Quest> findAllByOrderByModifiedAtDesc();
    List<Quest> findTop3ByOrderByModifiedAtDesc();

    List<Quest> findAllByMember(Member questOwner);
}
