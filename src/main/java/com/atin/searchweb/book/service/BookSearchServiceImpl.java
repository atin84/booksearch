package com.atin.searchweb.book.service;

import com.atin.searchweb.book.domain.BookSearchValue;
import com.atin.searchweb.book.dto.BookDto;
import com.atin.searchweb.book.dto.BookSearchRequestDto;
import com.atin.searchweb.book.dto.KakaoBooksDto;
import com.atin.searchweb.book.dto.NaverBooksDto;
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
public class BookSearchServiceImpl implements BookSearchService {

	private final KakaoBookApiService kakaoApiService;

	private final NaverBookApiService naverApiService;

	private final BookSearchHistoryService bookSearchHistoryService;

	private final BookSearchRankingService bookSearchRankingService;

	private final ModelMapper modelMapper;

	@Override
	@HystrixCommand(fallbackMethod = "getFallback")
	public Page<BookDto> searchBooks(BookSearchRequestDto bookSearchRequestDto) {
		Pageable pageable = new PageRequest(bookSearchRequestDto.getPage() - 1, bookSearchRequestDto.getSize());
		if (StringUtils.isBlank(bookSearchRequestDto.getTitle())) {
			return new PageImpl<>(Collections.emptyList(), pageable, 0);
		}

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

		postProcess(bookSearchRequestDto);

		return new PageImpl<>(bookDtoList, pageable, response.body().getMeta().getPageableCount());
	}

	@SuppressWarnings("unused")
	public Page<BookDto> getFallback(BookSearchRequestDto bookSearchRequestDto) {
		Pageable pageable = new PageRequest(bookSearchRequestDto.getPage() - 1, bookSearchRequestDto.getSize());
		if (StringUtils.isBlank(bookSearchRequestDto.getTitle())) {
			return new PageImpl<>(Collections.emptyList(), pageable, 0);
		}

		Response<NaverBooksDto> response;
		try {
			int start = (bookSearchRequestDto.getPage() - 1) * bookSearchRequestDto.getSize() + 1;
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

		postProcess(bookSearchRequestDto);

		return new PageImpl<>(bookDtoList, pageable, response.body().getTotal());
	}

	private void postProcess(BookSearchRequestDto bookSearchRequestDto) {
		bookSearchHistoryService.addBookSearchHistory(bookSearchRequestDto.getId(), BookSearchValue.builder().searchTime(LocalDateTime.now()).keyword(bookSearchRequestDto.getTitle()).build());
		bookSearchRankingService.incrementBookSearchScore(bookSearchRequestDto.getTitle());
	}

}
