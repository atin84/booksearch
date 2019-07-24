package com.atin.searchweb.book.service;

import com.atin.searchweb.book.domain.BookSearchHistory;
import com.atin.searchweb.book.domain.BookSearchValue;
import com.atin.searchweb.book.repository.BookSearchHistoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookSearchHistoryServiceImplTest {

	@Mock
	private BookSearchHistoryRepository bookSearchHistoryRepository;

	@InjectMocks
	private BookSearchHistoryServiceImpl bookSearchHistoryService;

	@Test
	public void fetchBookSearchHistory___검색내역없음___결과없음() {
		// given
		Optional<BookSearchHistory> bookSearchHistoryOptional = Optional.empty();
		when(bookSearchHistoryRepository.findById(anyString())).thenReturn(bookSearchHistoryOptional);

		// when
		List<BookSearchValue> bookSearchValueResultList = bookSearchHistoryService.fetchBookSearchHistory("test_id");

		// then
		assertNotNull(bookSearchValueResultList);
		assertEquals(0, bookSearchValueResultList.size());
	}

	@Test
	public void fetchBookSearchHistory___검색기록있음___결과존재() {
		// given
		LinkedList<BookSearchValue> bookSearchValueList = new LinkedList<>();
		bookSearchValueList.add(BookSearchValue.builder().keyword("a").build());
		BookSearchHistory bookSearchHistory = new BookSearchHistory();
		bookSearchHistory.setBookSearchHistoryList(bookSearchValueList);
		Optional<BookSearchHistory> bookSearchHistoryOptional = Optional.of(bookSearchHistory);
		when(bookSearchHistoryRepository.findById(anyString())).thenReturn(bookSearchHistoryOptional);

		// when
		List<BookSearchValue> bookSearchValueResultList = bookSearchHistoryService.fetchBookSearchHistory("test_id");

		// then
		assertNotNull(bookSearchValueResultList);
		assertEquals(1, bookSearchValueResultList.size());
	}

	@Test
	public void addBookSearchHistory___default___success() {
		// given
		LinkedList<BookSearchValue> bookSearchValueList = new LinkedList<>();
		bookSearchValueList.add(BookSearchValue.builder().keyword("a").build());
		BookSearchHistory bookSearchHistory = new BookSearchHistory();
		bookSearchHistory.setBookSearchHistoryList(bookSearchValueList);

		Optional<BookSearchHistory> bookSearchHistoryOptional = Optional.of(bookSearchHistory);

		// when
		bookSearchHistoryService.addBookSearchHistory("testid", BookSearchValue.builder().keyword("a").build());

		when(bookSearchHistoryRepository.findById(anyString())).thenReturn(bookSearchHistoryOptional);

		bookSearchHistoryService.addBookSearchHistory("testid", BookSearchValue.builder().keyword("a").build());

		// then
		verify(bookSearchHistoryRepository, times(2)).save(any());
	}
}