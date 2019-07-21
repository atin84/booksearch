package com.atin.searchweb.book.controller;

import com.atin.searchweb.book.domain.BookSearchHistory;
import com.atin.searchweb.book.dto.BookDocumentDto;
import com.atin.searchweb.book.dto.SearchRequestDto;
import com.atin.searchweb.book.service.BookSearchHistoryService;
import com.atin.searchweb.book.service.BookSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class BookSearchController {

	private final BookSearchService bookSearchService;

	private final BookSearchHistoryService bookSearchHistoryService;

	/*@GetMapping("/book-search")
	public String searchBook(Model model, SearchRequestDto searchRequestDto, Pageable pageable) {

		Page<BookDocumentDto> bookPage = bookSearchService.fetchBooks(searchRequestDto, pageable);

		model.addAttribute("searchRequest", searchRequestDto);
		model.addAttribute("bookPage", bookSearchService.fetchBooks(searchRequestDto, pageable));

		int totalPages = bookPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		return "main";
	}*/

	@GetMapping("/api/v1/books")
	@ResponseBody
	public Page<BookDocumentDto> searchBook(@RequestParam("search[value]") String searchValue,
											@RequestParam(value = "start", defaultValue = "0") int start,
											@RequestParam(value = "length", defaultValue = "10") int length,
											HttpServletRequest request) {

		SearchRequestDto searchRequestDto = new SearchRequestDto();
		searchRequestDto.setTitle(searchValue);
		searchRequestDto.setSize(length);
		searchRequestDto.setPage((start / length) + 1);
		searchRequestDto.setId(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

		return bookSearchService.fetchBooks(searchRequestDto);
	}

	@GetMapping("/api/v1/histories")
	@ResponseBody
	public BookSearchHistory getHistories(Principal principal) {
		principal.getName();
		String id = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		return bookSearchHistoryService.fetchBookSearchHistory(id);
	}
}
