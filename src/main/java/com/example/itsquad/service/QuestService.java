package com.example.itsquad.service;

import com.example.itsquad.controller.request.QuestRequestDto;
import com.example.itsquad.controller.response.QuestResponseDto;
import com.example.itsquad.domain.Folio;
import com.example.itsquad.domain.Member;
import com.example.itsquad.domain.Quest;
import com.example.itsquad.exceptionHandler.CustomException;
import com.example.itsquad.exceptionHandler.ErrorCode;
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
    public boolean createQuest(QuestRequestDto questRequestDto, UserDetailsImpl userDetails) {
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

        return true;
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
        Quest quest = validateQuest(questId);
        return new QuestResponseDto(quest);
    }

    @Transactional // 게시글 수정 // 기술스택 추가해야됨 !!
    public boolean updateQuest(Long questId, QuestRequestDto questRequestDto,
        UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        Quest quest = validateQuest(questId);
        if (validateAuthor(member, quest)) {
            quest.updateQuest(questRequestDto.getTitle(), questRequestDto.getContent(),
                questRequestDto.getType(), questRequestDto.getPosition(),
                questRequestDto.getMinPrice(), questRequestDto.getMaxPrice(),
                questRequestDto.getExpiredDate());
        }
        return true;
    }

    @Transactional // 게시글 삭제
    public boolean deleteQuest(Long questId, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        Quest quest = validateQuest(questId);
        if (validateAuthor(member, quest)) {
            questRepository.deleteById(questId);
        }
        return true;
    }

    public Quest validateQuest(Long questId) {
        return questRepository.findById(questId)
            .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    public boolean validateAuthor(Member member, Quest quest) { // 수정,삭제 권한 확인(글쓴이인지 확인)
        if (!member.getId().equals(quest.getMember().getId())) {
            throw new CustomException(ErrorCode.INVALID_AUTHORITY);
        }
        return true;
    }
}
