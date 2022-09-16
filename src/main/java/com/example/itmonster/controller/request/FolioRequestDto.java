package com.example.itmonster.controller.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolioRequestDto {
	private String title;
	private String notionUrl;
	private String githubUrl;
	private String blogUrl;

}
