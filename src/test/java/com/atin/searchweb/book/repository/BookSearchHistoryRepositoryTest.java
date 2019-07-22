package com.atin.searchweb.book.repository;

import com.atin.searchweb.book.domain.BookSearchHistory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.TreeSet;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookSearchHistoryRepositoryTest {

	@Autowired
	private BookSearchHistoryRepository repository;

	@Test
	public void save___기본_등록_조회___success() {
		//given
		String id = "testid";
		BookSearchHistory history = new BookSearchHistory();
		history.setBookSearchHistorySet(Collections.emptyList());
		history.setId(id);

		//when
		repository.save(history);

		//then
		BookSearchHistory searchHistory = repository.findById(id).get();
		assertEquals(id, searchHistory.getId());
		assertEquals(null, searchHistory.getBookSearchHistorySet());
	}
}
