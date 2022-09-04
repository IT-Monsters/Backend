package com.example.itmonster.repository;

import com.example.itmonster.domain.Comment;
import com.example.itmonster.domain.Quest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findById(Long id);

    Optional<Comment> findByQuestId(Long questId);

    Long countAllByQuest(Quest quest);
}
