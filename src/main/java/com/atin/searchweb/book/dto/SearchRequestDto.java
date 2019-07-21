package com.atin.searchweb.book.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SearchRequestDto {

	@NotBlank
	private String title;

	private int page;

	private int size;

	private String id;

}