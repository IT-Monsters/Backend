package com.example.itmonster.repository;

import com.example.itmonster.domain.Folio;
import com.example.itmonster.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolioRepository extends JpaRepository<Folio, Long> {
	Folio findByMemberId(Long memberId);

}
