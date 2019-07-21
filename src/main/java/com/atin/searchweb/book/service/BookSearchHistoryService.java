package com.atin.searchweb.book.service;

import com.atin.searchweb.book.domain.BookSearchHistory;
import com.atin.searchweb.book.domain.BookSearchValue;
import com.atin.searchweb.book.repository.BookSearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class BookSearchHistoryService {

	private final BookSearchHistoryRepository bookSearchHistoryRepository;

	public BookSearchHistory fetchBookSearchHistory(String id) {
		Optional<BookSearchHistory> bookSearchHistoryOptional = bookSearchHistoryRepository.findById(id);

		if (!bookSearchHistoryOptional.isPresent()) {
			BookSearchHistory bookSearchHistory = new BookSearchHistory();
			bookSearchHistory.setId(id);
			bookSearchHistory.setBookSearchHistorySet(Collections.emptySet());
			return bookSearchHistory;
		}
		return bookSearchHistoryOptional.get();
	}

	public void addBookSearchHistory(String id, BookSearchValue bookSearchValue) {
		Set<BookSearchValue> bookSearchValues;

		Optional<BookSearchHistory> bookSearchHistoryOptional = bookSearchHistoryRepository.findById(id);
		if (bookSearchHistoryOptional.isPresent()) {
			bookSearchValues = bookSearchHistoryOptional.get().getBookSearchHistorySet();
			bookSearchValues.remove(bookSearchValue);
		} else {
			bookSearchValues = new LinkedHashSet<>();
		}

		bookSearchValues.add(bookSearchValue);

		BookSearchHistory bookSearchHistory;
		if (bookSearchHistoryOptional.isPresent()) {
			bookSearchHistory = bookSearchHistoryOptional.get();
		} else {
			bookSearchHistory = new BookSearchHistory();
			bookSearchHistory.setId(id);
		}

		bookSearchHistory.setBookSearchHistorySet(bookSearchValues);
		bookSearchHistoryRepository.save(bookSearchHistory);
	}

}
