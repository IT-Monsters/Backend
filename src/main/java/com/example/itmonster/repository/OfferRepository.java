package com.example.itmonster.repository;

import com.example.itmonster.domain.Member;
import com.example.itmonster.domain.Offer;
import com.example.itmonster.domain.Quest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {


    Optional<Offer> findByOfferedMemberAndQuest(Member offeredMember, Quest quest);

    List<Offer> findAllByQuestIn(List<Quest> quests);
}
