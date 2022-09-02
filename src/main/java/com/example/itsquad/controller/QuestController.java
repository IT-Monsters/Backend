package com.example.itsquad.controller;

import com.example.itsquad.controller.request.QuestRequestDto;
import com.example.itsquad.controller.response.QuestResponseDto;
import com.example.itsquad.security.UserDetailsImpl;
import com.example.itsquad.service.QuestService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/quests")
public class QuestController {

    private final QuestService questService;

    @PostMapping("")
    public ResponseEntity<Boolean> createQuest(@RequestBody QuestRequestDto questRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(questService.createQuest(questRequestDto, userDetails));
    }

    @GetMapping("")
    public ResponseEntity<List<QuestResponseDto>> readAllQuest(){
        return ResponseEntity.ok(questService.readAllQuest());
    }

    @GetMapping("/main")
    public ResponseEntity<List<QuestResponseDto>> readTop3Quest(){
        return ResponseEntity.ok(questService.readTop3Quest());
    }

    @GetMapping("/{questId}")
    public ResponseEntity<QuestResponseDto> readQuest(@PathVariable Long questId){
        return ResponseEntity.ok(questService.readQuest(questId));
    }

    @PutMapping("/{questId}")
    public ResponseEntity<Boolean> updateQuest(@PathVariable Long questId,
        @RequestBody QuestRequestDto questRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(questService.updateQuest(questId, questRequestDto, userDetails));
    }

    @DeleteMapping("/{questId}")
    public ResponseEntity<Boolean> deleteQuest(@PathVariable Long questId,
        @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(questService.deleteQuest(questId, userDetails));
    }

    @GetMapping("/search")
    public ResponseEntity<List<QuestResponseDto>> searchQuests( @RequestParam MultiValueMap<String, String> allParameters ){

        return ResponseEntity.ok( questService.searchQuests( allParameters ) );
    }
}
