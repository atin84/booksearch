package com.atin.searchweb.book.service;

import com.atin.searchweb.book.domain.BookSearchValue;
import com.atin.searchweb.book.dto.*;
import com.atin.searchweb.ranking.service.BookRankingService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookSearchService {

	private final KakaoApiService kakaoApiService;

	private final NaverApiService naverApiService;

	private final BookSearchHistoryService bookSearchHistoryService;

	private final BookRankingService bookRankingRepository;

	private final ModelMapper modelMapper;

	@HystrixCommand(fallbackMethod = "getFallback")
	public Page<BookDto> searchBooks(BookSearchRequestDto bookSearchRequestDto) {
		Pageable pageable = new PageRequest(bookSearchRequestDto.getPage() - 1, bookSearchRequestDto.getSize());
		if (StringUtils.isBlank(bookSearchRequestDto.getTitle())) {
			return new PageImpl<>(Collections.emptyList(), pageable, 0);
		}

		bookSearchHistoryService.addBookSearchHistory(bookSearchRequestDto.getId(), BookSearchValue.builder().searchTime(LocalDateTime.now()).keyword(bookSearchRequestDto.getTitle()).build());
		bookRankingRepository.incrementBookSearchScore(bookSearchRequestDto.getTitle());

		Response<KakaoBooksDto> response;
		try {
			response = kakaoApiService.getBooks(bookSearchRequestDto.getTitle(), bookSearchRequestDto.getPage(), bookSearchRequestDto.getSize()).execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		if (!response.isSuccessful()) {
			throw new RuntimeException(response.message());
		}

		List<BookDto> bookDtoList = response.body()
				.getDocuments()
				.stream()
				.map(item -> modelMapper.map(item, BookDto.class))
				.collect(Collectors.toList());

		return new PageImpl<>(bookDtoList, pageable, response.body().getMeta().getPageableCount());
	}

	@SuppressWarnings("unused")
	public Page<BookDto> getFallback(BookSearchRequestDto bookSearchRequestDto) {
		Pageable pageable = new PageRequest(bookSearchRequestDto.getPage() - 1, bookSearchRequestDto.getSize());
		if (StringUtils.isBlank(bookSearchRequestDto.getTitle())) {
			return new PageImpl<>(Collections.emptyList(), pageable, 0);
		}

		bookSearchHistoryService.addBookSearchHistory(bookSearchRequestDto.getId(), BookSearchValue.builder().searchTime(LocalDateTime.now()).keyword(bookSearchRequestDto.getTitle()).build());
		bookRankingRepository.incrementBookSearchScore(bookSearchRequestDto.getTitle());

		Response<NaverBooksDto> response;
		try {
			int start = (bookSearchRequestDto.getPage() -1 ) * bookSearchRequestDto.getSize() + 1;
			response = naverApiService.getBooks(bookSearchRequestDto.getTitle(), start, bookSearchRequestDto.getSize()).execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		if (!response.isSuccessful()) {
			throw new RuntimeException(response.message());
		}

		List<BookDto> bookDtoList = response.body()
				.getItems()
				.stream()
				.map(item -> modelMapper.map(item, BookDto.class))
				.collect(Collectors.toList());

		return new PageImpl<>(bookDtoList, pageable, response.body().getTotal());
	}

}
