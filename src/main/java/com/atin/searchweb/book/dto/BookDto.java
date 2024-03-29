package com.atin.searchweb.book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookDto {
	@JsonProperty("title")
	private String title;

	@JsonProperty("contents")
	private String contents;

	@JsonProperty("url")
	private String url;

	@JsonProperty("isbn")
	private String isbn;

	@JsonProperty("datetime")
	private String datetime;

	@JsonProperty("authors")
	private String authors;

	@JsonProperty("publisher")
	private String publisher;

	@JsonProperty("translators")
	private String translators;

	@JsonProperty("price")
	private Integer price;

	@JsonProperty("salePrice")
	private Integer salePrice;

	@JsonProperty("thumbnail")
	private String thumbnail;

	@JsonProperty("status")
	private String status;
}
