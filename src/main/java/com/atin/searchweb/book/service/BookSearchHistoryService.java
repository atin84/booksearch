package com.atin.searchweb.book.service;

import com.atin.searchweb.book.domain.BookSearchValue;

import java.util.List;

public interface BookSearchHistoryService {

	List<BookSearchValue> fetchBookSearchHistory(String id);

	void addBookSearchHistory(String id, BookSearchValue bookSearchValue);

}
