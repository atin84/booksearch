package com.atin.searchweb.book.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookSearchRequestDto {

	private String title;

	private int page;

	private int size;

	private String id;

}
