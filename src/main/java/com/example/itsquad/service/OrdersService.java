package com.example.itsquad.service;

import com.example.itsquad.controller.response.OrderDetailsDto;
import com.example.itsquad.controller.response.OrderResponseDto;
import com.example.itsquad.domain.Member;
import com.example.itsquad.domain.Offer;
import com.example.itsquad.domain.Offer.StatusEnum;
import com.example.itsquad.domain.Quest;
import com.example.itsquad.exceptionHandler.CustomException;
import com.example.itsquad.exceptionHandler.ErrorCode;
import com.example.itsquad.repository.OrdersRepository;
import com.example.itsquad.repository.QuestRepository;
import com.example.itsquad.security.UserDetailsImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final QuestRepository questRepository;

    // 오더 생성
    @Transactional
    public boolean createOrder(Long questId, UserDetailsImpl userDetails) {

        Quest quest = questRepository.findById( questId ).orElseThrow(
            () -> new CustomException( ErrorCode.POST_NOT_FOUND )   // 에러 : 존재하지 않는 퀘스트
        );
        Member fromMember = userDetails.getMember();
        Member toMember = quest.getMember();

        if(Objects.equals(fromMember.getId() , toMember.getId() )) {
            throw new CustomException( ErrorCode.INVALID_ORDER_REQUEST );  // 에러 : 게시글 주인과 요청자가 같을 경우
        }

        Optional<Offer> order = ordersRepository.findByFromMemberAndToMemberAndQuest( fromMember, toMember , quest );

        if( order.isPresent() ) throw new CustomException( ErrorCode.ORDER_CONFLICT );  // 에러 : 오더가 이미 존재할 경우

        ordersRepository.save(
            Offer.builder()
                .fromMember( fromMember )
                .toMember( toMember )
                .quest( quest )
                .orderStatusToMember( false )
                .orderStatusFromMember( false )
                .statusEnum( StatusEnum.PROCESSING )
                .build()
        );

        return true;
    }

    // 회원의 현재 외주/수주 오더 목록
    public List<OrderResponseDto> getOrderList( Member member ) {

        List<Offer> orderPS = ordersRepository.findAllByToMemberOrFromMember( member , member );
        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();

        orderPS.forEach(order -> orderResponseDtos.add( new OrderResponseDto( order , isMyQuest( order , member ) )));

        return orderResponseDtos;
    }

    // 오더( 요청 ) 상세보기
    public OrderDetailsDto getOrderDetails(Long orderId, UserDetailsImpl userDetails ) {

        Offer order = ordersRepository.findById( orderId ). orElseThrow(
            () -> new CustomException( ErrorCode.ORDER_NOT_FOUND )  //에러 : 오더가 존재하지 않을 경우
        );

        return new OrderDetailsDto( order );
    }

    // 현재 로그인한 회원이 게시글 작성자와 같은지 확인
    public boolean isMyQuest(Offer order , Member member ){
        if(Objects.equals(order.getQuest().getMember().getId(), member.getId())) return true;
        return false;
    }


}
