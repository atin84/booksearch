package com.atin.searchweb.book.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookSearchValueDto {
	private String keyword;

	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
	private LocalDateTime searchTime;

}
