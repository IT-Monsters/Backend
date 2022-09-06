package com.example.itmonster.repository;

import com.example.itmonster.domain.StackOfMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StackOfMemberRepository extends JpaRepository<StackOfMember,Long> {

    List<StackOfMember> findByMemberId(Long memberId);

    boolean existsByMemberIdAndStackName(Long memberId,String stackName);
}
