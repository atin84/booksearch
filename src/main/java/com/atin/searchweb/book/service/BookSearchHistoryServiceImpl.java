package com.atin.searchweb.book.service;

import com.atin.searchweb.book.domain.BookSearchHistory;
import com.atin.searchweb.book.domain.BookSearchValue;
import com.atin.searchweb.book.repository.BookSearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class BookSearchHistoryServiceImpl implements BookSearchHistoryService {

	private final BookSearchHistoryRepository bookSearchHistoryRepository;

	@Override
	public List<BookSearchValue> fetchBookSearchHistory(String id) {
		Optional<BookSearchHistory> bookSearchHistoryOptional = bookSearchHistoryRepository.findById(id);

		if (!bookSearchHistoryOptional.isPresent()) {
			return Collections.emptyList();
		}
		return bookSearchHistoryOptional.get().getBookSearchHistoryList();
	}

	@Override
	public void addBookSearchHistory(String id, BookSearchValue bookSearchValue) {
		LinkedList<BookSearchValue> bookSearchValueList;

		Optional<BookSearchHistory> bookSearchHistoryOptional = bookSearchHistoryRepository.findById(id);
		if (bookSearchHistoryOptional.isPresent()) {
			bookSearchValueList = bookSearchHistoryOptional.get().getBookSearchHistoryList();
			bookSearchValueList.remove(bookSearchValue);
		} else {
			bookSearchValueList = new LinkedList<>();
		}

		bookSearchValueList.addFirst(bookSearchValue);

		BookSearchHistory bookSearchHistory;
		if (bookSearchHistoryOptional.isPresent()) {
			bookSearchHistory = bookSearchHistoryOptional.get();
		} else {
			bookSearchHistory = new BookSearchHistory();
			bookSearchHistory.setId(id);
		}

		bookSearchHistory.setBookSearchHistoryList(bookSearchValueList);
		bookSearchHistoryRepository.save(bookSearchHistory);
	}

}
