package com.example.itmonster.controller;

import com.example.itmonster.controller.request.QuestRequestDto;
import com.example.itmonster.controller.response.MainQuestResponseDto;
import com.example.itmonster.controller.response.QuestResponseDto;
import com.example.itmonster.controller.response.RecentQuestResponseDto;
import com.example.itmonster.security.UserDetailsImpl;
import com.example.itmonster.service.QuestService;
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
    public ResponseEntity<List<MainQuestResponseDto>> readFavorite3Quest(){
        return ResponseEntity.ok(questService.readFavorite3Quest());
    }

    @GetMapping("/recent")
    public ResponseEntity<List<RecentQuestResponseDto>> readRecent3Quest(){
        return ResponseEntity.ok(questService.readRecent3Quest());
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

    @PostMapping("/{questId}/bookmark")
    public ResponseEntity<Boolean> bookmarkQuest(@PathVariable Long questId,
        @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(questService.bookmarkQuest(questId, userDetails));
    }

    @GetMapping("/search")
    public ResponseEntity<List<QuestResponseDto>> searchQuests( @RequestParam MultiValueMap<String, String> allParameters ){

        return ResponseEntity.ok( questService.searchQuests( allParameters ) );
    }
}
