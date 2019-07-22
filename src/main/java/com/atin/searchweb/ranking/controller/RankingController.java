package com.atin.searchweb.ranking.controller;

import com.atin.searchweb.ranking.dto.BookRankingDto;
import com.atin.searchweb.ranking.service.BookRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/api/v1")
@Controller
@RequiredArgsConstructor
public class RankingController {

	private final BookRankingService bookRankingService;

	@GetMapping("/ranking")
	@ResponseBody
	public List<BookRankingDto> getHistories() {
		return bookRankingService.getBookSearchRank();
	}
}
