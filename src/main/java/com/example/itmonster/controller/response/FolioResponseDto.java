package com.example.itmonster.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FolioResponseDto {
	private String nickname;
	private String title;
	private String notionUrl;
	private String githubUrl;
	private String blogUrl;

}
