package com.example.itmonster.controller.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompletedQuestDto {
	private Long questId;
	private String questTitle;
}
