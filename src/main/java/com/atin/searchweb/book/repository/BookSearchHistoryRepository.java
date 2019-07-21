package com.atin.searchweb.book.repository;

import com.atin.searchweb.book.domain.BookSearchHistory;
import org.springframework.data.repository.CrudRepository;

public interface BookSearchHistoryRepository extends CrudRepository<BookSearchHistory, String> {
}
