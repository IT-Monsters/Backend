package com.example.itmonster.repository;

import com.example.itmonster.domain.Member;
import com.example.itmonster.domain.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {

    Optional<Member> findById(Long id);

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByEmail(String email);

    Optional<Member> findBySocialId(String socialId);

    List<Member> findAllByNicknameContaining(String nickname);

    List<Member> findTop3ByOrderByFollowCounter();

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
