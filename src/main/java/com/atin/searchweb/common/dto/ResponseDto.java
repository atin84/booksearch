package com.atin.searchweb.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ResponseDto<T> {

	private boolean success;

	private String message;

	private T content;

	public static <T> ResponseDto success(T content) {
		return new ResponseDto(true, "", content);
	}

	public static ResponseDto error(String message) {
		return new ResponseDto(false, message, null);
	}
}
