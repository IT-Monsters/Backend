package com.example.itmonster.controller;

import com.example.itmonster.controller.request.FolioRequestDto;
import com.example.itmonster.controller.response.FolioResponseDto;
import com.example.itmonster.security.UserDetailsImpl;
import com.example.itmonster.service.FolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FolioController {

	private final FolioService folioService;


	@PutMapping("/api/folio")
	public ResponseEntity<FolioResponseDto> updateFolio(@RequestBody FolioRequestDto requestDto,
										@AuthenticationPrincipal UserDetailsImpl userDetails){
		FolioResponseDto responseDto = folioService.updateFolio(requestDto,userDetails.getMember());
		return ResponseEntity.ok(responseDto);
	}

}
