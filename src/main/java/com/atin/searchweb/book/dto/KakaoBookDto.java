package com.atin.searchweb.book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class KakaoBookDto {
	// 도서 제목
	@JsonProperty("title")
	private String title;

	// 도서 소개
	@JsonProperty("contents")
	private String contents;

	// 도서 상세 URL
	@JsonProperty("url")
	private String url;

	// 국제 표준 도서번호(ISBN10 ISBN13) (ISBN10,ISBN13 중 하나 이상 존재하며, ' '(공백)을 구분자로 출력됩니다)
	@JsonProperty("isbn")
	private String isbn;

	// 도서 출판날짜. ISO 8601. [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
	@JsonProperty("datetime")
	private String datetime;

	// 도서 저자 리스트
	@JsonProperty("authors")
	private List<String> authors;

	// 도서 출판사
	@JsonProperty("publisher")
	private String publisher;

	// 도서 번역자 리스트
	@JsonProperty("translators")
	private List<String> translators;

	// 도서 정가
	@JsonProperty("price")
	private Integer price;

	// 도서 판매가
	@JsonProperty("sale_price")
	private Integer salePrice;

	// 도서 표지 썸네일 URL
	@JsonProperty("thumbnail")
	private String thumbnail;

	// 도서 판매 상태 정보(정상, 품절, 절판 등), 상황에 따라 변동 가능성이 있으므로 문자열 처리 지양, 단순 노출요소로 활용을 권장합니다.
	@JsonProperty("status")
	private String status;
}
