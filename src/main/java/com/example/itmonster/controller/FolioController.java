package com.example.itmonster.controller;

import com.example.itmonster.controller.request.FolioRequestDto;
import com.example.itmonster.controller.response.FolioResponseDto;
import com.example.itmonster.security.UserDetailsImpl;
import com.example.itmonster.service.FolioService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FolioController {

	private FolioService folioService;


	@PutMapping("/api/folio}")
	public ResponseEntity<FolioResponseDto> updateFolio(@RequestBody FolioRequestDto requestDto,
										@AuthenticationPrincipal UserDetailsImpl userDetails){

		return ResponseEntity.ok(folioService.updateFolio(requestDto,userDetails.getMember()));
	}



}
