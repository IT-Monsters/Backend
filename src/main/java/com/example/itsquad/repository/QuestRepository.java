package com.example.itsquad.repository;

import com.example.itsquad.domain.Quest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    List<Quest> findAllByOrderByModifiedAtDesc();
    List<Quest> findTop3ByOrderByModifiedAtDesc();

}
