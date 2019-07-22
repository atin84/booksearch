package com.atin.searchweb.book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NaverBookDto {
	@JsonProperty("title")
	private String title;

	@JsonProperty("description")
	private String contents;

	@JsonProperty("link")
	private String url;

	@JsonProperty("isbn")
	private String isbn;

	@JsonProperty("pubdate")
	private String datetime;

	@JsonProperty("author")
	private String authors;

	@JsonProperty("publisher")
	private String publisher;

	@JsonProperty("price")
	private Integer price;

	@JsonProperty("discount")
	private Integer salePrice;

	@JsonProperty("image")
	private String thumbnail;
}
