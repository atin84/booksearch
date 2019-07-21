package com.atin.searchweb.book.service;

import com.atin.searchweb.book.domain.BookSearchValue;
import com.atin.searchweb.book.dto.BookDocumentDto;
import com.atin.searchweb.book.dto.BookDto;
import com.atin.searchweb.book.dto.SearchRequestDto;
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

	public Page<BookDocumentDto> fetchBooks(SearchRequestDto searchRequestDto) {
		bookSearchHistoryService.addBookSearchHistory(searchRequestDto.getId(), BookSearchValue.builder().searchTime(LocalDateTime.now()).keyword(searchRequestDto.getTitle()).build());

		//
		Pageable pageable = new PageRequest(searchRequestDto.getPage() - 1, searchRequestDto.getSize());
		if (StringUtils.isBlank(searchRequestDto.getTitle())) {
			return new PageImpl<>(Collections.emptyList(), pageable, 0);
		}

		Response<BookDto> response;
		try {
			response = kakaoApiService.getBooks(searchRequestDto.getTitle(), searchRequestDto.getPage(), searchRequestDto.getSize()).execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		if (!response.isSuccessful()) {
			throw new RuntimeException(response.message());
		}

		return new PageImpl<>(response.body().getDocuments(), pageable, response.body().getMeta().getPageableCount());
	}

}
