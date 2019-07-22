package com.atin.searchweb.book.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@EqualsAndHashCode(of = "keyword")
public class BookSearchValue {

	private String keyword;

	private LocalDateTime searchTime;

}
