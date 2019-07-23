package com.atin.searchweb.book.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.LinkedList;

@Setter
@Getter
@RedisHash("book_history")
public class BookSearchHistory {

	@Id
	private String id;

	private LinkedList<BookSearchValue> bookSearchHistoryList;
}
