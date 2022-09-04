package com.example.itmonster.repository;

import com.example.itmonster.domain.SubComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubCommentRepository extends JpaRepository<SubComment, Long> {

    Optional<SubComment> findById(Long id);

    List<SubComment> findByCommentId(Long CommentId);

//    Optional<List<SubComment>> findByCommentId(Long CommentId);

}
