package com.example.itmonster.controller;

import com.example.itmonster.controller.response.OfferResponseDto;
import com.example.itmonster.security.UserDetailsImpl;
import com.example.itmonster.service.OfferService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    // 퀘스트 합류 요청
    @PostMapping("/quests/{questId}/offers")
    public ResponseEntity<Boolean> createOffer( @PathVariable Long questId,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok( offerService.createOffer( questId, userDetails ));
    }

    // 회원(게시글 주인)의 현재 들어온 합류요청 목록
    @GetMapping("/offers")
    public ResponseEntity<List<OfferResponseDto>> getOfferList( @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok( offerService.getOfferList( userDetails.getMember() ) );
    }

    // '거절' 누를 시 합류요청 삭제
    @DeleteMapping("/offers/{offerId}")
    public ResponseEntity<Boolean> deleteOffer( @PathVariable Long offerId,
        @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok( offerService.deleteOffer( offerId, userDetails ));

    }


//    @GetMapping("/quests/order/{orderId}")
//    public ResponseEntity<OrderDetailsDto> getOrderDetails( @PathVariable Long orderId,
//                                    @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return ResponseEntity.ok( offerService.getOrderDetails( orderId , userDetails ) );
//    }

}
