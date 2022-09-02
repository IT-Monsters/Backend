package com.example.itsquad.controller;

import com.example.itsquad.controller.response.OrderDetailsDto;
import com.example.itsquad.controller.response.OrderResponseDto;
import com.example.itsquad.security.UserDetailsImpl;
import com.example.itsquad.service.OrdersService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    // 오더 요청
    @PostMapping("/quests/{questId}/order")
    public ResponseEntity<Boolean> createOrder( @PathVariable Long questId,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok( ordersService.createOrder( questId, userDetails ));
    }

    // 회원의 현재 외주/수주 오더 목록
    @GetMapping("/quests/order")
    public ResponseEntity<List<OrderResponseDto>> getOrderList( @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok( ordersService.getOrderList( userDetails.getMember() ) );
    }

    // 오더( 요청 ) 상세보기
    @GetMapping("/quests/order/{orderId}")
    public ResponseEntity<OrderDetailsDto> getOrderDetails( @PathVariable Long orderId,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok( ordersService.getOrderDetails( orderId , userDetails ) );
    }

}
