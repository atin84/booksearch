package com.atin.searchweb.book.service;

import com.atin.searchweb.book.dto.BookRankingDto;

import java.util.List;

public interface BookSearchRankingService {

	void incrementBookSearchScore(String keyword);

	List<BookRankingDto> getBookSearchRank();
}
