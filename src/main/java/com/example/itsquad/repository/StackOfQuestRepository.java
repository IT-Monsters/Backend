package com.example.itsquad.repository;

import com.example.itsquad.domain.Quest;
import com.example.itsquad.domain.StackOfQuest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StackOfQuestRepository extends JpaRepository<StackOfQuest,Long> {

    List<StackOfQuest> findAllByQuest(Quest result);
}
