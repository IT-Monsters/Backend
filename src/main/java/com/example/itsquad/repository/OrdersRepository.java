package com.example.itsquad.repository;

import com.example.itsquad.domain.Member;
import com.example.itsquad.domain.Orders;
import com.example.itsquad.domain.Quest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {


    Optional<Orders> findByFromMemberAndToMemberAndQuest(Member fromMember, Member toMember, Quest quest);


    List<Orders> findAllByToMemberOrFromMember(Member toMember , Member fromMember);
}
