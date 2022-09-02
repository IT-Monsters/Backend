package com.example.itsquad.repository;

import com.example.itsquad.domain.Bookmark;
import com.example.itsquad.domain.Member;
import com.example.itsquad.domain.Quest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Long countAllByQuest(Quest quest);
    boolean existsByMarkedMemberAndQuest(Member member, Quest quest);
    void deleteByMarkedMemberAndQuest(Member member, Quest quest);

}
