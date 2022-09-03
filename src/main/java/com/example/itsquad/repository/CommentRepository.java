package com.example.itsquad.repository;

import com.example.itsquad.domain.Comment;
import com.example.itsquad.domain.Quest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.OptionalLong;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findById(Long id);

    Optional<Comment> findByQuestId(Long questId);

    Long countAllByQuest(Quest quest);
}
