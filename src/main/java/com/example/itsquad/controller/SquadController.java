package com.example.itsquad.controller;

import com.example.itsquad.controller.response.OfferResponseDto;
import com.example.itsquad.controller.response.SquadResponseDto;
import com.example.itsquad.domain.Squad;
import com.example.itsquad.repository.SquadRepository;
import com.example.itsquad.security.UserDetailsImpl;
import com.example.itsquad.service.SquadService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SquadController {

    private final SquadService squadService;

    // 스쿼드에 멤버 추가
    @PostMapping("/squads/{offerId}")
    public ResponseEntity<Boolean> addSquadMember( @PathVariable Long offerId , @AuthenticationPrincipal UserDetailsImpl userDetails ){
        return ResponseEntity.ok( squadService.addSquadMember( offerId , userDetails.getMember() ) );
    }

    // 퀘스트에 합류된 스쿼드 멤버들 리스트 불러오기
    @GetMapping("/squads/{questId}")
    public ResponseEntity<List<SquadResponseDto>> getSquadMembersByQuest( @PathVariable Long questId ){
        return ResponseEntity.ok( squadService.getSquadMembersByQuest( questId ) );
    }

    // 내가 소속한 스쿼드 불러오기
    @GetMapping("/squads")
    public ResponseEntity<List<SquadResponseDto>> getMySquads( @AuthenticationPrincipal UserDetailsImpl userDetails ){
        return ResponseEntity.ok( squadService.getMySquads( userDetails.getMember() ) );
    }

    // 스쿼드에 멤버 삭제
    @DeleteMapping("/squads/{squadId}")
    public ResponseEntity<Boolean> deleteSquadMember( @PathVariable Long squadId ){
        return ResponseEntity.ok( squadService.deleteSquadMember( squadId ) );
    }

}
