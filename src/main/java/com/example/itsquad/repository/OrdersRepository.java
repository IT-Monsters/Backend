package com.example.itsquad.repository;

import com.example.itsquad.domain.Member;
import com.example.itsquad.domain.Offer;
import com.example.itsquad.domain.Quest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Offer, Long> {


    Optional<Offer> findByFromMemberAndToMemberAndQuest(Member fromMember, Member toMember, Quest quest);


    List<Offer> findAllByToMemberOrFromMember(Member toMember , Member fromMember);
}
