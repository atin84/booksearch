package com.atin.searchweb.book.service;

import com.atin.searchweb.book.domain.BookSearchValue;
import com.atin.searchweb.book.dto.BookDocumentDto;
import com.atin.searchweb.book.dto.BookDto;
import com.atin.searchweb.book.dto.BookSearchRequestDto;
import com.atin.searchweb.ranking.service.BookRankingService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class BookSearchService {

	private final KakaoApiService kakaoApiService;

	private final BookSearchHistoryService bookSearchHistoryService;

	private final BookRankingService bookRankingRepository;

	public Page<BookDocumentDto> searchBooks(BookSearchRequestDto bookSearchRequestDto) {
		Pageable pageable = new PageRequest(bookSearchRequestDto.getPage() - 1, bookSearchRequestDto.getSize());
		if (StringUtils.isBlank(bookSearchRequestDto.getTitle())) {
			return new PageImpl<>(Collections.emptyList(), pageable, 0);
		}

		bookSearchHistoryService.addBookSearchHistory(bookSearchRequestDto.getId(), BookSearchValue.builder().searchTime(LocalDateTime.now()).keyword(bookSearchRequestDto.getTitle()).build());
		bookRankingRepository.incrementBookSearchScore(bookSearchRequestDto.getTitle());

		Response<BookDto> response;
		try {
			response = kakaoApiService.getBooks(bookSearchRequestDto.getTitle(), bookSearchRequestDto.getPage(), bookSearchRequestDto.getSize()).execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		if (!response.isSuccessful()) {
			throw new RuntimeException(response.message());
		}

		return new PageImpl<>(response.body().getDocuments(), pageable, response.body().getMeta().getPageableCount());
	}

}
