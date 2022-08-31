package com.example.itsquad.controller;

import com.example.itsquad.controller.request.QuestRequestDto;
import com.example.itsquad.controller.response.QuestResponseDto;
import com.example.itsquad.security.UserDetailsImpl;
import com.example.itsquad.service.QuestService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/quests")
public class QuestController {

    private final QuestService questService;

    @PostMapping("")
    public void createQuest(@RequestBody QuestRequestDto questRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        questService.createQuest(questRequestDto, userDetails);
    }

    @GetMapping("")
    public List<QuestResponseDto> readAllQuest(){
        return questService.readAllQuest();
    }

    @GetMapping("/main")
    public List<QuestResponseDto> readTop3Quest(){
        return questService.readTop3Quest();
    }

    @GetMapping("/{questId}")
    public QuestResponseDto readQuest(@PathVariable Long questId){
        return questService.readQuest(questId);
    }

    @PutMapping("/{questId}")
    public void updateQuest(@PathVariable Long questId, @RequestBody QuestRequestDto questRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails){
        questService.updateQuest(questId, questRequestDto, userDetails);
    }

    @DeleteMapping("/{questId}")
    public void deleteQuest(@PathVariable Long questId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        questService.deleteQuest(questId, userDetails);
    }
}
