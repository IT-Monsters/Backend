package com.example.itmonster.controller.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class RecentQuestResponseDto {

	private Long recentQuestId;
	private String title;
	private String nickname;
	private String content;
	private Long duration;
	private boolean status;
	private ClassDto classes;
	private Long bookmarkCnt;
	private Long commentCnt; //<댓글 기능 추가 후>
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private List<String> stacks;

}
