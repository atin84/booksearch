package com.atin.searchweb.book.controller;

import com.atin.searchweb.book.dto.BookDto;
import com.atin.searchweb.book.dto.BookRankingDto;
import com.atin.searchweb.book.dto.BookSearchRequestDto;
import com.atin.searchweb.book.dto.BookSearchValueDto;
import com.atin.searchweb.book.service.BookSearchHistoryService;
import com.atin.searchweb.book.service.BookSearchRankingService;
import com.atin.searchweb.book.service.BookSearchService;
import com.atin.searchweb.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Secured("ROLE_USER")
@RequestMapping("/api/v1")
@RestController
@Validated
@RequiredArgsConstructor
public class BookSearchController {

	private final BookSearchService bookSearchService;

	private final BookSearchHistoryService bookSearchHistoryService;

	private final BookSearchRankingService bookRankingService;

	private final ModelMapper modelMapper;

	@GetMapping("/books")
	@ResponseBody
	public ResponseDto<Page<BookDto>> searchBook(@RequestParam("search[value]") @NotBlank String searchValue,
												 @RequestParam(value = "start", defaultValue = "0") int start,
												 @RequestParam(value = "length", defaultValue = "10") int length,
												 Principal principal) {
		BookSearchRequestDto bookSearchRequestDto = new BookSearchRequestDto();
		bookSearchRequestDto.setTitle(searchValue);
		bookSearchRequestDto.setSize(length);
		bookSearchRequestDto.setPage((start / length) + 1);
		bookSearchRequestDto.setId(principal.getName());

		return ResponseDto.success(bookSearchService.searchBooks(bookSearchRequestDto));
	}

	@GetMapping("/histories")
	@ResponseBody
	public ResponseDto<List<BookSearchValueDto>> getHistories(Principal principal) {
		List<BookSearchValueDto> bookSearchValueList = bookSearchHistoryService.fetchBookSearchHistory(principal.getName())
				.stream()
				.map(v -> modelMapper.map(v, BookSearchValueDto.class))
				.collect(Collectors.toList());

		return ResponseDto.success(bookSearchValueList);
	}

	@GetMapping("/rankings")
	@ResponseBody
	public ResponseDto<List<BookRankingDto>> getHistories() {
		return ResponseDto.success(bookRankingService.getBookSearchRank());
	}

}
