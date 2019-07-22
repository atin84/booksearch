package com.atin.searchweb.book.controller;

import com.atin.searchweb.book.dto.BookDto;
import com.atin.searchweb.book.dto.KakaoBookDto;
import com.atin.searchweb.book.dto.BookSearchValueDto;
import com.atin.searchweb.book.dto.BookSearchRequestDto;
import com.atin.searchweb.book.service.BookSearchHistoryService;
import com.atin.searchweb.book.service.BookSearchService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1")
@Controller
@RequiredArgsConstructor
public class BookSearchController {

	private final BookSearchService bookSearchService;

	private final BookSearchHistoryService bookSearchHistoryService;

	private final ModelMapper modelMapper;

	@GetMapping("/books")
	@ResponseBody
	public Page<BookDto> searchBook(@RequestParam("search[value]") String searchValue,
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
				.collect(Collectors.toList());

		return bookSearchValueList;
	}


}
