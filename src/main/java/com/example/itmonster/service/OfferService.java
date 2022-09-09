package com.example.itmonster.service;

import com.example.itmonster.controller.response.OfferResponseDto;
import com.example.itmonster.domain.Member;
import com.example.itmonster.domain.Offer;
import com.example.itmonster.domain.Offer.ClassType;
import com.example.itmonster.domain.Quest;
import com.example.itmonster.exceptionHandler.CustomException;
import com.example.itmonster.exceptionHandler.ErrorCode;
import com.example.itmonster.repository.OfferRepository;
import com.example.itmonster.repository.QuestRepository;
import com.example.itmonster.security.UserDetailsImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final QuestRepository questRepository;

    // 합류 요청 생성
    @Transactional
    public boolean createOffer(Long questId, ClassType classType, UserDetailsImpl userDetails) {

        Quest quest = questRepository.findById( questId ).orElseThrow(
            () -> new CustomException( ErrorCode.QUEST_NOT_FOUND )   // 에러 : 존재하지 않는 퀘스트
        );

        Member offeredMember = userDetails.getMember();
        Member questOwner = quest.getMember();

        if(Objects.equals(offeredMember.getId() , questOwner.getId() )) {
            throw new CustomException( ErrorCode.INVALID_OFFER_REQUEST );  // 에러 : 게시글 주인과 요청자가 다를 경우
        }

        Optional<Offer> offer = offerRepository.findByOfferedMemberAndQuest( offeredMember , quest );

        if( offer.isPresent() ) throw new CustomException( ErrorCode.OFFER_CONFLICT );  // 에러 : 오더가 이미 존재할 경우

        chkClassRecruitment( classType , quest );

        offerRepository.save(
            Offer.builder()
                .offeredMember( offeredMember )
                .classType( classType )
                .quest( quest )
                .build()
        );
        return true;
    }

    // 회원(게시글 주인)의 현재 들어온 합류요청 목록
    @Transactional( readOnly = true )
    public List<OfferResponseDto> getOfferList( Member questOwner ) {

        List<Quest> quests = questRepository.findAllByMember( questOwner );

        List<Offer> offers = offerRepository.findAllByQuestIn( quests );
        List<OfferResponseDto> OfferResponseDtos = new ArrayList<>();

        offers.forEach( offer -> OfferResponseDtos.add (new OfferResponseDto( offer )) );

        return OfferResponseDtos;
    }

    // '거절' 누를 시 합류요청 삭제
    @Transactional
    public Boolean deleteOffer(Long offerId, UserDetailsImpl userDetails) {


        Offer offer = offerRepository.findById( offerId ).orElseThrow(
            () -> new CustomException( ErrorCode.OFFER_NOT_FOUND )
        );

        offerRepository.delete( offer );

        return true;
    }

    public void chkClassRecruitment( ClassType classType , Quest quest ) {
        if ((classType == ClassType.FRONTEND && quest.getFrontend() < 1)
            || (classType == ClassType.BACKEND && quest.getBackend() < 1)
            || (classType == ClassType.FULLSTACK && quest.getFullstack() < 1)
            || (classType == ClassType.DESIGNER && quest.getDesigner() < 1 ) )
            throw new CustomException(ErrorCode.NO_RECRUITMENT);
    }



}

//    // 오더( 요청 ) 상세보기
//    public OrderDetailsDto getOrderDetails(Long orderId, UserDetailsImpl userDetails ) {
//
//        Orders order = ordersRepository.findById( orderId ). orElseThrow(
//            () -> new CustomException( ErrorCode.ORDER_NOT_FOUND )  //에러 : 오더가 존재하지 않을 경우
//        );
//
//        return new OrderDetailsDto( order );
//    }
//
//    // 현재 로그인한 회원이 게시글 작성자와 같은지 확인
//    public boolean isMyQuest( Orders order , Member member ){
//        if(Objects.equals(order.getQuest().getMember().getId(), member.getId())) return true;
//        return false;
//    }

