package com.atin.searchweb.book.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookRankingDto {
	private long count;
	private String keyword;
}
