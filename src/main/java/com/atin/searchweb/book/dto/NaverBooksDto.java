package com.atin.searchweb.book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class NaverBooksDto {

	@JsonProperty("items")
	private List<NaverBookDto> items;

	@JsonProperty("meta")
	private String lastBuildDate;

	@JsonProperty("total")
	private Integer total;

	@JsonProperty("start")
	private Integer start;

	@JsonProperty("display")
	private Integer display;

}
