package com.example.itsquad.service;

import com.example.itsquad.controller.request.QuestRequestDto;
import com.example.itsquad.controller.response.QuestResponseDto;
import com.example.itsquad.domain.Folio;
import com.example.itsquad.domain.Member;
import com.example.itsquad.domain.Quest;
import com.example.itsquad.repository.FolioRepository;
import com.example.itsquad.repository.QuestRepository;
import com.example.itsquad.security.UserDetailsImpl;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestService {

    private final QuestRepository questRepository;
    private final FolioRepository folioRepository;

    @Transactional // 게시글 작성 // 기술스택 추가해야됨 !!
    public void createQuest(QuestRequestDto questRequestDto, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        questRepository.save(Quest.builder()
            .member(member)
            .title(questRequestDto.getTitle())
            .content(questRequestDto.getContent())
            .type(questRequestDto.getType())
            .position(questRequestDto.getPosition())
            .minPrice(questRequestDto.getMinPrice())
            .maxPrice(questRequestDto.getMaxPrice())
            .status(false)
            .expiredDate(questRequestDto.getExpiredDate())
            .build());

        // 빈 포트폴리오 생성
        folioRepository.save(Folio.builder()
            .title(member.getNickname() + "님의 포트폴리오입니다.")
            .member(member)
            .build());
    }

    @Transactional(readOnly = true) // 모든 게시글 최신순 조회 // 기술스택 추가해야됨 !!
    public List<QuestResponseDto> readAllQuest() {
        List<Quest> quests = questRepository.findAllByOrderByModifiedAtDesc();
        return quests.stream().map(QuestResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true) // 메인페이지용 게시글 최신순 3개 조회 // 기술스택 추가해야됨 !!
    public List<QuestResponseDto> readTop3Quest() {
        List<Quest> quests = questRepository.findTop3ByOrderByModifiedAtDesc();
        return quests.stream().map(QuestResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true) // 게시글 상세 조회 // 댓글조회, 기술스택 추가해야됨 !!
    public QuestResponseDto readQuest(Long questId) {
        Quest quest = questRepository.findById(questId)
            .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다"));
        return new QuestResponseDto(quest);
    }

    @Transactional // 게시글 수정 // 기술스택 추가해야됨 !!
    public void updateQuest(Long questId, QuestRequestDto questRequestDto,
        UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        Quest quest = questRepository.findById(questId)
            .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다"));
        if (!member.getId().equals(quest.getMember().getId())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        quest.updateQuest(questRequestDto.getTitle(), questRequestDto.getContent(),
            questRequestDto.getType(), questRequestDto.getPosition(),
            questRequestDto.getMinPrice(), questRequestDto.getMaxPrice(), questRequestDto.getExpiredDate());
    }

    @Transactional // 게시글 삭제
    public void deleteQuest(Long questId, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        Quest quest = questRepository.findById(questId)
            .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다"));
        if (!member.getId().equals(quest.getMember().getId())) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
        questRepository.deleteById(questId);
    }
}
