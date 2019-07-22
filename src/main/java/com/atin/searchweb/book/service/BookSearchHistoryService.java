package com.atin.searchweb.book.service;

import com.atin.searchweb.book.domain.BookSearchHistory;
import com.atin.searchweb.book.domain.BookSearchValue;
import com.atin.searchweb.book.repository.BookSearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class BookSearchHistoryService {

	private final BookSearchHistoryRepository bookSearchHistoryRepository;

	public List<BookSearchValue> fetchBookSearchHistory(String id) {
		Optional<BookSearchHistory> bookSearchHistoryOptional = bookSearchHistoryRepository.findById(id);

		if (!bookSearchHistoryOptional.isPresent()) {
			return Collections.emptyList();
		}
		return bookSearchHistoryOptional.get().getBookSearchHistorySet();
	}

	public void addBookSearchHistory(String id, BookSearchValue bookSearchValue) {
		List<BookSearchValue> bookSearchValueSet;

		Optional<BookSearchHistory> bookSearchHistoryOptional = bookSearchHistoryRepository.findById(id);
		if (bookSearchHistoryOptional.isPresent()) {
			bookSearchValueSet = bookSearchHistoryOptional.get().getBookSearchHistorySet();
			bookSearchValueSet.remove(bookSearchValue);
		} else {
			bookSearchValueSet = new ArrayList<>();
		}

		bookSearchValueSet.add(bookSearchValue);

		BookSearchHistory bookSearchHistory;
		if (bookSearchHistoryOptional.isPresent()) {
			bookSearchHistory = bookSearchHistoryOptional.get();
		} else {
			bookSearchHistory = new BookSearchHistory();
			bookSearchHistory.setId(id);
		}

		bookSearchHistory.setBookSearchHistorySet(bookSearchValueSet.stream().sorted(Comparator.comparing(BookSearchValue::getSearchTime).reversed()).collect(Collectors.toList()));
		bookSearchHistoryRepository.save(bookSearchHistory);
	}

}
