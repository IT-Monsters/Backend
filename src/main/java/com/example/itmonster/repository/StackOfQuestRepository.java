package com.example.itmonster.repository;

import com.example.itmonster.domain.Quest;
import com.example.itmonster.domain.StackOfQuest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StackOfQuestRepository extends JpaRepository<StackOfQuest,Long> {

    List<StackOfQuest> findAllByQuest(Quest result);
    void deleteByQuest(Quest quest);
}
