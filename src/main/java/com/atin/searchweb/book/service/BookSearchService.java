package com.atin.searchweb.book.service;

import com.atin.searchweb.book.dto.BookDto;
import com.atin.searchweb.book.dto.BookSearchRequestDto;
import org.springframework.data.domain.Page;

public interface BookSearchService {

	Page<BookDto> searchBooks(BookSearchRequestDto bookSearchRequestDto);

}
