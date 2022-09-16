package com.example.itmonster.service;

import com.example.itmonster.controller.request.FolioRequestDto;
import com.example.itmonster.controller.response.FolioResponseDto;
import com.example.itmonster.domain.Folio;
import com.example.itmonster.domain.Member;
import com.example.itmonster.repository.FolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FolioService {

	private final FolioRepository folioRepository;

	@Transactional
	public FolioResponseDto updateFolio(FolioRequestDto folioRequestDto, Member member) {
		Folio folio = folioRepository.findByMemberId(member.getId());
		folio.updateFolio(folioRequestDto.getTitle(), folioRequestDto.getNotionUrl(),
			folioRequestDto.getGithubUrl(), folioRequestDto.getBlogUrl());
		folioRepository.save(folio);

		FolioResponseDto response = FolioResponseDto.builder()
			.nickname(member.getNickname())
			.title(folio.getTitle())
			.blogUrl(folio.getBlogUrl())
			.notionUrl(folio.getNotionUrl())
			.githubUrl(folio.getGithubUrl())
			.build();

		return response;
	}


}
