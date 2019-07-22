package com.atin.searchweb.book.controller;

import com.atin.searchweb.book.dto.BookDocumentDto;
import com.atin.searchweb.book.dto.BookSearchValueDto;
import com.atin.searchweb.book.dto.BookSearchRequestDto;
import com.atin.searchweb.book.service.BookSearchHistoryService;
import com.atin.searchweb.book.service.BookSearchService;
import com.atin.searchweb.ranking.service.BookRankingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/api/v1")
@Controller
@RequiredArgsConstructor
public class BookSearchController {

	private final BookSearchService bookSearchService;

	private final BookSearchHistoryService bookSearchHistoryService;

	private final BookRankingService bookRankingRepository;

	private final ModelMapper modelMapper;

	@GetMapping("/books")
	@ResponseBody
	public Page<BookDocumentDto> searchBook(@RequestParam("search[value]") String searchValue,
											@RequestParam(value = "start", defaultValue = "0") int start,
											@RequestParam(value = "length", defaultValue = "10") int length,
											Principal principal) {

		BookSearchRequestDto bookSearchRequestDto = new BookSearchRequestDto();
		bookSearchRequestDto.setTitle(searchValue);
		bookSearchRequestDto.setSize(length);
		bookSearchRequestDto.setPage((start / length) + 1);
		bookSearchRequestDto.setId(principal.getName());

		return bookSearchService.searchBooks(bookSearchRequestDto);
	}

	@GetMapping("/histories")
	@ResponseBody
	public List<BookSearchValueDto> getHistories(Principal principal) {
		List<BookSearchValueDto> bookSearchValueList = bookSearchHistoryService.fetchBookSearchHistory(principal.getName())
				.stream()
				.map(v -> modelMapper.map(v, BookSearchValueDto.class))
				.collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
					Collections.reverse(list);
					return list;
				}));

		return bookSearchValueList;
	}


}
