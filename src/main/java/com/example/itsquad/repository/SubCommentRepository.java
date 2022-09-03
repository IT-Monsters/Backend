package com.example.itsquad.repository;

import com.example.itsquad.domain.Comment;
import com.example.itsquad.domain.SubComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubCommentRepository extends JpaRepository<SubComment, Long> {

    Optional<SubComment> findById(Long id);

    Optional<SubComment> findByCommentId(Long CommentId);

    Optional<List<SubComment>> findAllByCommentId(Long CommentId);

}
